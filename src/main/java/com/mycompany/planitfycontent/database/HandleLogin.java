package com.mycompany.planitfycontent.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.planitfycontent.App; // Tambahkan impor untuk kelas App
import com.mycompany.planitfycontent.App;
import com.mycompany.planitfycontent.database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class HandleLogin {
    public boolean handleLogin(ActionEvent event, TextField txtEmail, PasswordField txtPassword) {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        // Koneksi ke database
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Kueri untuk memeriksa apakah email dan password sesuai
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // Jika ditemukan, login berhasil
                    try {
                        // Pindah ke layar dashboard
                        App.setRoot("dashboard");
                    } catch (IOException e) {
                        // Tangani kesalahan jika terjadi saat pindah ke layar dashboard
                        showErrorAlert("Error", "Failed to load dashboard screen.");
                        e.printStackTrace();
                    }
                } else {
                    // Jika tidak ditemukan, tampilkan pesan kesalahan
                    showErrorAlert("Error", "Invalid email or password. Please try again.");
                }
            }
        } catch (SQLException e) {
            // Tangani kesalahan koneksi database
            showErrorAlert("Error", "Failed to connect to database.");
            e.printStackTrace();
        }
    }

    // Menampilkan alert untuk pesan kesalahan
    private static void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
