package com.example.simpletradingapp.model;

public class Category {
    private String symbol;
    private String name;

    // Basic constructor
    public Category() {
    }

    // Constructor with fields
    public Category(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    // Getters and Setters
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
