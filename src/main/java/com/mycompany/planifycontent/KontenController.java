package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import com.mycompany.planifycontent.database.KontenDAO;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class KontenController implements Initializable {

    @FXML
    private TableView<TableKonten> tableView;

    @FXML
    private TableColumn<TableKonten, Integer> no;
    @FXML
    private TableColumn<TableKonten, String> tema;
    @FXML
    private TableColumn<TableKonten, String> media;
    @FXML
    private TableColumn<TableKonten, String> platform;
    @FXML
    private TableColumn<TableKonten, String> link;
    @FXML
    private TableColumn<TableKonten, String> deadline;
    @FXML
    private TableColumn<TableKonten, String> tglPost;
    @FXML
    private TableColumn<TableKonten, String> picKonten;
    @FXML
    private TableColumn<TableKonten, String> status;

    private ObservableList<TableKonten> kontenData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        no.setCellValueFactory(new PropertyValueFactory<>("id"));
        tema.setCellValueFactory(new PropertyValueFactory<>("tema"));
        media.setCellValueFactory(new PropertyValueFactory<>("namaMedia"));
        platform.setCellValueFactory(new PropertyValueFactory<>("namaPlatform"));
        link.setCellValueFactory(new PropertyValueFactory<>("linkDesain"));
        deadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        tglPost.setCellValueFactory(new PropertyValueFactory<>("tglPost"));
        picKonten.setCellValueFactory(new PropertyValueFactory<>("namaUser"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Fetch data from database and add to the table
        fetchDataFromDatabase();
    }

    private void fetchDataFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            KontenDAO kontenDAO = new KontenDAO(connection);
            List<TableKonten> kontenList = kontenDAO.getAllKonten();
            kontenData.setAll(kontenList);
            tableView.setItems(kontenData);
        } catch (SQLException e) {
            e.printStackTrace();
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
    private void bukaHalamanUser(ActionEvent event) throws IOException {
        App.setRoot("user");
    }
    
    @FXML
    private void bukaHalamanClient(ActionEvent event) throws IOException {
        App.setRoot("client");
    }
    
    @FXML
    private Button btnBack;
    
    //popup tambah, edit, dan filter
    
    @FXML
    private void bukaHalamanTambah(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahDataKonten.fxml"));
        Parent root = fxmlLoader.load();    
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tambah Data Konten"); // Mengatur judul window popup
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait(); // Menampilkan popup dan menunggu sampai popup ditutup
    }
    
    @FXML
    private void bukaHalamanFilter(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/filterDataKonten.fxml"));
        Parent root = fxmlLoader.load();    
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Filter Data Konten"); // Mengatur judul window popup
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait(); // Menampilkan popup dan menunggu sampai popup ditutup
    }
    
        private TableProyek proyek; // Properti untuk menyimpan data proyek

    // Metode untuk mengatur data proyek
    public void setProyek(TableProyek proyek) {
        this.proyek = proyek;
        // Di sini Anda dapat menambahkan logika untuk menampilkan data proyek ke dalam tampilan halaman konten
        // Misalnya, mengatur teks pada label atau menampilkan data dalam tabel
    }
}
