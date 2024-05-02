package com.mycompany.planitfycontent;

import com.mycompany.planitfycontent.database.DataClientDAO;
import com.mycompany.planitfycontent.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DataClientController implements Initializable{

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahDataClient.fxml"));
        Parent root = fxmlLoader.load();    
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tambah Data Client"); // Mengatur judul window popup
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait(); // Menampilkan popup dan menunggu sampai popup ditutup
    }
    
    @FXML
    private void bukaHalamanFilter(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/filterDataClient.fxml"));
        Parent root = fxmlLoader.load();    
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Filter Data Client"); // Mengatur judul window popup
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
    private TableView<TableDataClient> tableView;

    @FXML
    private TableColumn<TableDataClient, Integer> noColumn;

    @FXML
    private TableColumn<TableDataClient, String> namaColumn;

    @FXML
    private TableColumn<TableDataClient, String> noTelpColumn;

    @FXML
    private TableColumn<TableDataClient, String> usahaColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            DataClientDAO dataClientDAO = new DataClientDAO(connection);
            List<TableDataClient> clientData = dataClientDAO.getAllDataClients();
            ObservableList<TableDataClient> observableClientData = FXCollections.observableArrayList(clientData);

            tableView.setItems(observableClientData);

            // Initialize columns
            noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
            namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
            noTelpColumn.setCellValueFactory(new PropertyValueFactory<>("no_telp"));
            usahaColumn.setCellValueFactory(new PropertyValueFactory<>("usaha"));

            // Set numbering for each item
            int index = 1;
            for (TableDataClient item : clientData) {
                item.noProperty().set(index++);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle error if failed to get connection or data
        }
    }
}
