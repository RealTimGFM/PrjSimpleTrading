package com.example.simpletradingapp.DAO;

import com.example.simpletradingapp.model.User;

public interface UserDAO {
    User findByUsernameOrEmail(String usernameOrEmail);
    User getUserById(int userId);
    boolean updateBalance(int userId, double newBalance);
    boolean createUser(User user);
}