package com.example.simpletradingapp.Test;
import jakarta.servlet.ServletContextListener;
import com.example.simpletradingapp.listener.DbInitListener;
import com.example.simpletradingapp.model.StockDataset;

import java.io.InputStream;
import java.util.*;

public class CSVTest {
    public static void main(String[] args) throws Exception {
        String[] fileNames = { "AAPL.csv", "AMD.csv" };
        String[] companyNames = { "Apple", "AMD" };

        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("db/data/" + fileName);

            List<StockDataset> records = DbInitListener.loadCSVData(is, fileName, companyNames[i]);

            System.out.println("File: " + fileName + ", Records loaded: " + records.size());
        }
    }
}
