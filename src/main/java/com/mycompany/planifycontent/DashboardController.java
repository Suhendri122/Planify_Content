package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DashboardDAO;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;


public class DashboardController implements Initializable{

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
    private TableView<TableDashboard> tableView;

    @FXML
    private TableColumn<TableDashboard, String> noColumn;
    
    @FXML
    private TableColumn<TableDashboard, String> proyekColumn;

    @FXML
    private TableColumn<TableDashboard, String> picProyekColumn;

     @FXML
    private TableColumn<TableDashboard, String> deadlineColumn;

    @FXML
    private TableColumn<TableDashboard, String> tglPostColumn;

    @FXML
    private TableColumn<TableDashboard, String> temaColumn;

    @FXML
    private TableColumn<TableDashboard, String> mediaColumn;

    @FXML
    private TableColumn<TableDashboard, String> picKontenColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            DashboardDAO dashboardDAO = new DashboardDAO(connection);
            List<TableDashboard> dashboardData = dashboardDAO.getDashboardData();
            ObservableList<TableDashboard> observableDashboardData = FXCollections.observableArrayList(dashboardData);
            tableView.setItems(observableDashboardData);

            // Inisialisasi kolom-kolom lain
            noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
            proyekColumn.setCellValueFactory(new PropertyValueFactory<>("proyek"));
            picProyekColumn.setCellValueFactory(new PropertyValueFactory<>("picProyek"));
            deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
            tglPostColumn.setCellValueFactory(new PropertyValueFactory<>("tglPost"));
            temaColumn.setCellValueFactory(new PropertyValueFactory<>("tema"));
            mediaColumn.setCellValueFactory(new PropertyValueFactory<>("media"));
            picKontenColumn.setCellValueFactory(new PropertyValueFactory<>("picKonten"));

            // Atur nomor untuk setiap item
            int index = 1;
            for (TableDashboard item : dashboardData) {
                item.noProperty().set(index++);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle kesalahan jika gagal mendapatkan koneksi atau data
        }
    }

}

