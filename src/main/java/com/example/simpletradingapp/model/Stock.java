package com.example.simpletradingapp.model;

import java.sql.Timestamp;

public class Stock {
    private int stockId;
    private String symbol;
    private String companyName;
    private double price;
    private Timestamp lastUpdated;

    public Stock(int stockId, String symbol, String companyName, double price, Timestamp lastUpdated) {
        this.stockId = stockId;
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
        this.lastUpdated = lastUpdated;
    }

    // Update stock price (used by admin or system)
    public void updatePrice(double newPrice) {
        this.price = newPrice;
        this.lastUpdated = new Timestamp(System.currentTimeMillis());
        System.out.println("Stock " + symbol + " price updated to $" + newPrice);
    }

    // Getters and Setters
    public int getStockId() { return stockId; }
    public String getSymbol() { return symbol; }
    public String getCompanyName() { return companyName; }
    public double getPrice() { return price; }
    public Timestamp getLastUpdated() { return lastUpdated; }
}
