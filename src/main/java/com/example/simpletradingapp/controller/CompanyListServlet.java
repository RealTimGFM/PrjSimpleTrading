package com.example.simpletradingapp.controller;

import com.example.simpletradingapp.model.Category;
import com.example.simpletradingapp.model.StockDataset;
import com.example.simpletradingapp.model.StockManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/company-list")
public class CompanyListServlet extends HttpServlet {

    private static StockManager sm;

    @Override
    public void init() {
        if (sm == null) {
            sm = new StockManager(); // 🔁 Load CSVs
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        List<Category> cats = sm.getAllCategories();
        List<StockDataset> stocks = new ArrayList<>();

        for (Category c : cats) {
            List<StockDataset> sList = sm.getStocksByCategory(c.getSymbol());
            sList.stream()
                    .max(Comparator.comparing(StockDataset::getDate))
                    .ifPresent(stocks::add);
        }

        req.setAttribute("cats", cats);
        req.setAttribute("stocks", stocks);
        req.getRequestDispatcher("/WEB-INF/company-list.jsp").forward(req, res);
    }
}
