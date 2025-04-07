package com.example.simpletradingapp.model;

import java.util.*;
//Class for .csv Dataset
public class StockDataset {
    //Fields
    private String id;
    private String name;
    private Date date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double adj_close;
    private int volume;

    //Constructor
    public StockDataset(String id, String name, Date date, double open, double high, double low, double close, double adjClose, int volume) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.high = high;
        this.low = low;
        this.close = close;
        adj_close = adjClose;
        this.open = open;
        this.volume = volume;
    }


    //getters and setters
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}

    public double getHigh() {return high;}
    public void setHigh(double high) {this.high = high;}

    public double getLow() {return low;}
    public void setLow(double low) {this.low = low;}

    public double getClose() {return close;}
    public void setClose(double close) {this.close = close;}

    public double getAdj_close() {return adj_close;}
    public void setAdj_close(double adj_close) {this.adj_close = adj_close;}

    public double getOpen() {return open;}
    public void setOpen(double open) {this.open = open;}

    public int getVolume() {return volume;}
    public void setVolume(int volume) {this.volume = volume;}

    //display information about this class
    @Override
    public String toString() {
        return "CsvInfo{" + "id: " + this.id + ", name: " + this.name + ", date: " + this.date + ", high: " + this.high + ", low: " + this.low + ", close: " + this.close;
    }
}

