package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.PlatformDAO;
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
import com.mycompany.planifycontent.TablePlatform;
import java.time.LocalDate;

public class EditPlatformController implements Initializable {

    @FXML
    private TextField platformNameField;

    private TablePlatform platform;

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    if (platform != null) {
        platformNameField.setText(platform.getPlatform());
    }
}


    public void setPlatform(TablePlatform platform) {
        this.platform = platform;
    }

    @FXML
    private void saveChanges(ActionEvent event) {
        try {
            String newName = platformNameField.getText().trim();
            if (!newName.isEmpty()) {
                platform.platformProperty().set(newName);
                updatePlatform(platform);
                closeWindow();
            } else {
                // Handle empty input
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
        }
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
        platformDAO.updatePlatform(platform.getNo(), platform.getPlatform());
    }

    private void closeWindow() {
        Stage stage = (Stage) platformNameField.getScene().getWindow();
        stage.close();
    }
}
