package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import com.mycompany.planifycontent.database.ProyekDAO;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class TambahProyekController {

    @FXML
    private TextField txtNamaProyek;
    
    @FXML
    private TableView<TableProyek> tableView;

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

    private ProyekDAO proyekDAO;
    private TableProyek proyek;
    private ObservableList<TableProyek> proyekData;

    @FXML
    private void initialize() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            proyekDAO = new ProyekDAO(connection);
            List<String> users = proyekDAO.getAllUsers();
            List<String> clients = proyekDAO.getAllClients();
            choicePicProyek.setItems(FXCollections.observableArrayList(users));
            choiceNamaClient.setItems(FXCollections.observableArrayList(clients));
            proyekData = FXCollections.observableArrayList();
            
            dpTglMulai.setValue(LocalDate.now());
            dpTglSelesai.setValue(LocalDate.now());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void setProyek(TableProyek proyek) {
        this.proyek = proyek;

        if (txtNoTelepon != null) {
            choiceNamaClient.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    try {
                        if (proyekDAO != null) {
                            txtNoTelepon.setText(proyekDAO.getPhoneNumberByClientName(newValue));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML
    private void saveProyek() {
        try {
            String namaProyek = txtNamaProyek.getText();
            String picProyek = choicePicProyek.getValue();
            String namaClient = choiceNamaClient.getValue();
            String noTelepon = txtNoTelepon.getText();
            double harga = Double.parseDouble(txtHarga.getText());
            LocalDate tglMulai = dpTglMulai.getValue();
            LocalDate tglSelesai = dpTglSelesai.getValue();

            int userId = proyekDAO.getUserIdByName(picProyek);
            int clientId = proyekDAO.getClientIdByName(namaClient);

            TableProyek proyekBaru = new TableProyek(0, userId, clientId, namaProyek, picProyek, namaClient, noTelepon, harga, tglMulai.toString(), tglSelesai.toString(), null);

            proyekDAO.addProyek(proyekBaru);

            Stage stage = (Stage) txtNamaProyek.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void popupBtnBatal(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
@FXML
private void handleTambah(ActionEvent event) {
    String namaProyek = txtNamaProyek.getText().trim();
    String picProyek = choicePicProyek.getValue();
    String namaClient = choiceNamaClient.getValue();
    String hargaText = txtHarga.getText().trim();
    LocalDate tglMulai = dpTglMulai.getValue();
    LocalDate tglSelesai = dpTglSelesai.getValue();

    if (namaProyek.isEmpty() || picProyek == null || namaClient == null || hargaText.isEmpty() || tglMulai == null || tglSelesai == null) {
        showErrorMessage("Peringatan", "Harap Lengkapi Semua Kolom Terlebih Dahulu!");
        return;
    }

    double harga = 0.0;
    try {
        harga = Double.parseDouble(hargaText);
    } catch (NumberFormatException e) {
        showErrorMessage("Peringatan", "Masukkan Harga dalam Format Angka yang Benar!");
        return;
    }

    try {
        String noTelepon = "";
        if (!namaClient.isEmpty()) {
            noTelepon = proyekDAO.getPhoneNumberByClientName(namaClient);
        }
        int userId = proyekDAO.getUserIdByName(picProyek);
        int clientId = proyekDAO.getClientIdByName(namaClient);

        TableProyek proyekBaru = new TableProyek(0, userId, clientId, namaProyek, picProyek, namaClient, noTelepon, harga, tglMulai.toString(), tglSelesai.toString(), null);
        proyekDAO.addProyek(proyekBaru);
        Stage stage = (Stage) txtNamaProyek.getScene().getWindow();
        stage.close();

        refreshTable();

    } catch (SQLException e) {
        e.printStackTrace();
        showErrorMessage("Error adding project", "Terjadi kesalahan saat menambahkan proyek. Silakan coba lagi.");
    }
}

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void refreshTable() {
        if (tableView == null) {
            System.out.println("Error: tableView is null in refreshTable()");
            return;
        }
        
        try {
            Connection connection = DatabaseConnection.getConnection();
            ProyekDAO proyekDAO = new ProyekDAO(connection);
            List<TableProyek> proyekList = proyekDAO.getAllProyek("0", "0", "", ""); 
            ObservableList<TableProyek> observableProyekList = FXCollections.observableArrayList(proyekList);
            updateProyekIds(observableProyekList);
            tableView.setItems(observableProyekList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateProyekIds(List<TableProyek> proyekList) {
        for (int i = 0; i < proyekList.size(); i++) {
            proyekList.get(i).setId(i + 1);
        }
    }
}
