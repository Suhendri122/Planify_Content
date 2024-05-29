package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import com.mycompany.planifycontent.database.ProyekDAO;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;

public class EditProyekController {

    @FXML
    private TextField txtNamaProyek;

    @FXML
    private ChoiceBox<String> choicePicProyek;

    @FXML
    private ChoiceBox<String> choiceNamaClient;

    @FXML
    private TextField txtNoTelepon;

    @FXML
    private TextField txtHarga;

    @FXML
    private DatePicker dpTglMulai;

    @FXML
    private DatePicker dpTglSelesai;

    private TableProyek proyek;
    private ProyekDAO proyekDAO;

    @FXML
    private void initialize() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            proyekDAO = new ProyekDAO(connection);
            List<String> users = proyekDAO.getAllUsers();
            List<String> clients = proyekDAO.getAllClients();
            choicePicProyek.setItems(FXCollections.observableArrayList(users));
            choiceNamaClient.setItems(FXCollections.observableArrayList(clients));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setProyek(TableProyek proyek) {
        this.proyek = proyek;
        fillForm();

        // Add listener to update phone number based on selected client
        choiceNamaClient.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    txtNoTelepon.setText(proyekDAO.getPhoneNumberByClientName(newValue));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fillForm() {
        if (proyek != null) {
            txtNamaProyek.setText(proyek.getNamaProyek());
            choicePicProyek.setValue(proyek.getPicProyek());
            choiceNamaClient.setValue(proyek.getNamaClient());
            txtNoTelepon.setText(proyek.getNoTelepon());
            txtHarga.setText(proyek.getHarga());
            dpTglMulai.setValue(LocalDate.parse(proyek.getTglMulai()));
            dpTglSelesai.setValue(LocalDate.parse(proyek.getTglSelesai()));
        }
    }

    @FXML
    private void saveProyek() {
        proyek.setNamaProyek(txtNamaProyek.getText());
        proyek.setPicProyek(choicePicProyek.getValue());
        proyek.setNamaClient(choiceNamaClient.getValue());
        proyek.setNoTelepon(txtNoTelepon.getText());
        proyek.setHarga(txtHarga.getText());
        proyek.setTglMulai(dpTglMulai.getValue().toString());
        proyek.setTglSelesai(dpTglSelesai.getValue().toString());

        try {
            int userId = proyekDAO.getUserIdByName(choicePicProyek.getValue());
            int clientId = proyekDAO.getClientIdByName(choiceNamaClient.getValue());
            proyek.setUserId(userId);
            proyek.setClientId(clientId);
            proyekDAO.updateProyek(proyek);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) txtNamaProyek.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelEdit() {
        Stage stage = (Stage) txtNamaProyek.getScene().getWindow();
        stage.close();
    }
}
