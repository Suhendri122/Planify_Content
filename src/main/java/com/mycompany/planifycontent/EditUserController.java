package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import com.mycompany.planifycontent.database.UserDAO;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        fillForm();
    }

    private void fillForm() {
        if (user != null) {
            nameField.setText(user.getUser());
            emailField.setText(user.getEmail());
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
                System.out.println("Please fill in all fields.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
