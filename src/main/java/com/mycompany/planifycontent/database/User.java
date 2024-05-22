package com.mycompany.planifycontent.database;

public class User{
    private int id;
    private String email;
    private String password;

    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static User findUserByEmail(String email) {
        // Replace this with actual database query
        if (email.equals("user@example.com")) {
            return new User(1, "user@example.com", "password123");
        }
        return null;
    }
}