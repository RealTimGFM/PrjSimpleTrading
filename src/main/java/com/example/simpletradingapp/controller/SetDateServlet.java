package com.example.simpletradingapp.controller;

import com.example.simpletradingapp.utils.DateManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
/**
 * Admin-only servlet to update the fake current date used by the system.
 */
@WebServlet("/set-date")
public class SetDateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        String user = (session != null) ? (String) session.getAttribute("username") : null;

        // Only T can control time
        if (!"T".equals(user)) {
            res.sendRedirect("login.jsp");
            return;
        }

        String newDateStr = req.getParameter("newDate");
        try {
            Date newDate = Date.valueOf(newDateStr);
            DateManager.setFakeToday(newDate);
            res.sendRedirect("company-list");
        } catch (Exception e) {
            res.getWriter().println("Invalid date format.");
        }
    }
}
