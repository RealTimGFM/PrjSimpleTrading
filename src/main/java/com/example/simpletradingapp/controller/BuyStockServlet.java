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
import java.sql.*;
/**
 * Handles stock purchase requests.
 * Validates balance, inserts user stock, and updates balance in DB.
 */
@WebServlet("/buy-stock")
public class BuyStockServlet extends HttpServlet {
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
            res.getWriter().println("No price data available for this date.");
            return;
        }

        double price = stock.getClose();
        double totalCost = price * qty;

        Connection conn = null;

        try {
            conn = DbUtil.getConnection();
            conn.setAutoCommit(false);

            User user = userDao.getUserById(userId);
            if (user.getBalance() < totalCost) {
                res.getWriter().println("Not enough balance.");
                return;
            }

            userDao.updateBalance(userId, user.getBalance() - totalCost);

            UserStock holding = new UserStock(userId, stock.getStockId(), qty, price, totalCost, new java.sql.Date(fakeToday.getTime()));
            stockHoldingsDao.insertHolding(holding);

            conn.commit();
            res.sendRedirect("company-list");

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Rollback error: " + ex.getMessage());
                }
            }
            e.printStackTrace();
            res.getWriter().println("Error during purchase.");
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }
}
