package com.mycompany.planitfycontent;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;


public class DashboardController implements Initializable {

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
        private TableView<TableDashboard> tableView;

        @FXML
        private TableColumn<TableDashboard, Integer> no;
        @FXML
        private TableColumn<TableDashboard, String> proyek;
        @FXML
        private TableColumn<TableDashboard, String> picProyek;
        @FXML
        private TableColumn<TableDashboard, String> tema;
        @FXML
        private TableColumn<TableDashboard, String> media;
        @FXML
        private TableColumn<TableDashboard, String> deadline;
        @FXML
        private TableColumn<TableDashboard, String> tglPost;
        @FXML
        private TableColumn<TableDashboard, String> picKonten;

        private ObservableList<TableDashboard> dataList;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            // Inisialisasi ObservableList
            dataList = FXCollections.observableArrayList();

            // Mengatur data dummy ke dalam ObservableList
            dataList.add(new TableDashboard(1, "ISTESA", "Aldio", "Hut ISTESA", "Video Horizontal", "03/05/2024", "04/05/2024", "Suhendri"));
            dataList.add(new TableDashboard(2, "ISTESA", "Aldio", "Hut ISTESA H-1", "postingan 1:1", "03/05/2024", "05/05/2024", "Kristanto"));
            dataList.add(new TableDashboard(3, "ISTESA", "Aldio", "Kuis", "postingan 1:1", "03/05/2024", "03/05/2024", "Aldio"));
            dataList.add(new TableDashboard(4, "Royal Cake", "Kristanto", "Promo buy 2 get 1", "postingan 1:1", "04/05/2024", "05/05/2024", "Aldio"));
            dataList.add(new TableDashboard(5, "Royal Cake", "Kristanto", "Promo 5.5", "postingan 1:1", "04/05/2024", "05/05/2024", "Kristanto"));
            dataList.add(new TableDashboard(6, "Royal Cake", "Kristanto", "Promo Anniversary 2th", "postingan 1:1", "05/05/2024", "06/05/2024", "Suhendri"));
            dataList.add(new TableDashboard(7, "Royal Cake", "Kristanto", "Anniversary 2th", "postingan 1:1", "06/05/2024", "07/05/2024", "Kristanto"));

            // Mengatur sumber data TableView
            tableView.setItems(dataList);

            // Menghubungkan setiap TableColumn dengan properti data yang sesuai
            no.setCellValueFactory(cellData -> cellData.getValue().noProperty().asObject());
            proyek.setCellValueFactory(cellData -> cellData.getValue().proyekProperty());
            picProyek.setCellValueFactory(cellData -> cellData.getValue().picProyekProperty());
            tema.setCellValueFactory(cellData -> cellData.getValue().temaProperty());
            media.setCellValueFactory(cellData -> cellData.getValue().mediaProperty());
            deadline.setCellValueFactory(cellData -> cellData.getValue().deadlineProperty());
            tglPost.setCellValueFactory(cellData -> cellData.getValue().tglPostProperty());
            picKonten.setCellValueFactory(cellData -> cellData.getValue().picKontenProperty());
        }
}
