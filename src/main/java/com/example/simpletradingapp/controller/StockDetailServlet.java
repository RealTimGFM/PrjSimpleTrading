package com.example.simpletradingapp.controller;

import com.example.simpletradingapp.DAO.implement.DatasetDAOImpl;
import com.example.simpletradingapp.model.StockDataset;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet("/stock-detail")
public class StockDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String symbol = request.getParameter("symbol");
        System.out.println(" Requested symbol = " + symbol);

        if (symbol == null || symbol.isEmpty()) {
            request.setAttribute("error", "No stock symbol provided!");
            request.getRequestDispatcher("/WEB-INF/stock-details.jsp").forward(request, response);
            return;
        }

        DatasetDAOImpl dao = new DatasetDAOImpl();
        List<StockDataset> stockList = dao.findBySymbol(symbol.toUpperCase());
        System.out.println("🔍 Got stockList size = " + (stockList != null ? stockList.size() : "null"));

        if (stockList == null || stockList.isEmpty()) {
            request.setAttribute("error", "No data found for symbol: " + symbol);
        } else {
            // ✅ Get latest stock (by most recent date)
            StockDataset latest = stockList.stream()
                    .max(Comparator.comparing(StockDataset::getDate))
                    .orElse(null);

            request.setAttribute("stock", latest);
        }

        request.getRequestDispatcher("/WEB-INF/stock-details.jsp").forward(request, response);

    }
}
