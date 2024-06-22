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
    
    @FXML
    private TextField clientNoTelpField;
    
    @FXML
    private TextField clientUsahaField;

    private TableClient client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (client != null) {
            clientNameField.setText(client.getNama());
        }
    }


    public void setClient(TableClient client) {
        this.client = client;
        fillForm();  
    }

    @FXML
    private void saveChanges(ActionEvent event) {
        try {
            String newName = clientNameField.getText().trim();
            String newNoTelp = clientNoTelpField.getText().trim();
            String newUsaha = clientUsahaField.getText().trim();

            if (!newName.isEmpty() && !newNoTelp.isEmpty() && !newUsaha.isEmpty()) {
                client.setNama(newName);
                client.setNo_telp(newNoTelp);
                client.setUsaha(newUsaha);
                updateClient(client);
                closeWindow();
            } else {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void fillForm() {
        if (client != null) {
            clientNameField.setText(client.getNama());
            clientNoTelpField.setText(client.getNo_telp());
            clientUsahaField.setText(client.getUsaha());
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
