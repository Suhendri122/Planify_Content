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
import com.mycompany.planifycontent.TableUser;
import com.mycompany.planifycontent.database.User;
import com.mycompany.planifycontent.database.UserDAO;
import java.time.LocalDate;

public class EditUserController implements Initializable {

    @FXML
    private TextField userNameField;

    private TableUser user;

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    if (user != null) {
        userNameField.setText(user.getUser());
    }
}


    public void setUser(TableUser user) {
        this.user = user;
    }

    @FXML
    private void saveChanges(ActionEvent event) {
        try {
            String newName = userNameField.getText().trim();
            if (!newName.isEmpty()) {
                user.userProperty().set(newName);
                updateUser(user);
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
        if (user != null) {
                    userNameField.setText(user.getUser());
        }
        
        }
        
    @FXML
    private void cancelEdit(ActionEvent event) {
        closeWindow();
    }

    private void updateUser(TableUser user) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        userDAO.updateUser(user.getNo(), user.getUser());
    }

    private void closeWindow() {
        Stage stage = (Stage) userNameField.getScene().getWindow();
        stage.close();
    }
}
