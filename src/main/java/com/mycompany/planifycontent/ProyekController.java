package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import com.mycompany.planifycontent.database.ProyekDAO;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProyekController implements Initializable{

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahDataProyek.fxml"));
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
private TableView<TableProyek> tableView;

@FXML
private TableColumn<TableProyek, Integer> no;

@FXML
private TableColumn<TableProyek, String> namaProyek;
@FXML
private TableColumn<TableProyek, String> picProyek;

@FXML
private TableColumn<TableProyek, String> namaClient;

@FXML
private TableColumn<TableProyek, String> noTelepon;

@FXML
private TableColumn<TableProyek, String> harga;

@FXML
private TableColumn<TableProyek, String> tglMulai;

@FXML
private TableColumn<TableProyek, String> tglSelesai;

@FXML
private TableColumn<TableProyek, String> aksi;
// Tambahkan TableColumn untuk properti lainnya

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    // Menginisialisasi kolom
    no.setCellValueFactory(new PropertyValueFactory<>("id"));
    namaProyek.setCellValueFactory(new PropertyValueFactory<>("namaProyek"));
    picProyek.setCellValueFactory(new PropertyValueFactory<>("picProyek"));
    namaClient.setCellValueFactory(new PropertyValueFactory<>("namaClient"));
    noTelepon.setCellValueFactory(new PropertyValueFactory<>("noTelepon"));
    harga.setCellValueFactory(new PropertyValueFactory<>("harga"));
    tglMulai.setCellValueFactory(new PropertyValueFactory<>("tglMulai"));
    tglSelesai.setCellValueFactory(new PropertyValueFactory<>("tglSelesai"));
    aksi.setCellValueFactory(new PropertyValueFactory<>("aksi"));

    // Di bagian di mana Anda membutuhkan koneksi ke database
    try {
        Connection connection = DatabaseConnection.getConnection();
        // Gunakan koneksi ke database untuk mendapatkan data proyek
        ProyekDAO proyekDAO = new ProyekDAO(connection);
        List<TableProyek> proyekList = proyekDAO.getAllProyek();
        ObservableList<TableProyek> observableProyekList = FXCollections.observableArrayList(proyekList);
        tableView.setItems(observableProyekList);
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle kesalahan jika gagal mendapatkan koneksi atau data proyek
    }
    
}


}

