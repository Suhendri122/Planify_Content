package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import com.mycompany.planifycontent.database.PlatformDAO;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditPlatformController implements Initializable {

    @FXML
    private TextField platformNameField;

    private TablePlatform platform;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillForm();
    }

    @FXML
    private void saveChanges(ActionEvent event) {
        try {
            String newName = platformNameField.getText().trim();
            if (!newName.isEmpty()) {
                platform.setPlatform(newName);
                updatePlatform(platform);
                closeWindow();
            } else {
                showAlert("Input Error", "Platform name cannot be empty.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to update the platform.");
        }
    }

    public void setPlatform(TablePlatform platform) {
        this.platform = platform;
        fillForm();
    }

    private void fillForm() {
        if (platform != null) {
            platformNameField.setText(platform.getPlatform());
        }
    }

    @FXML
    private void cancelEdit(ActionEvent event) {
        closeWindow();
    }

    private void updatePlatform(TablePlatform platform) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PlatformDAO platformDAO = new PlatformDAO(connection);
        platformDAO.updatePlatform(platform.getId(), platform.getPlatform());
    }

    private void closeWindow() {
        Stage stage = (Stage) platformNameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
