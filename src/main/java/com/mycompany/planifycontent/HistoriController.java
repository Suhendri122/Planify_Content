package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import com.mycompany.planifycontent.database.HistoriDAO;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class HistoriController implements Initializable {

    @FXML
    private TableView<TableHistori> tableView;

    @FXML
    private TableColumn<TableHistori, String> noColumn;
    @FXML
    private TableColumn<TableHistori, String> proyekColumn;
    @FXML
    private TableColumn<TableHistori, String> picProyekColumn;
    @FXML
    private TableColumn<TableHistori, String> namaClientColumn;
    @FXML
    private TableColumn<TableHistori, String> temaColumn;
    @FXML
    private TableColumn<TableHistori, String> mediaColumn;
    @FXML
    private TableColumn<TableHistori, String> platformColumn;
    @FXML
    private TableColumn<TableHistori, String> linkColumn;
    @FXML
    private TableColumn<TableHistori, String> deadlineColumn;
    @FXML
    private TableColumn<TableHistori, String> tglPostColumn;
    @FXML
    private TableColumn<TableHistori, String> picKontenColumn;
    @FXML
    private TableColumn<TableHistori, String> statusColumn;

    @FXML
    private DatePicker dariTgl;
    @FXML
    private DatePicker sampaiTgl;
    @FXML
    private Button filterButton;

    private Stage mainStage;
    private Connection connection;

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
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Logout");
        alert.setHeaderText(null);
        alert.setContentText("Apakah Anda yakin ingin logout?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            App.setRoot("login");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dariTgl.setValue(LocalDate.now());
        sampaiTgl.setValue(LocalDate.now());
        try {
            connection = DatabaseConnection.getConnection();
            HistoriDAO historiDAO = new HistoriDAO(connection);
            List<TableHistori> historiData = historiDAO.getHistoriData("", "");
            ObservableList<TableHistori> observableHistoriData = FXCollections.observableArrayList(historiData);
            tableView.setItems(observableHistoriData);
            noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
            proyekColumn.setCellValueFactory(new PropertyValueFactory<>("proyek"));
            picProyekColumn.setCellValueFactory(new PropertyValueFactory<>("picProyek"));
            namaClientColumn.setCellValueFactory(new PropertyValueFactory<>("namaClient"));
            temaColumn.setCellValueFactory(new PropertyValueFactory<>("tema"));
            mediaColumn.setCellValueFactory(new PropertyValueFactory<>("media"));
            platformColumn.setCellValueFactory(new PropertyValueFactory<>("platform"));
            linkColumn.setCellValueFactory(new PropertyValueFactory<>("link"));
            deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
            tglPostColumn.setCellValueFactory(new PropertyValueFactory<>("tglPost"));
            picKontenColumn.setCellValueFactory(new PropertyValueFactory<>("picKonten"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

            filterButton.setOnAction((e -> {
                refreshTable();
            }));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshTable() {
        try {
            String startDate = dariTgl.getValue() == null? "" : dariTgl.getValue().toString();
            String endDate = sampaiTgl.getValue() == null? "" : sampaiTgl.getValue().toString();

            HistoriDAO historiDAO = new HistoriDAO(connection);
            List<TableHistori> historiData = historiDAO.getHistoriData(startDate, endDate);
            ObservableList<TableHistori> observableHistoriData = FXCollections.observableArrayList(historiData);
            tableView.setItems(observableHistoriData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
