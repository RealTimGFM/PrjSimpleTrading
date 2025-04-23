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

        Connection conn = null;

        try {
            conn = DbUtil.getConnection();
            conn.setAutoCommit(false); // 🔐 Begin transaction

            // ✅ Sum all shares across purchase dates
            PreparedStatement check = conn.prepareStatement(
                    "SELECT SUM(quantity) AS total_qty " +
                            "FROM User_Stock us " +
                            "JOIN StockDataset sd ON us.stock_id = sd.stockID " +
                            "WHERE us.user_id = ? AND sd.symbol = ?"
            );
            check.setInt(1, userId);
            check.setString(2, symbol);
            ResultSet rs = check.executeQuery();

            if (!rs.next() || rs.getInt("total_qty") < qty) {
                res.getWriter().println("You don't own enough shares to sell.");
                return;
            }

            int remainingToSell = qty;

            // ✅ Get individual purchase records (to update/delete accurately)
            PreparedStatement selectLots = conn.prepareStatement(
                    "SELECT us.quantity, us.purchase_date, us.stock_id " +
                            "FROM User_Stock us " +
                            "JOIN StockDataset sd ON us.stock_id = sd.stockID " +
                            "WHERE us.user_id = ? AND sd.symbol = ? " +
                            "ORDER BY us.purchase_date ASC"
            );
            selectLots.setInt(1, userId);
            selectLots.setString(2, symbol);
            ResultSet lots = selectLots.executeQuery();

            while (lots.next() && remainingToSell > 0) {
                int lotQty = lots.getInt("quantity");
                Date lotDate = lots.getDate("purchase_date");
                String actualStockId = lots.getString("stock_id");
                if (lotQty <= remainingToSell) {
                    // Sell entire lot
                    PreparedStatement delete = conn.prepareStatement(
                            "DELETE FROM User_Stock WHERE user_id = ? AND stock_id = ? AND purchase_date = ?"
                    );
                    delete.setInt(1, userId);
                    delete.setString(2, actualStockId);
                    delete.setDate(3, lotDate);
                    delete.executeUpdate();

                    remainingToSell -= lotQty;
                } else {
                    // Partial sell from this lot
                    PreparedStatement update = conn.prepareStatement(
                            "UPDATE User_Stock SET quantity = quantity - ? WHERE user_id = ? AND stock_id = ? AND purchase_date = ?"
                    );
                    update.setInt(1, remainingToSell);
                    update.setInt(2, userId);
                    update.setString(3, actualStockId);
                    update.setDate(4, lotDate);
                    update.executeUpdate();

                    remainingToSell = 0;
                }
            }

            // ✅ Add cash back to user
            double gain = qty * stock.getClose();
            PreparedStatement updateBal = conn.prepareStatement(
                    "UPDATE Users SET balance = balance + ? WHERE user_id = ?"
            );
            updateBal.setDouble(1, gain);
            updateBal.setInt(2, userId);
            updateBal.executeUpdate();

            conn.commit(); // ✅ Done
            res.sendRedirect("company-list");

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Sell failed, rolling back.");
                } catch (SQLException se) {
                    System.err.println("Rollback error: " + se.getMessage());
                }
            }
            e.printStackTrace();
            res.getWriter().println("Something went wrong while selling.");
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }
}
