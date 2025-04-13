package com.example.simpletradingapp.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.simpletradingapp.model.InventoryManager;
import com.example.simpletradingapp.model.InventoryItem;

@WebServlet("/company")
public class CompanyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private InventoryManager manager;

    @Override
    public void init() throws ServletException {
        // Initialize the inventory manager with sample data
        manager = new InventoryManager();
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
                viewItem(request, response);
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
        // Set the items list as an attribute
        request.setAttribute("items", manager.getAllItems());
        request.setAttribute("categories", manager.getAllCategories());

        // Forward to the inventory list JSP
        request.getRequestDispatcher("/WEB-INF/inventory-list.jsp")
                .forward(request, response);
    }

    private void viewItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the item ID
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long itemId = Long.parseLong(idParam);
                InventoryItem item = manager.getItemById(itemId);

                if (item != null) {
                    // Set the item as an attribute
                    request.setAttribute("item", item);

                    // Forward to the item detail JSP
                    request.getRequestDispatcher("/WEB-INF/item-detail.jsp")
                            .forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                // Invalid ID format, just continue to show the inventory list
            }
        }

        // If we get here, the item wasn't found or ID was invalid
        response.sendRedirect(request.getContextPath() + "/inventory");
    }

    private void listByCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the category ID
        String categoryIdParam = request.getParameter("id");

        if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
            try {
                Long categoryId = Long.parseLong(categoryIdParam);

                // Set the filtered items as an attribute
                request.setAttribute("items", manager.getItemsByCategory(categoryId));
                request.setAttribute("categories", manager.getAllCategories());
                request.setAttribute("selectedCategoryId", categoryId);

                // Forward to the inventory list JSP
                request.getRequestDispatcher("/WEB-INF/inventory-list.jsp")
                        .forward(request, response);
                return;
            } catch (NumberFormatException e) {
                // Invalid ID format, just continue to show all inventory
            }
        }

        // If we get here, the category wasn't found or ID was invalid
        response.sendRedirect(request.getContextPath() + "/inventory");
    }
}
