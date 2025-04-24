package com.example.simpletradingapp.model;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Create a User
        User u = new User(1, "Tim", "t@t", "hashed123", 10000.00);
        System.out.println("Created user: " + u.getUsername() + ", balance: $" + u.getBalance());

        // Create a Category
        Category cat = new Category("AAPL", "Apple");

        // Create a StockDataset exp
        StockDataset stock = new StockDataset(
                new Date(),
                120,
                125,
                118,
                121,
                121,
                5000000,
                cat
        );

        System.out.println("Loaded stock: " + stock.getSymbol() + " at $" + stock.getClose());

        // Buy 50 shares
        boolean bought = u.buyStock(stock, 50);
        if (bought) {
            System.out.println("Bought 50 shares!");
        } else {
            System.out.println("Could not buy.");
        }

        System.out.println("Balance after buy: $" + u.getBalance());

        // Sell 20 shares
        boolean sold = u.sellStock(stock, 20);
        if (sold) {
            System.out.println("Sold 20 shares!");
        } else {
            System.out.println("Could not sell.");
        }
        System.out.println("Final balance: $" + u.getBalance());

    }
}
