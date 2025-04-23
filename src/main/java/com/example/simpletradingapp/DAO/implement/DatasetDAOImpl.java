package com.example.simpletradingapp.DAO.implement;

import com.example.simpletradingapp.DAO.DatasetDAO;
import com.example.simpletradingapp.db.DbUtil;
import com.example.simpletradingapp.model.Category;
import com.example.simpletradingapp.model.StockDataset;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatasetDAOImpl implements DatasetDAO {

    @Override
    public List<StockDataset> findBySymbol(String symbl) {
        List<StockDataset> result = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM StockDataset WHERE symbol = ?");
            stmt.setString(1, symbl);
            System.out.println("🔎 SQL query for symbol = " + symbl);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Date date = rs.getDate("date");
                double open = rs.getDouble("open");
                double high = rs.getDouble("high");
                double low = rs.getDouble("low");
                double close = rs.getDouble("close");
                double adjClose = rs.getDouble("adj_close");
                int volume = rs.getInt("volume");

                String symbol = rs.getString("symbol");
                String name = rs.getString("name");
                Category cat = new Category(symbol, name);

                StockDataset stock = new StockDataset(date, open, high, low, close, adjClose, volume, cat);
                result.add(stock);
            }

            return result;

        } catch (SQLException e) {
            System.err.println("Error finding symbol: " + e.getMessage());
            return null;
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }

    @Override
    public StockDataset findCloseByDate(Date date, Category cat) {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();

            System.out.println("? SQL = SELECT * FROM StockDataset WHERE date <= " + date + " AND symbol = " + cat.getSymbol());

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM StockDataset " +
                            "WHERE date <= ? AND symbol = ? " +
                            "ORDER BY date DESC LIMIT 1"
            );
            stmt.setDate(1, new java.sql.Date(date.getTime()));
            stmt.setString(2, cat.getSymbol());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Date realDate = rs.getDate("date");
                double open = rs.getDouble("open");
                double high = rs.getDouble("high");
                double low = rs.getDouble("low");
                double close = rs.getDouble("close");
                double adjClose = rs.getDouble("adj_close");
                int volume = rs.getInt("volume");

                System.out.println("? Executed. Has data? true ✅");
                String stockId = rs.getString("stockID"); // the actual one from the DB
                StockDataset stock = new StockDataset(realDate, open, high, low, close, adjClose, volume, cat);
                stock.setStockId(stockId); // ← THIS IS THE CRUCIAL LINE
                return stock;
            } else {
                System.out.println("? Executed. Has data? false ❌");
            }

            return null;

        } catch (SQLException e) {
            System.err.println("❌ Error finding close by date and symbol: " + e.getMessage());
            return null;
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }
    public String findSymbolByStockId(String stockId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT symbol FROM StockDataset WHERE stockID = ?"
            );
            stmt.setString(1, stockId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("symbol");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
