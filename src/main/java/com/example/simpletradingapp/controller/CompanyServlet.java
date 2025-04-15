package com.example.simpletradingapp.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.simpletradingapp.model.StockManager;
import com.example.simpletradingapp.model.StockDataset;

@WebServlet("/stonks")
public class CompanyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private StockManager manager;

    @Override
    public void init() throws ServletException {
        // Initialize the inventory manager with sample data
        manager = new StockManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the action parameter (default to "list")
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        // Route to the appropriate action
        switch (action) {
            case "view":
                viewStock(request, response);
                break;
            case "category":
                listByCategory(request, response);
                break;
            case "list":
            default:
                listInventory(request, response);
                break;
        }
    }

    private void listInventory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set the stocks list as an attribute
        request.setAttribute("stocks", manager.getAllStockDatasets());
        request.setAttribute("categories", manager.getAllCategories());

        // Forward to the inventory list JSP
        request.getRequestDispatcher("/WEB-INF/company-list.jsp")
                .forward(request, response);
    }

    private void viewStock(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the stock ID
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                StockDataset stock = manager.getStockById(idParam);

                if (stock != null) {
                    // Set the stock as an attribute
                    request.setAttribute("stock", stock);

                    // Forward to the stock detail JSP
                    request.getRequestDispatcher("/WEB-INF/stock-details.jsp")
                            .forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                // Invalid ID format, just continue to show the inventory list
            }
        }

        // If we get here, the stock wasn't found or ID was invalid
        response.sendRedirect(request.getContextPath() + "/stonks");
    }

    private void listByCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the category ID
        String categoryIdParam = request.getParameter("symbol");

        if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
            try {

                // Set the filtered stocks as an attribute
                request.setAttribute("stocks", manager.getStocksByCategory(categoryIdParam));
                request.setAttribute("categories", manager.getAllCategories());
                request.setAttribute("selectedCategoryId", categoryIdParam);

                // Forward to the inventory list JSP
                request.getRequestDispatcher("/WEB-INF/company-list.jsp")
                        .forward(request, response);
                return;
            } catch (NumberFormatException e) {
                // Invalid ID format, just continue to show all inventory
            }
        }

        // If we get here, the category wasn't found or ID was invalid
        response.sendRedirect(request.getContextPath() + "/stonks");
    }
}
