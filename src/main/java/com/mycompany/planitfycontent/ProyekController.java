package com.mycompany.planitfycontent;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import java.io.IOException;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProyekController implements Initializable {

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahDataProyek.fxml"));
        Parent root = fxmlLoader.load();    
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tambah Data Proyek"); // Mengatur judul window popup
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait(); // Menampilkan popup dan menunggu sampai popup ditutup
    }
    
     @FXML
    private void bukaHalamanFilter(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/filterDataProyek.fxml"));
        Parent root = fxmlLoader.load();    
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Filter Data Proyek"); // Mengatur judul window popup
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
    private TableView<Proyek> tableView;

    @FXML
    private TableColumn<Proyek, String> noColumn;

    @FXML
    private TableColumn<Proyek, String> namaProyekColumn;

    @FXML
    private TableColumn<Proyek, String> picProyekColumn;

    @FXML
    private TableColumn<Proyek, String> namaClientColumn;

    @FXML
    private TableColumn<Proyek, String> noTeleponColumn;

    @FXML
    private TableColumn<Proyek, String> hargaColumn;

    @FXML
    private TableColumn<Proyek, String> tglMulaiColumn;

    @FXML
    private TableColumn<Proyek, String> tglSelesaiColumn;

    private ObservableList<Proyek> proyekList;

    @Override
    public void initialize(URL url ResourceBundke resourceBundle) {
        // Inisialisasi ObservableList
        proyekList = FXCollections.observableArrayList();

        // Tambahkan data dummy
        proyekList.add(new Proyek("1", "Proyek A", "PIC A", "Client A", "123456", "$1000", "2024-05-01", "2024-06-01"));
        proyekList.add(new Proyek("2", "Proyek B", "PIC B", "Client B", "789012", "$2000", "2024-06-01", "2024-07-01"));

        // Set data ke TableView
        tableView.setItems(proyekList);

        // Hubungkan setiap TableColumn dengan properti data yang sesuai
        no.setCellValueFactory(cellData -> cellData.getValue().getNo());
        namaProyek.setCellValueFactory(cellData -> cellData.getValue().getNamaProyek());
        picProyek.setCellValueFactory(cellData -> cellData.getValue().getPicProyek());
        namaClient.setCellValueFactory(cellData -> cellData.getValue().getNamaClient());
        noTelepon.setCellValueFactory(cellData -> cellData.getValue().getNoTelepon());
        harga.setCellValueFactory(cellData -> cellData.getValue().getHarga());
        tglMulai.setCellValueFactory(cellData -> cellData.getValue().getTglMulai());
        tglSelesai.setCellValueFactory(cellData -> cellData.getValue().getTglSelesai());
    }
}
