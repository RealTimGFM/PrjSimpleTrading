package com.example.simpletradingapp.controller;

import java.io.*;
import java.util.*;
import com.example.simpletradingapp.model.StockDataset;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet("/stonks")
public class StockServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Access the strongly-typed CSV data from the servlet context
        @SuppressWarnings("unchecked")
        Map<String, List<StockDataset>> allCsvData =
                (Map<String, List<StockDataset>>) getServletContext().getAttribute("allCsvData");

        if (allCsvData == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "CSV data not available");
            return;
        }

        //token
        HttpSession session = request.getSession();

        request.setAttribute("allCsvData", allCsvData);
        request.getRequestDispatcher("/WEB-INF/views/displayStocks.jsp").forward(request, response);

    }

    public void destroy() {
    }
}