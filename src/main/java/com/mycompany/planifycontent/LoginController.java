package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends App {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    public void handleLogin(ActionEvent event) {
        try {
            Connection connection = DatabaseConnection.getConnection(); // Mengambil koneksi dari database
            String query = "SELECT * FROM user WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, txtEmail.getText());
            preparedStatement.setString(2, txtPassword.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) { // Jika query mengembalikan hasil
                setRoot("dashboard"); // Pindah ke halaman dashboard
                
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Gagal", "Email atau password salah!");
            }

            connection.close(); // Tutup koneksi
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Kesalahan", "Terjadi kesalahan saat login.");
        }
    }
    
    @FXML
    private static void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
