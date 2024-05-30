package com.mycompany.planifycontent.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Informasi koneksi database
    private static final String URL = "jdbc:mysql://localhost:3306/planify_content";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    // Metode untuk mendapatkan koneksi ke database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
