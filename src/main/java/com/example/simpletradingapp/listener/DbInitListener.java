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
        System.out.println("Initializing database for College Manager...");

        SchemaInitializer.initializeSchema();

    //CSV INITIALIZER
        ServletContext context = sce.getServletContext();

        String[] fileNames = {
                "AAPL.csv", "AMD.csv", "AMZN.csv", "GOOG.csv",
                "MSFT.csv", "ORCL.csv", "PINS.csv", "TSLA.csv"
        };
        String[] companyNames = {"Apple", "Advanced Micro Devices", "Amazon", "Google", "Microsoft", "Oracle", "Pinterest", "Tesla"};

        Map<String, List<StockDataset>> allCsvData = new HashMap<>();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            String resourcePath = "db/data/" + fileName;

            try (InputStream is = classLoader.getResourceAsStream(resourcePath)) {
                if (is == null) {
                    context.log("Resource not found: " + resourcePath);
                    continue;
                }

                // Add the filename and companyNames to the StockDataset constructor
                List<StockDataset> csvRecords = loadCSVData(is, fileName, companyNames[i]);
                allCsvData.put(fileName, csvRecords);

                context.log("Loaded: " + fileName);
            } catch (Exception e) {
                context.log("Failed to load CSV: " + fileName, e);
            }
        }

        context.setAttribute("allCsvData", allCsvData);
    }

    private List<StockDataset> loadCSVData(InputStream is, String fileName, String companyName) throws IOException {
        List<StockDataset> records = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // Format to parse date

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            // Skip header line
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // Split csv into columns
                String[] columns = line.split(",");

                // Assuming the date is the first column
                String dateString = columns[0].trim();

                if (columns.length >= 8) {
                    try {
                        //String to Date
                        Date date = format.parse(dateString);  // Parse the date

                        // new StockDataset object
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
                }
            }
        }

        return records;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Clean up database resources if needed
        System.out.println("Trading application shutting down...");
    }
}
