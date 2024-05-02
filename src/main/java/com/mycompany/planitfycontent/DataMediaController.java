package com.mycompany.planitfycontent;

import com.mycompany.planitfycontent.database.DashboardDAO;
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
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.mycompany.planitfycontent.TableDataPlatform;
import com.mycompany.planitfycontent.database.DataMediaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class DataMediaController implements Initializable{
    
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahDataMedia.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tambah Data Media");
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
    private TableView<TableDataMedia> tableView;
     
     @FXML
    private TableColumn<TableDataMedia, Integer> noColumn;

    @FXML
    private TableColumn<TableDataMedia, String> mediaColumn;
     
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            DataMediaDAO DataMediaDAO = new DataMediaDAO(connection);
            List<TableDataMedia> mediaData = DataMediaDAO.getAllDataMedia();
            ObservableList<TableDataMedia> observableMediaData = FXCollections.observableArrayList(mediaData);

            tableView.setItems(observableMediaData);

            // Inisialisasi kolom-kolom lain
            noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
            mediaColumn.setCellValueFactory(new PropertyValueFactory<>("media"));

            // Atur nomor untuk setiap item
            int index = 1;
            for (TableDataMedia item : mediaData) {
                item.noProperty().set(index++);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle kesalahan jika gagal mendapatkan koneksi atau data
        }
    }
}