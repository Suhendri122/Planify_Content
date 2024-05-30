package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import com.mycompany.planifycontent.database.ProyekDAO;
import com.mycompany.planifycontent.database.UserDAO;
import com.mycompany.planifycontent.database.ClientDAO;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

public class ProyekController implements Initializable {

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
    
    // Buat koneksi ke ChoiceBox 'user' di proyek.fxml
    @FXML
    private ChoiceBox<String> userBox;
    
    @FXML
    private ChoiceBox<String> clientBox;

    private Stage mainStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            // Masukkan function buat ambil data mahasiswa di database
            getListMahasiswa();
        }
        catch(Exception e){
            System.out.println("error");
        }
        
        
        try{
            // Masukkan function buat ambil data mahasiswa di database
            getListKonsumen();
        }
        catch(Exception e){
            System.out.println("error");
        }

        no.setCellValueFactory(new PropertyValueFactory<>("id"));
        namaProyek.setCellValueFactory(new PropertyValueFactory<>("namaProyek"));
        picProyek.setCellValueFactory(new PropertyValueFactory<>("picProyek"));
        namaClient.setCellValueFactory(new PropertyValueFactory<>("namaClient"));
        noTelepon.setCellValueFactory(new PropertyValueFactory<>("noTelepon"));
        harga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        tglMulai.setCellValueFactory(new PropertyValueFactory<>("tglMulai"));
        tglSelesai.setCellValueFactory(new PropertyValueFactory<>("tglSelesai"));

        aksi.setCellFactory(new Callback<TableColumn<TableProyek, String>, TableCell<TableProyek, String>>() {
            @Override
            public TableCell<TableProyek, String> call(final TableColumn<TableProyek, String> param) {
                return new TableCell<TableProyek, String>() {
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

                            btnDetail.setOnAction(event -> {
                                TableProyek proyek = getTableView().getItems().get(getIndex());
                                try {
                                    bukaHalamanKonten(event);

                                    KontenController kontenController = App.getLoader().getController();
                                    kontenController.setProyek(proyek);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });

                            btnEdit.setOnAction(event -> {
                                TableProyek proyek = getTableView().getItems().get(getIndex());
                                showEditPopup(proyek);
                            });

                            btnDelete.setOnAction(event -> {
                                TableProyek proyek = getTableView().getItems().get(getIndex());

                                Alert alert = new Alert(AlertType.CONFIRMATION);
                                alert.setTitle("Konfirmasi Penghapusan");
                                alert.setHeaderText(null);
                                alert.setContentText("Apakah Anda yakin ingin menghapus proyek ini?");

                                alert.showAndWait().ifPresent(response -> {
                                    if (response == ButtonType.OK) {
                                        try {
                                            Connection connection = DatabaseConnection.getConnection();
                                            ProyekDAO proyekDAO = new ProyekDAO(connection);
                                            proyekDAO.deleteProyek(proyek.getId());
                                            refreshTable();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            });

                            anchorPane.getChildren().clear();

                            anchorPane.getChildren().addAll(btnDetail, btnEdit, btnDelete);

                            AnchorPane.setLeftAnchor(btnDetail, 0.0);
                            AnchorPane.setLeftAnchor(btnEdit, 40.0);
                            AnchorPane.setLeftAnchor(btnDelete, 80.0);

                            btnDetail.setPadding(new Insets(5));
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

   private void showEditPopup(TableProyek proyek) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editProyek.fxml"));
        Parent root = loader.load();

        EditProyekController controller = loader.getController();
        controller.setProyek(proyek);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Edit Proyek");
        stage.setScene(new Scene(root));
        stage.showAndWait();

        refreshTable(); // Refresh table view to show updated data
    } catch (IOException e) {
        e.printStackTrace();
    }
   }
    
    // Function buat masukkin nama user kedalam ChoiceBox
    private void getListMahasiswa() throws Exception{
        try{
            // Buat koneksi ke database user
            Connection connection = DatabaseConnection.getConnection();
            UserDAO dataUserDAO = new UserDAO(connection);
            
            // Simpan data ke dalam array untuk dikelola datanya
            List<TableUser> userData = dataUserDAO.getAllDataUsers();
            ObservableList<String> users = FXCollections.observableArrayList();

            // Masukkin data yang mau ditambahkan ke array untuk ditaruh ke ChoiceBox
            for(TableUser user : userData){
                users.add(user.getNama());
            }

            // Terapkan ke ChoiceBox
            userBox.setItems(users);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    private void getListKonsumen() throws Exception{
        try{
            // Buat koneksi ke database user
            Connection connection = DatabaseConnection.getConnection();
            ClientDAO dataClientDAO = new ClientDAO(connection);
            
            // Simpan data ke dalam array untuk dikelola datanya
            List<TableClient> clientData = dataClientDAO.getAllDataClients();
            ObservableList<String> users = FXCollections.observableArrayList();

            // Masukkin data yang mau ditambahkan ke array untuk ditaruh ke ChoiceBox
            for(TableClient client : clientData){
                users.add(client.getNama());
            }

            // Terapkan ke ChoiceBox
            clientBox.setItems(users);
        }
        catch(Exception e){
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
}
