package com.mycompany.planitfycontent;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import com.mycompany.planitfycontent.database.DatabaseConnection;
import com.mycompany.planitfycontent.database.HistoriDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

public class HistoriController implements Initializable{

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


    // Metode lainnya...

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            HistoriDAO historiDAO = new HistoriDAO(connection);
            List<TableHistori> historiData = historiDAO.getHistoriData();
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

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}
