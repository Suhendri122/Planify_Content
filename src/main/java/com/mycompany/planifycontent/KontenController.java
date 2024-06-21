package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import com.mycompany.planifycontent.database.KontenDAO;
import com.mycompany.planifycontent.database.UserDAO;
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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class KontenController implements Initializable {
    
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
    @FXML
    private TableColumn<TableKonten, String> aksiColumn;

    private ObservableList<TableKonten> kontenData = FXCollections.observableArrayList();
    
    private TableProyek proyek;
    private TableKonten konten;

    // Metode untuk mengatur objek proyek
    public void setProyek(TableProyek proyek) {
        this.proyek = proyek;
        // Lakukan tindakan yang diperlukan dengan objek proyek, misalnya menampilkan informasi proyek di antarmuka pengguna
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            if (tableView != null) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            KontenDAO KontenDAO = new KontenDAO(connection);
            List<TableKonten> kontenData = KontenDAO.getAllKontens();
            ObservableList<TableKonten> observableKontenData = FXCollections.observableArrayList(kontenData);

            int index = 1;
            for (TableKonten item : kontenData) {
                    item.setId(index++);
                }
            tableView.setItems(observableKontenData);
            
        no.setCellValueFactory(new PropertyValueFactory<>("id"));
        tema.setCellValueFactory(new PropertyValueFactory<>("tema"));
        media.setCellValueFactory(new PropertyValueFactory<>("namaMedia"));
        platform.setCellValueFactory(new PropertyValueFactory<>("namaPlatform"));
        link.setCellValueFactory(new PropertyValueFactory<>("linkDesain"));
        deadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        tglPost.setCellValueFactory(new PropertyValueFactory<>("tglPost"));
        picKonten.setCellValueFactory(new PropertyValueFactory<>("namaUser"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        
aksiColumn.setCellFactory(new Callback<TableColumn<TableKonten, String>, TableCell<TableKonten, String>>() {
                @Override
                public TableCell<TableKonten, String> call(TableColumn<TableKonten, String> param) {
                    return new TableCell<TableKonten, String>() {
                        final Button btnEdit = new Button();
                        final Button btnDelete = new Button();
                        final HBox hbox = new HBox(btnEdit, btnDelete);
                        final AnchorPane anchorPane = new AnchorPane();

                        {
                            // Setup buttons
                            ImageView ivEdit = new ImageView(new Image(getClass().getResourceAsStream("/assets/edit.png")));
                            ivEdit.setFitHeight(20);
                            ivEdit.setFitWidth(20);
                            btnEdit.setGraphic(ivEdit);

                            ImageView ivDelete = new ImageView(new Image(getClass().getResourceAsStream("/assets/delete.png")));
                            ivDelete.setFitHeight(20);
                            ivDelete.setFitWidth(20);
                            btnDelete.setGraphic(ivDelete);
             
                            AnchorPane.setLeftAnchor(btnEdit, 0.0);
                            AnchorPane.setLeftAnchor(btnDelete, 40.0);
                            
                            btnEdit.setPadding(new Insets(5));
                            btnDelete.setPadding(new Insets(5));

                            // Setup button actions
                            btnEdit.setOnAction(event -> {
                                TableKonten konten = getTableView().getItems().get(getIndex());
                                showEditPopup(konten);
                            });

                            btnDelete.setOnAction(event -> {
                                TableKonten konten = getTableView().getItems().get(getIndex());

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Konfirmasi Penghapusan");
                                alert.setHeaderText(null);
                                alert.setContentText("Apakah Anda yakin ingin menghapus proyek ini?");

                                alert.showAndWait().ifPresent(response -> {
                                    if (response == ButtonType.OK) {
                                        try {
                                            Connection connection = DatabaseConnection.getConnection();
                                            KontenDAO kontenDAO = new KontenDAO(connection);
                                            kontenDAO.deleteKonten(konten.getNo()); 
                                            refreshTable();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            });
                        }

                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                anchorPane.getChildren().clear();
                                anchorPane.getChildren().addAll(btnEdit, btnDelete);

                                AnchorPane.setLeftAnchor(btnEdit, 0.0);
                                AnchorPane.setLeftAnchor(btnDelete, 40.0);

                                btnEdit.setPadding(new Insets(5));
                                btnDelete.setPadding(new Insets(5));

                                setGraphic(anchorPane);
                                setText(null);
                            }
                        }

                    };
                }
            });
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle kesalahan jika gagal mendapatkan koneksi atau data
        }
    }
}

public void refreshTable() {
    if (tableView == null) {
        System.out.println("Error: tableView is null in refreshTable()");
        return;
    }
    
    try {
        Connection connection = DatabaseConnection.getConnection();
        KontenDAO kontenDAO = new KontenDAO(connection);
        List<TableKonten> kontenList = kontenDAO.getAllKontens();
        ObservableList<TableKonten> observableKontenList = FXCollections.observableArrayList(kontenList);
        updateKontenIds(observableKontenList);
        tableView.setItems(observableKontenList);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    private void updateKontenIds(ObservableList<TableKonten> kontenList) {
        for (int i = 0; i < kontenList.size(); i++) {
            kontenList.get(i).setId(i + 1); // Reorder IDs to be sequential starting from 1
        }
    }

    private void showEditPopup(TableKonten konten) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editKonten.fxml"));
            Parent root = loader.load();

            EditKontenController controller = loader.getController();
            controller.setKonten(konten);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Edit Konten");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    @FXML
    private void bukaHalamanTambah(ActionEvent event) throws IOException {
        try {
            // Memuat FXML untuk Tambah Proyek
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tambahKonten.fxml"));
            Parent root = loader.load();

            // Mengambil controller dari loader
            TambahKontenController controller = loader.getController();
            controller.setKonten(konten); // Mengeset proyek jika diperlukan

            // Membuat Stage baru untuk menampilkan form Tambah Proyek
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Tambah Konten");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTable();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
        public void setKonten(TableKonten konten) {
        this.konten = konten;
        }
}