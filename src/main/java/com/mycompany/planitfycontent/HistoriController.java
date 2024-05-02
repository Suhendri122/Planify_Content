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

public class HistoriController {

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
    
    @FXML
    private void popupBtnBatal(ActionEvent event) {
        // Mendapatkan stage dari event
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Menutup stage (popup)
        stage.close();
    }
    
        @FXML
    private TableView<TableHistori> tableView;

    @FXML
    private TableColumn<TableHistori, Integer> no;
    @FXML
    private TableColumn<TableHistori, String> proyek;
    @FXML
    private TableColumn<TableHistori, String> picProyek;
    @FXML
    private TableColumn<TableHistori, String> namaKonsumen;
    @FXML
    private TableColumn<TableHistori, String> tema;
    @FXML
    private TableColumn<TableHistori, String> media;
    @FXML
    private TableColumn<TableHistori, String> platform;
    @FXML
    private TableColumn<TableHistori, String> link;
    @FXML
    private TableColumn<TableHistori, String> deadline;
    @FXML
    private TableColumn<TableHistori, String> tglPost;
    @FXML
    private TableColumn<TableHistori, String> picKonten;
    @FXML
    private TableColumn<TableHistori, String> status;

    public void initialize() {
        // Add dummy data to TableView
        tableView.getItems().add(new TableHistori(1, "Proyek 1", "PIC Proyek 1", "Nama Konsumen 1", "Tema 1", "Media 1", "Platform 1", "Link 1", "Deadline 1", "Tgl. Post 1", "PIC Konten 1", "Status 1"));
        tableView.getItems().add(new TableHistori(2, "Proyek 2", "PIC Proyek 2", "Nama Konsumen 2", "Tema 2", "Media 2", "Platform 2", "Link 2", "Deadline 2", "Tgl. Post 2", "PIC Konten 2", "Status 2"));

        // Connect each TableColumn with corresponding data property
        no.setCellValueFactory(cellData -> cellData.getValue().noProperty().asObject());
        proyek.setCellValueFactory(cellData -> cellData.getValue().proyekProperty());
        picProyek.setCellValueFactory(cellData -> cellData.getValue().picProyekProperty());
        namaKonsumen.setCellValueFactory(cellData -> cellData.getValue().namaKonsumenProperty());
        tema.setCellValueFactory(cellData -> cellData.getValue().temaProperty());
        media.setCellValueFactory(cellData -> cellData.getValue().mediaProperty());
        platform.setCellValueFactory(cellData -> cellData.getValue().platformProperty());
        link.setCellValueFactory(cellData -> cellData.getValue().linkProperty());
        deadline.setCellValueFactory(cellData -> cellData.getValue().deadlineProperty());
        tglPost.setCellValueFactory(cellData -> cellData.getValue().tglPostProperty());
        picKonten.setCellValueFactory(cellData -> cellData.getValue().picKontenProperty());
        status.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
    }
}


