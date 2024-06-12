package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.MediaDAO;
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

import com.mycompany.planifycontent.TableMedia;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class MediaController implements Initializable {

    @FXML
    private TableView<TableMedia> tableView;

    @FXML
    private TableColumn<TableMedia, Integer> noColumn;

    @FXML
    private TableColumn<TableMedia, String> mediaColumn;

    @FXML
    private TextField mediaNameField;

    private ObservableList<TableMedia> mediaData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaData = FXCollections.observableArrayList();

        if (tableView != null) {
            try {
                Connection connection = DatabaseConnection.getConnection();
                MediaDAO mediaDAO = new MediaDAO(connection);
                List<TableMedia> mediaList = mediaDAO.getAllDataMedia();
                mediaData.setAll(mediaList);

                tableView.setItems(mediaData);

                noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
                mediaColumn.setCellValueFactory(new PropertyValueFactory<>("media"));

                int index = 1;
                for (TableMedia item : mediaData) {
                    item.setNo(index++);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahMedia.fxml"));
        Parent root = fxmlLoader.load();    
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tambah Data Media");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
        initialize(null, null); // Refresh data setelah pop-up ditutup
    }

    @FXML
    private void handleTambah(ActionEvent event) {
        if (mediaNameField != null) {
            String mediaName = mediaNameField.getText();
            if (mediaName != null && !mediaName.isEmpty()) {
                try {
                    Connection connection = DatabaseConnection.getConnection();
                    MediaDAO mediaDAO = new MediaDAO(connection);
                    mediaDAO.tambahMedia(mediaName);
                    List<TableMedia> mediaList = mediaDAO.getAllDataMedia();

                    if (mediaData != null) {
                        mediaData.setAll(mediaList);

                        int index = 1;
                        for (TableMedia item : mediaData) {
                            item.setNo(index++);
                        }
                    }
                } catch (SQLException e) {
                    // Handle the SQL exception and display an error message
                    e.printStackTrace();
                    showErrorMessage("Error adding media", "An error occurred while adding the media. Please try again.");
                }
            } else {
                showErrorMessage("Peringatan", "Masukkan Nama Media Terlebih Dahulu");
            }
            closeWindow();
        }
    }

    @FXML
    private void popupBtnBatal(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        if (mediaNameField != null) {
            Stage stage = (Stage) mediaNameField.getScene().getWindow();
            stage.close();
        }
    }

    private void showErrorMessage(String title, String message) {
        // You can use an Alert to display error messages to the user
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
