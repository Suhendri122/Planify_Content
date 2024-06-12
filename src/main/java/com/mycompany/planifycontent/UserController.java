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
import com.mycompany.planifycontent.database.UserDAO;
import com.mycompany.planifycontent.database.DatabaseConnection;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UserController implements Initializable {

    @FXML
    private TableView<TableUser> tableView;

    @FXML
    private TableColumn<TableUser, Integer> noColumn;

    @FXML
    private TableColumn<TableUser, String> namaColumn;

    @FXML
    private TableColumn<TableUser, String> emailColumn;

    @FXML
    private TextField namaField;

    @FXML
    private TextField emailField;

    private ObservableList<TableUser> userData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userData = FXCollections.observableArrayList();

        if (tableView == null) {
            System.out.println("tableView is null in initialize");
            return;
        }

        try {
            Connection connection = DatabaseConnection.getConnection();
            UserDAO dataUserDAO = new UserDAO(connection);
            userData.addAll(dataUserDAO.getAllDataUsers());

            System.out.println("userData initialized with size: " + userData.size());

            tableView.setItems(userData);

            noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
            namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            // aksiColumn.setCellValueFactory(new PropertyValueFactory<>("aksi")); // Tambahkan jika aksiColumn ada di TableUser

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahUser.fxml"));
            Parent root = fxmlLoader.load();

            // Buat stage baru untuk pop-up
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Tambah Data User");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();

            // Refresh data setelah penambahan user baru
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
    private void tambahDataUser(ActionEvent event) {
        if (userData == null) {
            System.out.println("userData is null");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Data user tidak diinisialisasi dengan benar.");
            alert.showAndWait();
            return;
        }

        System.out.println("userData size before addition: " + userData.size());

        try {
            String nama = namaField.getText();
            String email = emailField.getText();

            if (nama.isEmpty() || email.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Peringatan");
                alert.setHeaderText(null);
                alert.setContentText("Nama dan Email harus diisi.");
                alert.showAndWait();
                return;
            }

            Connection connection = DatabaseConnection.getConnection();
            UserDAO userDAO = new UserDAO(connection);

            int newUserId = userDAO.tambahUser(nama, email);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasi");
            alert.setHeaderText(null);
            alert.setContentText("User berhasil ditambahkan!");
            alert.showAndWait();

            // Bersihkan field setelah menambahkan user
            namaField.clear();
            emailField.clear();

            // Tambahkan data user baru ke dalam TableView
            userData.add(new TableUser(userData.size() + 1, nama, email));

            if (tableView != null) {
                tableView.refresh();
            } else {
                System.out.println("tableView is null at the point of refresh");
            }

            System.out.println("userData size after addition: " + userData.size());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Terjadi kesalahan saat menambahkan user: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void refreshTableData() {
        userData.clear();
        try {
            Connection connection = DatabaseConnection.getConnection();
            UserDAO dataUserDAO = new UserDAO(connection);
            userData.addAll(dataUserDAO.getAllDataUsers());
            tableView.setItems(userData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
