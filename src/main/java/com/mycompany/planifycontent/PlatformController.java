package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.PlatformDAO;
import com.mycompany.planifycontent.database.DatabaseConnection;
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
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.mycompany.planifycontent.TablePlatform;
import javafx.scene.control.TextField;

public class PlatformController implements Initializable {

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
    private void bukaHalamanTambah(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahProyek.fxml"));
        Parent root = fxmlLoader.load();    
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tambah Data Proyek");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
    }

    @FXML
    private void popupBtnBatal(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private TextField platformNameField;

    @FXML
    private TableView<TablePlatform> tableView;

    @FXML
    private TableColumn<TablePlatform, Integer> noColumn;

    @FXML
    private TableColumn<TablePlatform, String> platformColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (tableView != null) {
            try {
                Connection connection = DatabaseConnection.getConnection();
                PlatformDAO dataPlatformDAO = new PlatformDAO(connection);
                List<TablePlatform> platformData = dataPlatformDAO.getAllDataPlatforms();
                ObservableList<TablePlatform> observablePlatformData = FXCollections.observableArrayList(platformData);

                tableView.setItems(observablePlatformData);

                // Inisialisasi kolom-kolom lain
                noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
                platformColumn.setCellValueFactory(new PropertyValueFactory<>("platform"));

                // Atur nomor untuk setiap item
                int index = 1;
                for (TablePlatform item : platformData) {
                    item.noProperty().set(index++);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle kesalahan jika gagal mendapatkan koneksi atau data
            }
        }
    }
}
