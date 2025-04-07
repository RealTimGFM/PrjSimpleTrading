package com.example.simpletradingapp.controller;

import java.io.*;
import java.util.*;
import com.example.simpletradingapp.model.StockDataset;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet("/stock-servlet")
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
        List<StockDataset> StockDatasets =
                (List<StockDataset>) getServletContext().getAttribute("StockDatasets");

        if (StockDatasets == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "CSV data not available");
            return;
        }

        // Use the data in your response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>CSV Data</h1>");
        out.println("<table border='1'>");

        // Print headers
        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th>Name</th>");
        out.println("<th>Category</th>");
        out.println("<th>Value</th>");
        out.println("</tr>");

        // Print data rows with proper type handling
        for (StockDataset record : StockDatasets) {
            out.println("<tr>");
            out.println("<td>" + record.getId() + "</td>");
            out.println("<td>" + record.getName() + "</td>");
            out.println("<td>" + record.getDate() + "</td>");
            out.println("<td>" + record.get() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}