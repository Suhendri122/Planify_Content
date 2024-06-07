package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.ClientDAO;
import com.mycompany.planifycontent.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class ClientController implements Initializable{

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
    private void bukaHalamanPlatform(ActionEvent event) throws IOException {
        App.setRoot("platform");
    }

    @FXML
    private void bukaHalamanMedia(ActionEvent event) throws IOException {
        App.setRoot("media");
    }

    @FXML
    private void bukaHalamanClient(ActionEvent event) throws IOException {
        App.setRoot("client");
    }

    @FXML
    private void bukaHalamanUser(ActionEvent event) throws IOException {
        App.setRoot("user");
    }
    
        @FXML
    private void logout(ActionEvent event) throws IOException {
        // Membuat dialog konfirmasi
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Logout");
        alert.setHeaderText(null);
        alert.setContentText("Apakah Anda yakin ingin logout?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            App.setRoot("login");
        }
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
    private TableView<TableClient> tableView;

    @FXML
    private TableColumn<TableClient, Integer> no;

    @FXML
    private TableColumn<TableClient, String> nama;

    @FXML
    private TableColumn<TableClient, String> noTelp;

    @FXML
    private TableColumn<TableClient, String> usaha;
    
    @FXML
    private TableColumn<TableClient, String> aksiColumn;
    
    
     @FXML
    private ChoiceBox<String> namaclient;
    
    @FXML
    private ChoiceBox<String> namausaha;
    
    @FXML
    private Button filtercari;

    private Stage mainStage;
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    if (tableView != null) {
        try {
            Connection connection = DatabaseConnection.getConnection();
        ClientDAO dataClientDAO = new ClientDAO(connection);
        List<TableClient> clientData = dataClientDAO.getAllClients();
        ObservableList<TableClient> observableClientData = FXCollections.observableArrayList(clientData);

        tableView.setItems(observableClientData);

        // Initialize columns
        no.setCellValueFactory(new PropertyValueFactory<>("no"));
        nama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        noTelp.setCellValueFactory(new PropertyValueFactory<>("no_telp"));
        usaha.setCellValueFactory(new PropertyValueFactory<>("usaha"));

        // Set numbering for each item
        int index = 1;
        for (TableClient item : clientData) {
            item.noProperty().set(index++);
        }
        
        // Populate ChoiceBox items
        ObservableList<String> namaItems = FXCollections.observableArrayList();
        ObservableList<String> usahaItems = FXCollections.observableArrayList();

        for (TableClient client : clientData) {
            if (!namaItems.contains(client.getNama())) {
                namaItems.add(client.getNama());
            }
            if (!usahaItems.contains(client.getUsaha())) {
                usahaItems.add(client.getUsaha());
            }
        }

        namaclient.setItems(namaItems);
        namausaha.setItems(usahaItems);
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle error if failed to get connection or data
    }
        
       // Remove the ChangeListener from the ChoiceBox selection model
    namaclient.getSelectionModel().selectedItemProperty().removeListener((obs, oldValue, newValue) -> {
        filterCariAction(null);
    });
    
    namausaha.getSelectionModel().selectedItemProperty().removeListener((obs, oldValue, newValue) -> {
        filterCariAction(null);
    });
    
    // Add an action listener to the "Cari" button
    filtercari.setOnAction(event -> {
        filterCariAction(event);
    });
        
    }
    }
    
    
    @FXML
private void filterCariAction(ActionEvent event) {
    String nama = namaclient.getSelectionModel().getSelectedItem();
    String usaha = namausaha.getSelectionModel().getSelectedItem();

    try {
        Connection connection = DatabaseConnection.getConnection();
        ClientDAO dataClientDAO = new ClientDAO(connection);
        List<TableClient> filteredData = dataClientDAO.getDataClientsByFilter(nama, usaha);
        ObservableList<TableClient> observableFilteredData = FXCollections.observableArrayList(filteredData);

        tableView.setItems(observableFilteredData);
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle error if failed to get connection or data
    }
}
}
