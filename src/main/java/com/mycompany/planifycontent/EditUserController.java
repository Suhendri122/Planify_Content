package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.mycompany.planifycontent.TableUser;
import com.mycompany.planifycontent.database.UserDAO;

public class EditUserController implements Initializable {
    

    @FXML
    private TextField nameField;
    
    @FXML
    private TextField emailField;

    private TableUser user;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillForm();
    }

    public void setUser(TableUser user) {
        this.user = user;
        fillForm();  // Ensure fillForm is called after user is set
    }

    private void fillForm() {
        if (user != null) {
            System.out.println("Filling form with user: " + user.getUser());
            nameField.setText(user.getUser());
            emailField.setText(user.getEmail());
        } else {
            System.out.println("User is null");
        }
    }


    @FXML
    private void saveChanges(ActionEvent event) {
        try {
            String newName = nameField.getText().trim();
            String newEmail = emailField.getText().trim();
            if (!newName.isEmpty() && !newEmail.isEmpty()) {
                user.setUser(newName);
                user.setEmail(newEmail);
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
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
