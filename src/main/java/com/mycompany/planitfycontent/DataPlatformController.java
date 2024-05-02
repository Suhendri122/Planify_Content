package com.mycompany.planitfycontent;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DataPlatformController implements Initializable{
    
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
    
    
    //popup tambah, edit, dan filter
    
    @FXML
    private void bukaHalamanTambah(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahDataPlatform.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tambah Data Platform");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
    }

    
    @FXML
    private void popupBtnBatal(ActionEvent event) {
        // Mendapatkan stage dari event
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Menutup stage (popup)
        stage.close();
    }
    
    @FXML
    private TableView<TableDataPlatform> tableView;

    @FXML
    private TableColumn<TableDataPlatform, Integer> no;

    @FXML
    private TableColumn<TableDataPlatform, String> platform;
    
    @FXML
    private TableColumn<TableDataPlatform, String> aksi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up columns
        no.setCellValueFactory(cellData -> cellData.getValue().noProperty().asObject());
        platform.setCellValueFactory(cellData -> cellData.getValue().platformProperty());

        // Set up data
        TableDataPlatform dataPlatform1 = new TableDataPlatform(1, "Platform 1");
        TableDataPlatform dataPlatform2 = new TableDataPlatform(2, "Platform 2");

        TableDataPlatform tableDataPlatform = new TableDataPlatform();
        tableDataPlatform.addDataPlatform(dataPlatform1);
        tableDataPlatform.addDataPlatform(dataPlatform2);

        tableView.setItems(tableDataPlatform.getDataPlatforms());
    }
}