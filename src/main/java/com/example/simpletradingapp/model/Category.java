package com.example.simpletradingapp.model;

public class Category {
    private String stockId;
    private String symbol;

    // Basic constructor
    public Category() {
    }

    // Constructor with fields
    public Category(String stockId, String symbol) {
        this.stockId = stockId;
        this.symbol = symbol;
    }

    // Getters and Setters
    public String getstockId() {
        return this.stockId;
    }

    public void setstockId(String stockId) {
        this.stockId = stockId;
    }

    public String getsymbol() {
        return symbol;
    }

    public void setsymbol(String symbol) {
        this.symbol = symbol;
    }
}
