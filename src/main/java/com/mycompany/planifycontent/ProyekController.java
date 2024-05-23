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
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;



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
    private void bukaHalamanKonten(ActionEvent event) throws IOException {
        App.setRoot("konten");
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

private Stage mainStage; // Tambahkan properti mainStage

public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
}

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    // Inisialisasi kolom
    no.setCellValueFactory(new PropertyValueFactory<>("id"));
    namaProyek.setCellValueFactory(new PropertyValueFactory<>("namaProyek"));
    picProyek.setCellValueFactory(new PropertyValueFactory<>("picProyek"));
    namaClient.setCellValueFactory(new PropertyValueFactory<>("namaClient"));
    noTelepon.setCellValueFactory(new PropertyValueFactory<>("noTelepon"));
    harga.setCellValueFactory(new PropertyValueFactory<>("harga"));
    tglMulai.setCellValueFactory(new PropertyValueFactory<>("tglMulai"));
    tglSelesai.setCellValueFactory(new PropertyValueFactory<>("tglSelesai"));

    // Menambahkan cell factory untuk kolom aksi
    aksi.setCellFactory(new Callback<TableColumn<TableProyek, String>, TableCell<TableProyek, String>>() {
        @Override
        public TableCell<TableProyek, String> call(final TableColumn<TableProyek, String> param) {
            final TableCell<TableProyek, String> cell;
            cell = new TableCell<TableProyek, String>() {
                
                final Button btnDetail = new Button();
                final Button btnEdit = new Button();
                final Button btnDelete = new Button();
                final AnchorPane anchorPane = new AnchorPane();

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        // Load images for buttons
                        ImageView ivDetail = new ImageView(new Image(getClass().getResourceAsStream("/assets/detail.png")));
                        ivDetail.setFitHeight(20);
                        ivDetail.setFitWidth(20);
                        btnDetail.setGraphic(ivDetail);

                        ImageView ivEdit = new ImageView(new Image(getClass().getResourceAsStream("/assets/edit.png")));
                        ivEdit.setFitHeight(20);
                        ivEdit.setFitWidth(20);
                        btnEdit.setGraphic(ivEdit);

                        ImageView ivDelete = new ImageView(new Image(getClass().getResourceAsStream("/assets/delete.png")));
                        ivDelete.setFitHeight(20);
                        ivDelete.setFitWidth(20);
                        btnDelete.setGraphic(ivDelete);

                        // Set actions for buttons
                        btnDetail.setOnAction(event -> {
                            TableProyek proyek = getTableView().getItems().get(getIndex());
                            try {
                                // Panggil metode bukaHalamanKonten dari ProyekController
                                bukaHalamanKonten(event);
                                
                                // Mengirim data proyek ke controller konten
                                KontenController kontenController = App.getLoader().getController();
                                kontenController.setProyek(proyek);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        
                        
                        btnDelete.setOnAction(event -> {
                            TableProyek proyek = getTableView().getItems().get(getIndex());
                            try {
                                Connection connection = DatabaseConnection.getConnection();
                                ProyekDAO proyekDAO = new ProyekDAO(connection);
                                proyekDAO.deleteProyek(proyek.getId());
                                refreshTable();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });

                        // Clear existing children from AnchorPane
                        anchorPane.getChildren().clear();

                        // Add buttons to AnchorPane
                        anchorPane.getChildren().addAll(btnDetail, btnEdit, btnDelete);

                        // Set button positions in AnchorPane
                        AnchorPane.setLeftAnchor(btnDetail, 0.0);
                        AnchorPane.setLeftAnchor(btnEdit, 40.0);
                        AnchorPane.setLeftAnchor(btnDelete, 80.0);

                        // Set padding for buttons
                        btnDetail.setPadding(new Insets(5));
                        btnEdit.setPadding(new Insets(5));
                        btnDelete.setPadding(new Insets(5));

                        setGraphic(anchorPane);
                        setText(null);
                    }
                }
            };
            return cell;
        }
    });

    // Load data from database
    refreshTable();
}

public void refreshTable() {
    try {
        Connection connection = DatabaseConnection.getConnection();
        ProyekDAO proyekDAO = new ProyekDAO(connection);
        List<TableProyek> proyekList = proyekDAO.getAllProyek();
        ObservableList<TableProyek> observableProyekList = FXCollections.observableArrayList(proyekList);
        tableView.setItems(observableProyekList);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


}

