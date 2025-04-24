package com.example.simpletradingapp.model;

import java.util.HashMap;
import java.util.Map;

public class User extends Person {
    private String passwordHash;
    private double balance;
    private Map<String, Integer> portfolio;

    public User(int userId, String username, String email, String passwordHash, double balance) {
        super(userId, username, email);  // Call super from Person
        this.passwordHash = passwordHash;
        this.balance = balance;
        this.portfolio = new HashMap<>();
    }

    public double getBalance() { return balance; }
    public String getPasswordHash() { return passwordHash; }

    public double checkBalance() { return balance; }

    public boolean buyStock(StockDataset stock, int quantity) {
        double totalCost = stock.getClose() * quantity;
        if (balance >= totalCost) {
            balance -= totalCost;
            portfolio.put(stock.getStockId(), portfolio.getOrDefault(stock.getStockId(), 0) + quantity);
            return true;
        }
        return false;
    }

    public boolean sellStock(StockDataset stock, int quantity) {
        if (portfolio.getOrDefault(stock.getStockId(), 0) >= quantity) {
            double totalSale = stock.getClose() * quantity;
            balance += totalSale;
            portfolio.put(stock.getStockId(), portfolio.get(stock.getStockId()) - quantity);
            return true;
        }
        return false;
    }
}