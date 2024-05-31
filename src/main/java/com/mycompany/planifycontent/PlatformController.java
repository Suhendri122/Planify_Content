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
    private TableView<TablePlatform> tableView;

    @FXML
    private TableColumn<TablePlatform, Integer> noColumn;

    @FXML
    private TableColumn<TablePlatform, String> platformColumn;

    @FXML
    private TextField platformNameField;

    private ObservableList<TablePlatform> platformData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        platformData = FXCollections.observableArrayList();
        
        if (tableView != null) {
            try {
                Connection connection = DatabaseConnection.getConnection();
                PlatformDAO dataPlatformDAO = new PlatformDAO(connection);
                List<TablePlatform> platformList = dataPlatformDAO.getAllDataPlatforms();
                platformData.setAll(platformList);

                tableView.setItems(platformData);

                noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
                platformColumn.setCellValueFactory(new PropertyValueFactory<>("platform"));

                int index = 1;
                for (TablePlatform item : platformData) {
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
    private void bukaHalamanTambah(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahPlatform.fxml"));
        Parent root = fxmlLoader.load();    
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tambah Data Platform");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
        initialize(null, null); // Refresh data setelah pop-up ditutup
    }

    @FXML
    private void handleTambah(ActionEvent event) {
        if (platformNameField != null) {
            String platformName = platformNameField.getText();
            if (platformName != null && !platformName.isEmpty()) {
                try {
                    Connection connection = DatabaseConnection.getConnection();
                    PlatformDAO dataPlatformDAO = new PlatformDAO(connection);
                    dataPlatformDAO.tambahPlatform(platformName);
                    List<TablePlatform> platformList = dataPlatformDAO.getAllDataPlatforms();

                    if (platformData != null) {
                        platformData.setAll(platformList);

                        int index = 1;
                        for (TablePlatform item : platformData) {
                            item.setNo(index++);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeWindow();
        }
    }

    @FXML
    private void popupBtnBatal(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        if (platformNameField != null) {
            Stage stage = (Stage) platformNameField.getScene().getWindow();
            stage.close();
        }
    }
}
