package com.example.simpletradingapp.DAO;

import com.example.simpletradingapp.model.UserStock;

import java.util.List;

public interface UserStockDAO {
    List<UserStock> getHoldingsByUser(int userId);
    boolean updateQuantity(int userId, String stockId, int newQty);
    boolean insertHolding(UserStock holding);
    boolean deleteHolding(int userId, String stockId, java.sql.Date purchaseDate);
}
