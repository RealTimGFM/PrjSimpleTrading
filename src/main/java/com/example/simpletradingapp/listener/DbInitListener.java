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

    //CSV INITIALIZER
        System.out.println("||- CSV Initializing -||");
        ServletContext context = sce.getServletContext();

        String[] fileNames = {
                "AAPL.csv", "AMD.csv", "AMZN.csv", "GOOG.csv",
                "MSFT.csv", "ORCL.csv", "PINS.csv", "TSLA.csv"
        };
        String[] companyNames = {"Apple", "Advanced Micro Devices", "Amazon", "Google", "Microsoft", "Oracle", "Pinterest", "Tesla"};

        Map<String, List<StockDataset>> allCsvData = new HashMap<>();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("ClassLoader COMPLETE");

        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            String resourcePath = "data/" + fileName;

            try (InputStream is = classLoader.getResourceAsStream(resourcePath)) {
                if (is == null) {
                    System.out.println("Resource not found: " + resourcePath);
                    continue;
                }

                // Add (company id || file name) and company name to the StockDataset constructor
                List<StockDataset> csvRecords = loadCSVData(is, fileName, companyNames[i]);
                allCsvData.put(fileName, csvRecords);

                System.out.println("Loaded: " + fileName);

            } catch (Exception e) {
                context.log("Failed to load CSV: " + fileName, e);
            }
        }


        context.setAttribute("allCsvData", allCsvData);
    }

    public static List<StockDataset> loadCSVData(InputStream is, String fileName, String companyName) throws IOException {
        List<StockDataset> records = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // Format to parse date

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;  // Skip header line
                }

                // Split csv into columns
                String[] columns = line.split(",");
                String dateString = columns[0].trim();

                // Ensure there are enough columns
                if (columns.length >= 7) {
                    try {
                        Date date = format.parse(dateString);  // Parse the date
                        Date limit = format.parse("2015-01-01");
                        // Proceed to parse and create StockDataset
                        StockDataset record = new StockDataset(
                                fileName,
                                companyName,
                                date,
                                Double.parseDouble(columns[1]),
                                Double.parseDouble(columns[2]),
                                Double.parseDouble(columns[3]),
                                Double.parseDouble(columns[4]),
                                Double.parseDouble(columns[5]),
                                Integer.parseInt(columns[6])
                        );
                        records.add(record);
                    } catch (Exception e) {
                        System.err.println("Error parsing date or data in line: " + line);
                    }
                } else {
                    System.err.println("Skipping malformed line (not enough columns): " + line);
                }
            }
        }
        System.out.println("Total records for " + fileName + ": " + records.size());
        return records;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Clean up database resources if needed
        System.out.println("Trading application shutting down...");
    }
}
