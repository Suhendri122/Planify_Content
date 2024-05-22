package com.mycompany.planifycontent;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import com.mycompany.planifycontent.database.UserDAO;
import com.mycompany.planifycontent.database.DatabaseConnection;
import javafx.fxml.Initializable;

public class UserController implements Initializable {
    
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
    private TableView<TableUser> tableView;

    @FXML
    private TableColumn<TableUser, Integer> noColumn;

    @FXML
    private TableColumn<TableUser, String> namaColumn;

    @FXML
    private TableColumn<TableUser, String> emailColumn;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            UserDAO dataUserDAO = new UserDAO(connection);
            ObservableList<TableUser> userData = FXCollections.observableArrayList(dataUserDAO.getAllDataUsers());

            tableView.setItems(userData);

            // Initialize columns
            noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
            namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle error if failed to get connection or data
        }
    }
}
