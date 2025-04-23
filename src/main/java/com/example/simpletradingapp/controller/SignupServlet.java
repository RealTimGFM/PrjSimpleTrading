package com.example.simpletradingapp.controller;

import com.example.simpletradingapp.db.DbUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String user = req.getParameter("username");
        String email = req.getParameter("email");
        String pw = req.getParameter("password");

        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Users (username, email, password_hash) VALUES (?, ?, ?)");
            stmt.setString(1, user);
            stmt.setString(2, email);
            stmt.setString(3, pw);

            stmt.executeUpdate();
            res.sendRedirect("login.jsp");
        } catch (SQLException e) {
            req.setAttribute("error", "Username or email already exists.");
            req.getRequestDispatcher("signup.jsp").forward(req, res);
        }
    }
}