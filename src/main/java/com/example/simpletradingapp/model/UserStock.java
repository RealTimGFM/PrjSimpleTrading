package com.example.simpletradingapp.model;

import java.sql.Date;

public class UserStock {
    private int userId;
    private String stockId;
    private int quantity;
    private double avgBuyPrice;
    private double totalValue;
    private Date purchaseDate;

    public UserStock(int userId, String stockId, int quantity, double avgBuyPrice, double totalValue, Date purchaseDate) {
        this.userId = userId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.avgBuyPrice = avgBuyPrice;
        this.totalValue = totalValue;
        this.purchaseDate = purchaseDate;
    }

    // Calculate profit/loss based on new price
    public double calculateProfitLoss(double currentPrice) {
        double currentValue = currentPrice * quantity;
        return currentValue - totalValue;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public String getStockId() { return stockId; }
    public int getQuantity() { return quantity; }
    public double getAvgBuyPrice() { return avgBuyPrice; }
    public double getTotalValue() { return totalValue; }
    public Date getPurchaseDate() { return purchaseDate; }
}
