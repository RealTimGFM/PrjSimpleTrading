package com.example.simpletradingapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/ProcessParams")
public class ProcessParams extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("ProcessParams servlet received a request");

        // 1. Get current time and add as request attribute
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        request.setAttribute("timestamp", timestamp);

        // 2. Extract individual parameters
        String name = request.getParameter("name");
        String role = request.getParameter("role");
        String message = request.getParameter("message");

        // Set default values if parameters are missing
        if (name == null || name.isEmpty()) {
            name = "Guest";
        }
        if (role == null || role.isEmpty()) {
            role = "Visitor";
        }
        if (message == null || message.isEmpty()) {
            message = "No message provided";
        }

        // 3. Add processed parameters as request attributes
        request.setAttribute("processedName", name.toUpperCase());
        request.setAttribute("processedRole", role);
        request.setAttribute("processedMessage", message);

        // 4. Collect all parameter names
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, String> allParams = new HashMap<>();

        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            allParams.put(paramName, paramValue);
        }

        request.setAttribute("allParams", allParams);

        // 5. Get query string and request URL
        String queryString = request.getQueryString();
        String requestURL = request.getRequestURL().toString();

        request.setAttribute("queryString", queryString);
        request.setAttribute("requestURL", requestURL);

        // 6. Forward to JSP for display
        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }
}
