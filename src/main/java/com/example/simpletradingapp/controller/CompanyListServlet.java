package com.example.simpletradingapp.controller;

import com.example.simpletradingapp.db.DbUtil;
import com.example.simpletradingapp.model.Category;
import com.example.simpletradingapp.model.StockDataset;
import com.example.simpletradingapp.model.StockManager;

import com.example.simpletradingapp.utils.DateManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
/**
 * Displays a list of available stocks.
 */
@WebServlet("/company-list")
public class CompanyListServlet extends HttpServlet {

    private static StockManager sm;

    @Override
    public void init() {
        if (sm == null) {
            sm = new StockManager(); //Load CSVs
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Date fakeToday = DateManager.getFakeToday();
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            int uid = (int) session.getAttribute("userId");

            try (Connection conn = DbUtil.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("SELECT balance FROM Users WHERE user_id = ?");
                stmt.setInt(1, uid);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    double bal = rs.getDouble("balance");
                    req.setAttribute("balance", bal);
                }
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("balance", 0.0);
            }
        }


        List<Category> cats = sm.getAllCategories();
        List<StockDataset> stocks = new ArrayList<>();

        for (Category c : cats) {
            List<StockDataset> sList = sm.getStocksByCategory(c.getSymbol());
            sList.stream()
                    .filter(s -> s.getDate().before(fakeToday))
                    .max(Comparator.comparing(StockDataset::getDate))
                    .ifPresent(stocks::add);
        }

        req.setAttribute("cats", cats);
        req.setAttribute("stocks", stocks);
        req.getRequestDispatcher("/WEB-INF/company-list.jsp").forward(req, res);
    }
}
