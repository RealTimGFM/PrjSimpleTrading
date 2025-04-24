package com.example.simpletradingapp.controller;

import com.example.simpletradingapp.db.DbUtil;
import com.example.simpletradingapp.utils.DateManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;
/**
 * Shows the logged-in user's portfolio including current holdings.
 */
@WebServlet("/portfolio")
public class PortfolioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        List<Map<String, Object>> portfolio = new ArrayList<>();

        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT us.stock_id, s.symbol, s.name, us.quantity, us.avg_buy_price, us.total_value " +
                            "FROM User_Stock us JOIN StockDataset s ON us.stock_id = s.stockID " +
                            "WHERE us.user_id = ?"
            );
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("symbol", rs.getString("symbol"));
                row.put("name", rs.getString("name"));
                row.put("quantity", rs.getInt("quantity"));
                row.put("avgPrice", rs.getDouble("avg_buy_price"));
                row.put("totalValue", rs.getDouble("total_value"));
                portfolio.add(row);
            }

            req.setAttribute("portfolio", portfolio);
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/WEB-INF/portfolio.jsp").forward(req, res);
    }
}
