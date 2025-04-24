package com.example.simpletradingapp.model;

/**
 * Abstract base class for all people in the system.
 */
public abstract class Person {
    protected int userId;
    protected String username;
    protected String email;

    public Person(int userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}
