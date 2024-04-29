package com.mycompany.planitfycontent.database;

public class Users {
    private int id;
    private String email;
    private String password;

    public Users(int id, String email, String password) {
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

    public static Users findUserByEmail(String email) {
        // Replace this with actual database query
        if (email.equals("user@example.com")) {
            return new Users(1, "user@example.com", "password123");
        }
        return null;
    }
}