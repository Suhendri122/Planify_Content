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
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class EditProyekController {
    
    @FXML
    private TableView<TableProyek> tableView;
    
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
            txtHarga.setText(String.valueOf(proyek.getHarga()));
            dpTglMulai.setValue(LocalDate.parse(proyek.getTglMulai()));
            dpTglSelesai.setValue(LocalDate.parse(proyek.getTglSelesai()));
        }
    }

    @FXML
private void saveProyek() {
    try {
        proyek.setNamaProyek(txtNamaProyek.getText());
        proyek.setPicProyek(choicePicProyek.getValue());
        proyek.setNamaClient(choiceNamaClient.getValue());
        proyek.setNoTelepon(txtNoTelepon.getText());
        double harga = Double.parseDouble(txtHarga.getText());
        proyek.setHarga((int) harga);

        // Set tanggal mulai dan tanggal selesai
        proyek.setTglMulai(dpTglMulai.getValue().toString());
        proyek.setTglSelesai(dpTglSelesai.getValue().toString());

        System.out.println("Nama Proyek: " + proyek.getNamaProyek());
        System.out.println("Pic Proyek: " + proyek.getPicProyek());
        System.out.println("Nama Client: " + proyek.getNamaClient());
        System.out.println("No Telepon: " + proyek.getNoTelepon());
        System.out.println("Harga: " + proyek.getHarga());
        System.out.println("Tanggal Mulai: " + proyek.getTglMulai());
        System.out.println("Tanggal Selesai: " + proyek.getTglSelesai());

        // Mengambil ID dari user dan client
        int userId = proyekDAO.getUserIdByName(choicePicProyek.getValue());
        int clientId = proyekDAO.getClientIdByName(choiceNamaClient.getValue());
        proyek.setUserId(userId);
        proyek.setClientId(clientId);

        // Update proyek menggunakan DAO
        proyekDAO.updateProyek(proyek);

        // Tutup stage setelah data disimpan
        Stage stage = (Stage) txtNamaProyek.getScene().getWindow();
        stage.close();
    } catch (NumberFormatException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    refreshTable();
}



    @FXML
    private void cancelEdit() {
        Stage stage = (Stage) txtNamaProyek.getScene().getWindow();
        stage.close();
        refreshTable();
    }
    
    
    private void refreshTable() {
        if (tableView == null) {
            System.out.println("Error: tableView is null in refreshTable()");
            return;
        }
        
        try {
            Connection connection = DatabaseConnection.getConnection();
            ProyekDAO proyekDAO = new ProyekDAO(connection);
            List<TableProyek> proyekList = proyekDAO.getAllProyek("0", "0", "", ""); // Sesuaikan dengan kebutuhan Anda
            ObservableList<TableProyek> observableProyekList = FXCollections.observableArrayList(proyekList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
