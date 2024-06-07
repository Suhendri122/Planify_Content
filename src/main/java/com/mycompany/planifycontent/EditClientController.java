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
import com.mycompany.planifycontent.TableClient;
import com.mycompany.planifycontent.database.ClientDAO;
import java.time.LocalDate;

public class EditClientController implements Initializable {

    @FXML
    private TextField clientNameField;

    private TableClient client;

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    if (client != null) {
        clientNameField.setText(client.getNama());
    }
}


    public void setClient(TableClient client) {
        this.client = client;
    }

    @FXML
    private void saveChanges(ActionEvent event) {
        try {
            String newName = clientNameField.getText().trim();
            if (!newName.isEmpty()) {
                client.namaProperty().set(newName);
                updateClient(client);
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
        if (client != null) {
                    clientNameField.setText(client.getNama());
        }
        
        }
        
    @FXML
    private void cancelEdit(ActionEvent event) {
        closeWindow();
    }

    private void updateClient(TableClient client) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        ClientDAO clientDAO = new ClientDAO(connection);
        clientDAO.updateClient(client.getNo(), client.getNama(), client.getNo_telp(), client.getUsaha());
    }

    private void closeWindow() {
        Stage stage = (Stage) clientNameField.getScene().getWindow();
        stage.close();
    }
}
