package com.mycompany.planifycontent;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
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
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import com.mycompany.planifycontent.database.ClientDAO;
import com.mycompany.planifycontent.database.DatabaseConnection;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private void bukaHalamanTambah(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahClient.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage for the pop-up
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Tambah Data Client");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();

            // Refresh data after adding a new client
            refreshTableData();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void popupBtnBatal(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void tambahDataClient(ActionEvent event) {
        if (clientData == null) {
            System.out.println("clientData is null");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Data client tidak diinisialisasi dengan benar.");
            alert.showAndWait();
            return;
        }

        System.out.println("clientData size before addition: " + clientData.size());

        try {
            String nama = namaField.getText();
            String noTelp = noTelpField.getText();
            String usaha = usahaField.getText();

            if (nama.isEmpty() || noTelp.isEmpty() || usaha.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Peringatan");
                alert.setHeaderText(null);
                alert.setContentText("Nama, No. Telp, dan Usaha harus diisi.");
                alert.showAndWait();
                return;
            }

            Connection connection = DatabaseConnection.getConnection();
            ClientDAO clientDAO = new ClientDAO(connection);

            TableClient newClient = new TableClient(clientData.size() + 1, nama, noTelp, usaha);
            clientDAO.addClient(newClient); // Menambah data klien ke database
            clientData.add(newClient); // Menambah data ke ObservableList

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasi");
            alert.setHeaderText(null);
            alert.setContentText("Client berhasil ditambahkan!");
            alert.showAndWait();

            // Clear the fields after adding the client
            namaField.clear();
            noTelpField.clear();
            usahaField.clear();

            if (tableView != null) {
                tableView.refresh();
            } else {
                System.out.println("tableView is null at the point of refresh");
            }

            System.out.println("clientData size after addition: " + clientData.size());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Terjadi kesalahan saat menambahkan client: " + e.getMessage());
            alert.showAndWait();
        }
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
            ClientDAO clientDAO = new ClientDAO(connection);

            TableClient newClient = new TableClient(clientData.size() + 1, nama, noTelp, usaha);
            clientDAO.addClient(newClient); // Menambah data klien ke database
            clientData.add(newClient); // Menambah data ke ObservableList

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasi");
            alert.setHeaderText(null);
            alert.setContentText("Client berhasil ditambahkan!");
            alert.showAndWait();

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
        
        namaclient.getItems().add(0, "Semua");
        namausaha.getItems().add(0, "Semua");
        
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
    
    if ("Semua".equals(nama) && "Semua".equals(usaha)) {
        // Clear filter and show all data
        try {
            Connection connection = DatabaseConnection.getConnection();
            ClientDAO dataClientDAO = new ClientDAO(connection);
            List<TableClient> allData = dataClientDAO.getAllClients();
            ObservableList<TableClient> observableAllData = FXCollections.observableArrayList(allData);

            tableView.setItems(observableAllData);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle error if failed to get connection or data
        }
    } else {
        // Filter data based on selected options

    try {
            Connection connection = DatabaseConnection.getConnection();
            ClientDAO dataClientDAO = new ClientDAO(connection);
            List<TableClient> filteredData = dataClientDAO.getDataClientsByFilter(nama, usaha);
            ObservableList<TableClient> observableFilteredData = FXCollections.observableArrayList(filteredData);

            tableView.setItems(observableFilteredData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
}
    
}

}

