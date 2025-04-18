package com.example.simpletradingapp.DAO.implement;
import com.example.simpletradingapp.DAO.DatasetDAO;
import com.example.simpletradingapp.db.DbUtil;
import com.example.simpletradingapp.model.Category;
import com.example.simpletradingapp.model.StockDataset;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class DatasetDAOImpl implements DatasetDAO {
    @Override
    public List<StockDataset> findBySymbol(String symbl) {
        List<StockDataset> outputSymb = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Dataset WHERE symbol = ?");
            stmt.setString(1, symbl);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Date date = rs.getDate("date");
                double open = rs.getDouble("open");
                double high = rs.getDouble("high");
                double low = rs.getDouble("low");
                double close = rs.getDouble("close");
                double adjClose = rs.getDouble("adj_close");
                int volume = rs.getInt("volume");
                Category cat = rs.getObject("category", Category.class);
                StockDataset dataset = new StockDataset(date, open, high, low, close, adjClose, volume, cat);
                outputSymb.add(dataset);
                return outputSymb;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error finding symbol: " + e.getMessage());
            return null;
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }

    public List<StockDataset> findCloseByDate(Date date) {
        List<StockDataset> results = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM Dataset WHERE date = ?"
            );
            stmt.setDate(1, (java.sql.Date) date);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                double open = rs.getDouble("open");
                double high = rs.getDouble("high");
                double low = rs.getDouble("low");
                double close = rs.getDouble("close");
                double adjClose = rs.getDouble("adj_close");
                int volume = rs.getInt("volume");
                Category cat = rs.getObject("category", Category.class);
                StockDataset dataset = new StockDataset(date, open, high, low, close, adjClose, volume, cat);
                results.add(dataset);
            }
            return results;
        } catch (SQLException e) {
            System.err.println("Error finding close values by date: " + e.getMessage());
            return results;
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }
    public StockDataset findCloseByDate(Date date, Category cat) {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM Dataset WHERE date = ? AND symbol = ?"
            );
            stmt.setDate(1, (java.sql.Date) date);
            stmt.setString(2, cat.getSymbol());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double open = rs.getDouble("open");
                double high = rs.getDouble("high");
                double low = rs.getDouble("low");
                double close = rs.getDouble("close");
                double adjClose = rs.getDouble("adj_close");
                int volume = rs.getInt("volume");
                StockDataset dataset = new StockDataset(date, open, high, low, close, adjClose, volume, cat);
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error finding close by date and symbol: " + e.getMessage());
            return null;
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }
}
