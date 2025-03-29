package com.example.simpletradingapp.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer {

    public static void initializeSchema() {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();
            Statement stmt = conn.createStatement();

            // Drop tables if they exist (for development/testing purposes)
            //stmt.execute("DROP TABLE IF EXISTS User_Stock");
            //stmt.execute("DROP TABLE IF EXISTS Stock_History");
            //stmt.execute("DROP TABLE IF EXISTS Stocks");
            //stmt.execute("DROP TABLE IF EXISTS Users");
            //System.out.println("Old tables dropped.");

            // Create Users table
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS Users (" +
                            "  user_id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "  username VARCHAR(50) UNIQUE NOT NULL, " +
                            "  email VARCHAR(100) UNIQUE NOT NULL, " +
                            "  password_hash VARCHAR(255) NOT NULL, " +
                            "  balance DECIMAL(10,2) NOT NULL DEFAULT 100000.00" +
                            ")"
            );
            System.out.println("Users table created.");

            // Create Stocks table (stores latest price)
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS Stocks (" +
                            "  stock_id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "  symbol VARCHAR(10) UNIQUE NOT NULL, " +
                            "  company_name VARCHAR(100) NOT NULL, " +
                            "  price DECIMAL(10,2) NOT NULL, " +
                            "  last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                            ")"
            );
            System.out.println("Stocks table created.");

            // Create Stock_History table (tracks daily stock prices)
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS Stock_History (" +
                            "  history_id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "  stock_id INT NOT NULL, " +
                            "  recorded_date DATE NOT NULL DEFAULT CURRENT_DATE, " +
                            "  price DECIMAL(10,2) NOT NULL, " +
                            "  FOREIGN KEY (stock_id) REFERENCES Stocks(stock_id) ON DELETE CASCADE" +
                            ")"
            );
            System.out.println("Stock_History table created.");

            // Create User_Stock table (tracks user purchases)
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS User_Stock (" +
                            "  user_id INT NOT NULL, " +
                            "  stock_id INT NOT NULL, " +
                            "  quantity INT NOT NULL, " +
                            "  avg_buy_price DECIMAL(10,2) NOT NULL, " +
                            "  total_value DECIMAL(10,2) NOT NULL, " +
                            "  purchase_date DATE NOT NULL DEFAULT CURRENT_DATE, " +
                            "  PRIMARY KEY (user_id, stock_id, purchase_date), " +
                            "  FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE, " +
                            "  FOREIGN KEY (stock_id) REFERENCES Stocks(stock_id) ON DELETE CASCADE" +
                            ")"
            );
            System.out.println("User_Stock table created.");

            System.out.println("Database schema setup complete!");

        } catch (SQLException e) {
            System.err.println("Error setting up database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }

    public static void main(String[] args) {
        initializeSchema(); // Run this to initialize DB schema
    }
}