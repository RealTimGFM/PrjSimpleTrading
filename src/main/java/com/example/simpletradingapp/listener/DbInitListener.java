package com.example.simpletradingapp.listener;

import com.example.simpletradingapp.db.SchemaInitializer;
import com.example.simpletradingapp.model.StockDataset;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@WebListener
public class DbInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing Stock Database...");

        SchemaInitializer.initializeSchema();
        System.out.println("Stock Database Initialized!...");

    }



    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Clean up database resources if needed
        System.out.println("Trading application shutting down...");
    }
}
