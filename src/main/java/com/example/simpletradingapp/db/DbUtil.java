package com.example.simpletradingapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    // JDBC URL for H2 database (file-based, stored in user home directory)
    private static final String JDBC_URL = "jdbc:h2:./simpletradingapp;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa"; // Default H2 username
    private static final String PASSWORD = ""; // Default H2 password

    // Static block to load the JDBC driver
    static {
        try {
            Class.forName("org.h2.Driver");
            System.out.println("H2 JDBC driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("H2 JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    /**
     * Get a database connection
     * @return a new database connection
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    /**
     * Close a connection quietly (without throwing exceptions)
     * @param connection the connection to close
     */
    public static void closeQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
