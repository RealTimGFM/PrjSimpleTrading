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

        DatasetDAOImpl dao = new DatasetDAOImpl();
        Date fakeToday = DateManager.getFakeToday();
        Category cat = new Category(symbol, symbol);
        StockDataset stock = dao.findCloseByDate(fakeToday, cat);

        if (stock == null) {
            res.getWriter().println("No price data available for this date.");
            return;
        }

        double price = stock.getClose();
        double totalCost = price * qty;

        try (Connection conn = DbUtil.getConnection()) {
            // Check balance
            PreparedStatement balStmt = conn.prepareStatement("SELECT balance FROM Users WHERE user_id = ?");
            balStmt.setInt(1, userId);
            ResultSet rs = balStmt.executeQuery();
            if (!rs.next() || rs.getDouble("balance") < totalCost) {
                res.getWriter().println("Not enough balance.");
                return;
            }

            // Deduct balance
            PreparedStatement updateBal = conn.prepareStatement("UPDATE Users SET balance = balance - ? WHERE user_id = ?");
            updateBal.setDouble(1, totalCost);
            updateBal.setInt(2, userId);
            updateBal.executeUpdate();

            // Insert into User_Stock
            PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO User_Stock (user_id, stock_id, quantity, avg_buy_price, total_value, purchase_date) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );
            insert.setInt(1, userId);
            insert.setString(2, stock.getStockId());
            insert.setInt(3, qty);
            insert.setDouble(4, price);
            insert.setDouble(5, totalCost);
            insert.setDate(6, fakeToday);

            insert.executeUpdate();

            res.sendRedirect("company-list");

        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error during purchase.");
        }
    }
}
