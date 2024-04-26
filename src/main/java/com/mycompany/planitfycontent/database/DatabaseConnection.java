package com.mycompany.planitfycontent.database;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/planify_content";
    private static final String USER = "root";
    private static final String PASSWORD = "112233";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
