package com.mycompany.planitfycontent;

import com.mycompany.planitfycontent.App;
import com.mycompany.planitfycontent.database.DatabaseConnection;
import com.mycompany.planitfycontent.database.DatabaseConnection;
import com.mycompany.planitfycontent.database.HandleLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController {

    @FXML
     TextField txtEmail;

    @FXML
    PasswordField txtPassword;

    @FXML
    private void initialize() {
        // Tidak perlu menambahkan event handler di sini karena kita sudah menambahkannya di file FXML
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        HandleLogin coba = new HandleLogin();

        // Koneksi ke database
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Kueri untuk memeriksa apakah email dan password sesuai
            
            if(coba.handleLogin(event, txtEmail, txtPassword)) {
                App.setRoot("dashboard");
            }
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

    // Validasi email menggunakan ekspresi reguler
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Validasi password
    private boolean isValidPassword(String password) {
        // Pastikan password memiliki panjang minimal 8 karakter
        return password.length() >= 8;
    }

    // Menampilkan alert untuk pesan kesalahan
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
