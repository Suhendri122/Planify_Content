package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.mycompany.planifycontent.database.MediaDAO;
import com.mycompany.planifycontent.TableMedia;
import java.time.LocalDate;

public class EditMediaController implements Initializable {

    @FXML
    private TextField mediaNameField;

    private TableMedia media;

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    if (media != null) {
        mediaNameField.setText(media.getMedia());
    }
}


    public void setMedia(TableMedia media) {
        this.media = media;
    }

    @FXML
    private void saveChanges(ActionEvent event) {
        try {
            String newName = mediaNameField.getText().trim();
            if (!newName.isEmpty()) {
                media.mediaProperty().set(newName);
                updateMedia(media);
                closeWindow();
            } else {
                // Handle empty input
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
        }
    }
    
        void fillForm() {
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
}
