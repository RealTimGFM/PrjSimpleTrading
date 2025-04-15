package com.example.simpletradingapp.model;

import java.sql.Timestamp;
import java.util.*;

//Class for .csv Dataset in /resources
public class StockDataset {
    ///FIELDS
    private String stockId;
    private String symbol;
    private String name;
    private Date date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double adj_close;
    private int volume;
    private static String lastId = "000000";
    private Category category;

    ///CONSTRUCTOR
    //basic constructor
    public StockDataset() {}
    //constructor with fields
    public StockDataset(Date date, double open, double high, double low, double close, double adjClose, int volume, Category category) {
        this.stockId = createId();
        this.date = date;
        this.high = high;
        this.low = low;
        this.close = close;
        adj_close = adjClose;
        this.open = open;
        this.volume = volume;
    }

    ///PROPERTIES : getters and setters
    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}

    public double getHigh() {return high;}
    public void setHigh(double high) {this.high = high;}

    public double getLow() {return low;}
    public void setLow(double low) {this.low = low;}

    public double getClose() {return close;}
    public void setClose(double close) {this.close = close;}

    public double getAdjClose() {return adj_close;}
    public void setAdjClose(double adj_close) {this.adj_close = adj_close;}

    public double getOpen() {return open;}
    public void setOpen(double open) {this.open = open;}

    public int getVolume() {return volume;}
    public void setVolume(int volume) {this.volume = volume;}

    public String getStockId() {return stockId;}
    public void setStockId(String stockId) {this.stockId = stockId;}

    public Category getCategory() {return category;}
    public void setCategory(Category category) {
        this.category = category;
    }

    /// METHODS
    ///
    private String createId() {
        // Convert the lasId from base-36 to a decimal number
        long decimalValue = Long.parseLong(lastId, 36);
        decimalValue++;

        // unconvert decimalValue + toUpper() letters
        String newId = Long.toString(decimalValue, 36).toUpperCase();
        // Pad with leading zeros to ensure it's always 6 characters
        lastId = String.format("%3s", newId).replace(' ', '0');
        return lastId;
    }
    // Admin updates stock history (for testing)
    public void updateDate(java.sql.Date newDate) {
        this.date = newDate;
        System.out.println("Stock history date changed to: " + newDate);
    }
    // Update stock price (used by admin or system)
    public void updatePrice(double newPrice) {
        this.close = newPrice;
        this.date = new Timestamp(System.currentTimeMillis());
        System.out.println("Stock " + getCategory().getSymbol()+ " price updated to $" + newPrice);
    }

    //display information about this class
    @Override
    public String toString() {
        return "CsvInfo{" + "Stock Id: "+ this.stockId + ", symbol: " + category.getSymbol() + ", name: " + category.getSymbol() + ", date: " + this.date + ", high: " + this.high + ", low: " + this.low + ", close: " + this.close;
    }

}

