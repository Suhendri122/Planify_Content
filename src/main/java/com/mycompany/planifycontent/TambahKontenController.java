package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import com.mycompany.planifycontent.database.KontenDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TambahKontenController {

    @FXML
    private TableView<TableKonten> tableView;

    @FXML
    private TextField temaField;

    @FXML
    private ChoiceBox<String> mediaChoice;

    @FXML
    private ChoiceBox<String> platformChoice;

    @FXML
    private TextField linkField;

    @FXML
    private DatePicker dpTglPost;

    @FXML
    private ChoiceBox<String> picKontenField;

    @FXML
    private DatePicker dpDeadline;

    @FXML
    private ChoiceBox<String> statusField;

    private KontenDAO kontenDAO;
    private ObservableList<TableKonten> kontenData;
    private TableKonten konten;
    
    public enum Status {
        Belum,
        Berjalan,
        Selesai
    }


    @FXML
    private void initialize() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            kontenDAO = new KontenDAO(connection);

            // Populate choice boxes with data from DAO
            List<String> mediaList = kontenDAO.getAllMedia();
            List<String> platformList = kontenDAO.getAllPlatforms();
            List<String> picKontenList = kontenDAO.getAllUsers(); // Assuming this method retrieves all users
            List<String> statusList = getStatusList(); 

            mediaChoice.setItems(FXCollections.observableArrayList(mediaList));
            platformChoice.setItems(FXCollections.observableArrayList(platformList));
            picKontenField.setItems(FXCollections.observableArrayList(picKontenList));
            statusField.setItems(FXCollections.observableArrayList(statusList)); // Set String list to statusField

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }
    
    private List<String> getStatusList() {
        // Convert enum Status values to String
        return List.of(Status.Belum.toString(), Status.Berjalan.toString(), Status.Selesai.toString());
    }
    
    public void setKonten(TableKonten konten) {
        this.konten = konten;
    }

    @FXML
    private void popupBtnBatal() {
        Stage stage = (Stage) temaField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleTambah() {
        try {
            String tema = temaField.getText().trim();
            String media = mediaChoice.getValue();
            String platform = platformChoice.getValue();
            String link = linkField.getText().trim();
            LocalDate tglPost = dpTglPost.getValue();
            String picKonten = picKontenField.getValue();
            LocalDate deadline = dpDeadline.getValue();
            String statusString = statusField.getValue();
            String status = statusField.getValue();

            // Validate input fields
            if (tema.isEmpty() || media == null || platform == null || link.isEmpty() || tglPost == null || picKonten == null || deadline == null || status == null) {
                showErrorMessage("Peringatan", "Harap lengkapi semua kolom.");
                return;
            }

            kontenDAO.insertKonten(picKonten, media, platform, link, tema, deadline.toString(), tglPost.toString(), statusString);
            Stage stage = (Stage) temaField.getScene().getWindow();
            stage.close();
            
            refreshTable();

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessage("Error", "Terjadi kesalahan saat menyimpan konten. Silakan coba lagi.");
        }
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfoMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
            List<TableKonten> kontenList = kontenDAO.getAllKontens();
            kontenData = FXCollections.observableArrayList(kontenList);
            ObservableList<TableKonten> observableKontenList = FXCollections.observableArrayList(kontenList);
            tableView.setItems(kontenData);
            updateKontenIds(observableKontenList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateKontenIds(List<TableKonten> kontenList) {
        for (int i = 0; i < kontenList.size(); i++) {
            kontenList.get(i).setId(i + 1);
        }
    }
}
