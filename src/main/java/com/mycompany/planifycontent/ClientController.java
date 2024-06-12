package com.mycompany.planifycontent;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class ClientController implements Initializable {

    @FXML
    private TableView<TableClient> tableView;

    @FXML
    private TableColumn<TableClient, Integer> noColumn;

    @FXML
    private TableColumn<TableClient, String> namaColumn;

    @FXML
    private TableColumn<TableClient, String> noTelpColumn;

    @FXML
    private TableColumn<TableClient, String> usahaColumn;

    @FXML
    private TextField namaField;

    @FXML
    private TextField noTelpField;

    @FXML
    private TextField usahaField;

    private ObservableList<TableClient> clientData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientData = FXCollections.observableArrayList();

        if (tableView == null) {
            System.out.println("tableView is null in initialize");
            return;
        }

        try {
            Connection connection = DatabaseConnection.getConnection();
            ClientDAO clientDAO = new ClientDAO(connection);
            clientData.addAll(clientDAO.getAllDataClients());

            System.out.println("clientData initialized with size: " + clientData.size());

            tableView.setItems(clientData);

            noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
            namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
            noTelpColumn.setCellValueFactory(new PropertyValueFactory<>("noTelp"));
            usahaColumn.setCellValueFactory(new PropertyValueFactory<>("usaha"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    private void refreshTableData() {
        clientData.clear();
        try {
            Connection connection = DatabaseConnection.getConnection();
            ClientDAO clientDAO = new ClientDAO(connection);
            clientData.addAll(clientDAO.getAllDataClients());
            tableView.setItems(clientData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
