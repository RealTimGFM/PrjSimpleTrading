package com.example.simpletradingapp.DAO;

import com.example.simpletradingapp.model.User;

public interface UserDAO {
    /**
     * Find a user by their username or email.
     *
     * @param usernameOrEmail the username or email to search
     * @return the User object if found, otherwise null
     */
    User findByUsernameOrEmail(String usernameOrEmail);
    /**
     * Get a user by their ID.
     *
     * @param userId
     * @return the User object if found, otherwise null
     */
    User getUserById(int userId);
    /**
     * Update the balance for a given user.
     *
     * @param userId
     * @param newBalance the new balance to set, not a shoes brand =_=
     * @return true if updated successfully, false otherwise
     */
    boolean updateBalance(int userId, double newBalance);
    /**
     * Create a new user and put it into the database.
     *
     * @param user the User object to insert
     * @return true if created successfully, false otherwise
     */
    boolean createUser(User user);
}