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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
private void handleTambahButtonAction(ActionEvent event) {
    showAddPopup();
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
    private ChoiceBox<String> picKontenChoiceBox;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private DatePicker tglPostDatePicker;
    @FXML
    private DatePicker deadlineDatePicker;
    @FXML
    private Button searchButton;
    

    private UserDAO userDAO;
    private KontenDAO kontenDAO;
    
    @FXML
    private TableColumn<TableKonten, String> aksiColumn;

    private ObservableList<TableKonten> kontenData;
    
    private TableProyek proyek; // Deklarasi variabel proyek

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
            userDAO = new UserDAO(connection);
            kontenDAO = new KontenDAO(connection);
            
            
            List<TableKonten> kontenList = kontenDAO.getAllKontens(picKontenChoiceBox.getValue(), statusChoiceBox.getValue(), tglPostDatePicker.getValue(), deadlineDatePicker.getValue() );
            kontenData = FXCollections.observableArrayList(kontenList);
            ObservableList<TableKonten> observableKontenData = FXCollections.observableArrayList(kontenData);
            
            // Add "Semua" item to picKontenChoiceBox
            picKontenChoiceBox.getItems().add("Semua");

            // Add "Semua" item to statusChoiceBox
            statusChoiceBox.getItems().add("Semua");
            
            List<String> userNames = userDAO.getAllUserNames();
            picKontenChoiceBox.getItems().addAll(userNames);
            
             List<String> statuses = Arrays.asList("Belum", "Sedang Berlangsung", "Selesai"); // hardcoded list of statuses
            statusChoiceBox.getItems().addAll(statuses);
            
            searchButton.setOnAction(event -> filterData());

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

            // Atur nomor untuk setiap item
            int index = 1;
            for (TableKonten item : kontenData) {
                item.noProperty().set(index++);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    public void refreshTable() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            kontenDAO = new KontenDAO(connection);
            List<TableKonten> kontenList = kontenDAO.getAllKontens(picKontenChoiceBox.getValue(), statusChoiceBox.getValue(), tglPostDatePicker.getValue(), deadlineDatePicker.getValue());
            ObservableList<TableKonten> observableKontenList = FXCollections.observableArrayList(kontenList);
            updateKontenIds(observableKontenList);  // Reorder IDs before setting the items
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
    
    private void showAddPopup() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tambah_data.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Tambah Data");
        stage.setScene(new Scene(root));
        stage.showAndWait();

        // Refresh the table view after adding data
        refreshTable();
    } catch (IOException e) {
        e.printStackTrace();
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

            refreshTable(); // Refresh table view to show updated data
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
  @FXML
private void handleSearchButtonAction(ActionEvent event) {
    filterData();
}


    
 @FXML
private void filterData() {
    String picKontenFilterValue = picKontenChoiceBox.getSelectionModel().getSelectedItem();
    String statusFilterValue = statusChoiceBox.getSelectionModel().getSelectedItem();
    LocalDate tglPostFilterValue = tglPostDatePicker.getValue();
    LocalDate deadlineFilterValue = deadlineDatePicker.getValue();

    ObservableList<TableKonten> tableViewList = tableView.getItems();
    tableViewList.clear();

    try {
        Connection connection = DatabaseConnection.getConnection();
        KontenDAO kontenDAO = new KontenDAO(connection);
        List<TableKonten> kontenList = kontenDAO.getAllKontens(picKontenFilterValue, statusFilterValue, tglPostFilterValue, deadlineFilterValue);
        tableViewList.addAll(kontenList);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    
    
}