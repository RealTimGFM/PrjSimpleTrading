package com.example.simpletradingapp.controller;

import com.example.simpletradingapp.DAO.implement.DatasetDAOImpl;
import com.example.simpletradingapp.model.StockDataset;

import com.example.simpletradingapp.utils.DateManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/stock-detail")
public class StockDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String symbol = request.getParameter("symbol");

        if (symbol == null || symbol.isEmpty()) {
            request.setAttribute("error", "No stock symbol provided!");
            request.getRequestDispatcher("/WEB-INF/stock-details.jsp").forward(request, response);
            return;
        }

        DatasetDAOImpl dao = new DatasetDAOImpl();
        List<StockDataset> stockList = dao.findBySymbol(symbol.toUpperCase());

        if (stockList == null || stockList.isEmpty()) {
            request.setAttribute("error", "No data found for symbol: " + symbol);
        } else {
            //assume date
            Date fakeToday = DateManager.getFakeToday();

            //Show latest stock before fake today
            StockDataset latestBeforeToday = stockList.stream()
                    .filter(s -> s.getDate().before(fakeToday))
                    .max(Comparator.comparing(StockDataset::getDate))
                    .orElse(null);

            //Filter history to only show data before 2017-01-01
            List<StockDataset> filteredHistory = stockList.stream()
                    .filter(s -> s.getDate().before(fakeToday))
                    .collect(Collectors.toList());

            request.setAttribute("stock", latestBeforeToday);
            request.setAttribute("historyList", filteredHistory);
        }

        request.getRequestDispatcher("/WEB-INF/stock-details.jsp").forward(request, response);
    }
}
