package com.example.simpletradingapp.controller;

import com.example.simpletradingapp.DAO.implement.DatasetDAOImpl;
import com.example.simpletradingapp.DAO.implement.UserDAOImpl;
import com.example.simpletradingapp.DAO.implement.UserStockDAOImpl;
import com.example.simpletradingapp.model.Category;
import com.example.simpletradingapp.model.StockDataset;
import com.example.simpletradingapp.model.User;
import com.example.simpletradingapp.model.UserStock;
import com.example.simpletradingapp.utils.DateManager;
import com.example.simpletradingapp.db.DbUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
/**
 * Handles stock sell requests.
 */
@WebServlet("/sell-stock")
public class SellStockServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String symbol = req.getParameter("symbol");
        int qty = Integer.parseInt(req.getParameter("quantity"));

        Category cat = new Category(symbol, symbol);
        Date fakeToday = DateManager.getFakeToday();

        DatasetDAOImpl stockDao = new DatasetDAOImpl();
        UserDAOImpl userDao = new UserDAOImpl();
        UserStockDAOImpl stockHoldingsDao = new UserStockDAOImpl();

        StockDataset stock = stockDao.findCloseByDate(fakeToday, cat);
        if (stock == null) {
            res.getWriter().println("No price data found for this stock on fake today.");
            return;
        }

        Connection conn = null;

        try {
            conn = DbUtil.getConnection();
            conn.setAutoCommit(false);

            List<UserStock> holdings = stockHoldingsDao.getHoldingsByUser(userId);
            int totalOwned = 0;
            for (UserStock h : holdings) {
                String heldSymbol = stockDao.findSymbolByStockId(h.getStockId());
                if (heldSymbol.equals(symbol)) {
                    totalOwned += h.getQuantity();
                }
            }

            if (totalOwned < qty) {
                res.getWriter().println("You don't own enough shares to sell.");
                return;
            }

            int remainingToSell = qty;

            for (UserStock h : holdings) {
                if (remainingToSell == 0) break;

                String heldSymbol = stockDao.findSymbolByStockId(h.getStockId());
                if (!heldSymbol.equals(symbol)) continue;

                int lotQty = h.getQuantity();
                Date purchaseDate = h.getPurchaseDate();

                if (lotQty <= remainingToSell) {
                    stockHoldingsDao.deleteHolding(userId, h.getStockId(), purchaseDate);
                    remainingToSell -= lotQty;
                } else {
                    stockHoldingsDao.updateQuantity(conn, userId, h.getStockId(), lotQty - remainingToSell);
                    remainingToSell = 0;
                }
            }

            double gain = qty * stock.getClose();
            User user = userDao.getUserById(userId);
            userDao.updateBalance(userId, user.getBalance() + gain);

            conn.commit();
            res.sendRedirect("company-list");

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Sell failed. Rolled back.");
                } catch (SQLException ex) {
                    System.err.println("Rollback error: " + ex.getMessage());
                }
            }
            e.printStackTrace();
            res.getWriter().println("Something went wrong while selling.");
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }
}
