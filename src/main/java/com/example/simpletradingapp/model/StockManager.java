package com.example.simpletradingapp.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockManager {

    private List<StockDataset> stockDatasets;
    private List<Category> categories;

    public StockManager() {

        stockDatasets = new ArrayList<>();
        categories = new ArrayList<>();

        System.out.println("||- CSV Initializing in CompanyManager -||");
        Category aapl = new Category("AAPL", "Apple");
        Category amd = new Category("AMD", "Advanced Micro Devices");
        Category amzn = new Category("AMZN","Amazon");
        Category goog = new Category("GOOG", "Google");
        Category msft = new Category("MSFT","Microsoft");
        Category orcl = new Category("ORCL", "Oracle");
        Category pins = new Category("PINS", "Pinterest");
        Category tsla = new Category("TSLA", "Tesla");

        categories.add(aapl);
        categories.add(amd);
        categories.add(amzn);
        categories.add(goog);
        categories.add(msft);
        categories.add(orcl);
        categories.add(pins);
        categories.add(tsla);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("ClassLoader READY");

        for (int i = 0; i < categories.size(); i++) {
            String fileName = categories.get(i).getSymbol();
            String resourcePath = "data/" + fileName + ".csv";

            try (InputStream is = classLoader.getResourceAsStream(resourcePath)) {
                if (is == null) {
                    System.out.println("Resource not found: " + resourcePath);
                    continue;
                }

                // Load the CSV data and add StockDataset objects to the list
                List<StockDataset> datasets = loadCSVData(is, categories.get(i));

                // Add datasets to the main list of all stock datasets
                stockDatasets.addAll(datasets);

                System.out.println("Initialized CSV: " + fileName + " with " + datasets.size() + " records");

            } catch (Exception e) {
                System.err.println("Failed to load CSV: " + fileName);
                e.printStackTrace();
            }
        }
    }

    // Method to load and parse CSV data
    public List<StockDataset> loadCSVData(InputStream is, Category category) throws IOException {
        List<StockDataset> records = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;  // Skip header
                }

                String[] columns = line.split(",");
                if (columns.length >= 7) {
                    try {
                        Date date = format.parse(columns[0].trim());

                        // Create a new StockDataset object for each line in CSV
                        StockDataset dataset = new StockDataset(
                                date,
                                Double.parseDouble(columns[1]),
                                Double.parseDouble(columns[2]),
                                Double.parseDouble(columns[3]),
                                Double.parseDouble(columns[4]),
                                Double.parseDouble(columns[5]),
                                Integer.parseInt(columns[6]),
                                category
                        );

                        // Add the created StockDataset to the list
                        records.add(dataset);

                    } catch (Exception e) {
                        System.err.println("Error parsing line: " + line);
                    }
                } else {
                    System.err.println("Malformed line (too few columns): " + line);
                }
            }
        }
        return records;
    }

    // Getter method to retrieve all datasets
    public List<StockDataset> getAllStockDatasets() {return stockDatasets;}
    public List<Category> getAllCategories() {
        return categories;
    }

    //Find stock by ID
    public StockDataset getStockById(String id) {
        for (StockDataset stock : stockDatasets) {
            if (stock.getStockId().equals(id)) {
                return stock;
            }
        }
        return null;
    }
    //find stocks by Category
    public List<StockDataset> getStocksByCategory(String categoryId) {
        List<StockDataset> result = new ArrayList<>();

        for (StockDataset stock : stockDatasets) {
            if (stock.getCategory() != null && stock.getCategory().getSymbol().equals(categoryId)) {
                result.add(stock);
            }
        }
        return result;
    }
    
}