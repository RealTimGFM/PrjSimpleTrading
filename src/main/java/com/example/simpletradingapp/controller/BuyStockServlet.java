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
        UserStockDAOImpl stockHoldingsDao = new UserStockDAOImpl();

        StockDataset stock = stockDao.findCloseByDate(fakeToday, cat);
        if (stock == null) {
            res.getWriter().println("No price data available for this date.");
            return;
        }

        double price = stock.getClose();
        double totalCost = price * qty;

        try (Connection conn = DbUtil.getConnection()) {
            conn.setAutoCommit(false);  // start transaction

            // 1. Get user's current balance
            double balance = 0;
            PreparedStatement getBal = conn.prepareStatement("SELECT balance FROM Users WHERE user_id = ?");
            getBal.setInt(1, userId);
            ResultSet rs = getBal.executeQuery();
            if (rs.next()) {
                balance = rs.getDouble("balance");
            } else {
                res.getWriter().println("User not found.");
                return;
            }

            if (balance < totalCost) {
                res.getWriter().println("Not enough balance.");
                return;
            }

            // 2. Deduct balance
            PreparedStatement updateBal = conn.prepareStatement(
                    "UPDATE Users SET balance = balance - ? WHERE user_id = ?"
            );
            updateBal.setDouble(1, totalCost);
            updateBal.setInt(2, userId);
            updateBal.executeUpdate();

            // 3. Insert stock using DAO (with shared connection!)
            UserStock holding = new UserStock(userId, stock.getStockId(), qty, price, totalCost, new java.sql.Date(fakeToday.getTime()));
            boolean inserted = stockHoldingsDao.insertHolding(conn, holding);

            if (!inserted) {
                conn.rollback();
                res.getWriter().println("Failed to save purchase. Rolled back.");
                return;
            }

            conn.commit();  // commit everything
            res.sendRedirect("company-list");

        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error during purchase.");
        }
    }
}