package com.example.simpletradingapp.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private double balance;
    private Map<Integer, Integer> portfolio; // Stock ID -> Quantity Owned

    public User(int userId, String username, String email, String passwordHash, double balance) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.balance = balance;
        this.portfolio = new HashMap<>();
    }

    // Buy stocks
    public boolean buyStock(Stock stock, int quantity) {
        double totalCost = stock.getPrice() * quantity;
        if (balance >= totalCost) {
            balance -= totalCost;
            portfolio.put(stock.getStockId(), portfolio.getOrDefault(stock.getStockId(), 0) + quantity);
            System.out.println(username + " bought " + quantity + " shares of " + stock.getSymbol());
            return true;
        } else {
            System.out.println("Insufficient funds!");
            return false;
        }
    }

    // Sell stocks
    public boolean sellStock(Stock stock, int quantity) {
        if (portfolio.getOrDefault(stock.getStockId(), 0) >= quantity) {
            double totalSale = stock.getPrice() * quantity;
            balance += totalSale;
            portfolio.put(stock.getStockId(), portfolio.get(stock.getStockId()) - quantity);
            System.out.println(username + " sold " + quantity + " shares of " + stock.getSymbol());
            return true;
        } else {
            System.out.println("Not enough shares to sell!");
            return false;
        }
    }

    // Check balance
    public double checkBalance() {
        return balance;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public double getBalance() { return balance; }
}
