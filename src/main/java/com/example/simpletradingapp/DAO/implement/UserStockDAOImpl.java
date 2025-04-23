package com.example.simpletradingapp.DAO.implement;

import com.example.simpletradingapp.DAO.UserStockDAO;
import com.example.simpletradingapp.db.DbUtil;
import com.example.simpletradingapp.model.UserStock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserStockDAOImpl implements UserStockDAO {

    @Override
    public List<UserStock> getHoldingsByUser(int userId) {
        List<UserStock> list = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM User_Stock WHERE user_id = ?"
            );
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserStock us = new UserStock(
                        rs.getInt("user_id"),
                        rs.getString("stock_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("avg_buy_price"),
                        rs.getDouble("total_value"),
                        rs.getDate("purchase_date")
                );
                list.add(us);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateQuantity(int userId, String stockId, int newQty) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE User_Stock SET quantity = ? WHERE user_id = ? AND stock_id = ?"
            );
            stmt.setInt(1, newQty);
            stmt.setInt(2, userId);
            stmt.setString(3, stockId);
            return stmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insertHolding(UserStock holding) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO User_Stock (user_id, stock_id, quantity, avg_buy_price, total_value, purchase_date) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );
            stmt.setInt(1, holding.getUserId());
            stmt.setString(2, holding.getStockId());
            stmt.setInt(3, holding.getQuantity());
            stmt.setDouble(4, holding.getAvgBuyPrice());
            stmt.setDouble(5, holding.getTotalValue());
            stmt.setDate(6, holding.getPurchaseDate());
            return stmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteHolding(int userId, String stockId, java.sql.Date purchaseDate) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM User_Stock WHERE user_id = ? AND stock_id = ? AND purchase_date = ?"
            );
            stmt.setInt(1, userId);
            stmt.setString(2, stockId);
            stmt.setDate(3, purchaseDate);
            return stmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
