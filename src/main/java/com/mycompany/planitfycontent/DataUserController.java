package com.mycompany.planitfycontent;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DataUserController {
    
    @FXML
    private void bukaHalamanDashboard(ActionEvent event) throws IOException {
        App.setRoot("dashboard");
    }
    
    @FXML
    private void bukaHalamanProyek(ActionEvent event) throws IOException {
        App.setRoot("proyek");
    }

    @FXML
    private void bukaHalamanHistori(ActionEvent event) throws IOException {
        App.setRoot("histori");
    }

    @FXML
    private void bukaHalamanDataPlatform(ActionEvent event) throws IOException {
        App.setRoot("dataPlatform");
    }

    @FXML
    private void bukaHalamanDataMedia(ActionEvent event) throws IOException {
        App.setRoot("dataMedia");
    }

    @FXML
    private void bukaHalamanDataClient(ActionEvent event) throws IOException {
        App.setRoot("dataClient");
    }

    @FXML
    private void bukaHalamanDataUser(ActionEvent event) throws IOException {
        App.setRoot("dataUser");
    }
    
    // Popup tambah, edit, dan filter
    @FXML
    private void bukaHalamanTambah(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahDataUser.fxml"));
        Parent root = fxmlLoader.load();    
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tambah Data User"); // Mengatur judul window popup
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait(); // Menampilkan popup dan menunggu sampai popup ditutup
    }
    
    @FXML
    private void popupBtnBatal(ActionEvent event) {
        // Mendapatkan stage dari event
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Menutup stage (popup)
        stage.close();
    }
    
    @FXML
    private TableView<TableDataUser> tableView;

    @FXML
    private TableColumn<TableDataUser, Integer> no;

    @FXML
    private TableColumn<TableDataUser, String> nama;
    
    @FXML
    private TableColumn<TableDataUser, String> email;

    @FXML
    public void initialize() {
        // Set up columns
        no.setCellValueFactory(cellData -> cellData.getValue().noProperty().asObject());
        nama.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        email.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        // Set up data
        TableDataUser dataUser1 = new TableDataUser(1, "John Doe", "john.doe@example.com");
        TableDataUser dataUser2 = new TableDataUser(2, "Jane Doe", "jane.doe@example.com");

        TableDataUser tableDataUser = new TableDataUser();
        tableDataUser.addDataUser(dataUser1);
        tableDataUser.addDataUser(dataUser2);

        tableView.setItems(tableDataUser.getDataUsers());
    }
}
