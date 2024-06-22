package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import com.mycompany.planifycontent.database.MediaDAO;
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

public class EditMediaController implements Initializable {

    @FXML
    private TextField mediaNameField;

    private TableMedia media;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillForm();
    }

    @FXML
    private void saveChanges(ActionEvent event) {
        try {
            String newName = mediaNameField.getText().trim();
            if (!newName.isEmpty()) {
                media.setMedia(newName);
                updateMedia(media);
                closeWindow();
            } else {
                showAlert("Input Error", "Media name cannot be empty.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to update the media.");
        }
    }

    public void setMedia(TableMedia media) {
        this.media = media;
        fillForm(); // Mengisi data saat media diatur
    }

    private void fillForm() {
        if (media != null) {
            mediaNameField.setText(media.getMedia());
        }
    }

    @FXML
    private void cancelEdit(ActionEvent event) {
        closeWindow();
    }

    private void updateMedia(TableMedia media) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        MediaDAO mediaDAO = new MediaDAO(connection);
        mediaDAO.updateMedia(media.getNo(), media.getMedia());
    }

    private void closeWindow() {
        Stage stage = (Stage) mediaNameField.getScene().getWindow();
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
