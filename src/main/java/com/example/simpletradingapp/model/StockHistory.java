package com.example.simpletradingapp.model;

import java.sql.Date;

public class StockHistory {
    private int historyId;
    private int stockId;
    private Date recordedDate;
    private double price;

    public StockHistory(int historyId, int stockId, Date recordedDate, double price) {
        this.historyId = historyId;
        this.stockId = stockId;
        this.recordedDate = recordedDate;
        this.price = price;
    }

    // Admin updates stock history (for testing)
    public void changeDate(Date newDate) {
        this.recordedDate = newDate;
        System.out.println("Stock history date changed to: " + newDate);
    }

    // Getters and Setters
    public int getHistoryId() { return historyId; }
    public int getStockId() { return stockId; }
    public Date getRecordedDate() { return recordedDate; }
    public double getPrice() { return price; }
}
