package com.example.simpletradingapp.DAO;

import com.example.simpletradingapp.model.UserStock;

import java.util.List;

public interface UserStockDAO {
    /**
     * Get all stock holdings owned by a user.
     *
     * @param userId
     * @return a list of UserStock objects owned by the user
     */
    List<UserStock> getHoldingsByUser(int userId);
    /**
     * Update the quantity of a specific stock holding.
     *
     * @param userId
     * @param stockId
     * @param newQty the new quantity to set
     * @return true if updated successfully, false otherwise
     */
    boolean updateQuantity(int userId, String stockId, int newQty);
    /**
     * Insert a new stock holding for the user.
     *
     * @param holding the UserStock object to insert
     * @return true if inserted successfully, false otherwise
     */
    boolean insertHolding(UserStock holding);
    /**
     * Delete a stock holding based on user ID, stock ID, and purchase date.
     *
     * @param userId the user’s ID
     * @param stockId the stock ID
     * @param purchaseDate the original purchase date
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteHolding(int userId, String stockId, java.sql.Date purchaseDate);
}
