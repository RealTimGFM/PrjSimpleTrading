package com.example.simpletradingapp.controller;

import com.example.simpletradingapp.DAO.implement.DatasetDAOImpl;
import com.example.simpletradingapp.db.DbUtil;
import com.example.simpletradingapp.model.Category;
import com.example.simpletradingapp.model.StockDataset;
import com.example.simpletradingapp.utils.DateManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

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

        DatasetDAOImpl dao = new DatasetDAOImpl();
        StockDataset stock = dao.findCloseByDate(fakeToday, cat);
        if (stock == null) {
            res.getWriter().println("No price data found for this stock on fake today.");
            return;
        }

        try (Connection conn = DbUtil.getConnection()) {
            // Check if user owns this stock
            PreparedStatement check = conn.prepareStatement(
                    "SELECT quantity FROM User_Stock WHERE user_id = ? AND stock_id = ?"
            );
            check.setInt(1, userId);
            check.setString(2, stock.getStockId());

            ResultSet rs = check.executeQuery();
            if (!rs.next()) {
                res.getWriter().println("You don't own this stock.");
                return;
            }

            int ownedQty = rs.getInt("quantity");
            if (qty > ownedQty) {
                res.getWriter().println("You only own " + ownedQty + " shares.");
                return;
            }

            double price = stock.getClose();
            double totalGain = qty * price;

            // Update balance
            PreparedStatement updateBal = conn.prepareStatement(
                    "UPDATE Users SET balance = balance + ? WHERE user_id = ?"
            );
            updateBal.setDouble(1, totalGain);
            updateBal.setInt(2, userId);
            updateBal.executeUpdate();

            // Update User_Stock quantity or delete if sold all
            if (qty == ownedQty) {
                PreparedStatement deleteStock = conn.prepareStatement(
                        "DELETE FROM User_Stock WHERE user_id = ? AND stock_id = ?"
                );
                deleteStock.setInt(1, userId);
                deleteStock.setString(2, stock.getStockId());
                deleteStock.executeUpdate();
            } else {
                PreparedStatement updateQty = conn.prepareStatement(
                        "UPDATE User_Stock SET quantity = quantity - ? WHERE user_id = ? AND stock_id = ?"
                );
                updateQty.setInt(1, qty);
                updateQty.setInt(2, userId);
                updateQty.setString(3, stock.getStockId());
                updateQty.executeUpdate();
            }

            res.sendRedirect("company-list");

        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Something went wrong while selling.");
        }
    }
}
