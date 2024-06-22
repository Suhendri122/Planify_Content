package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.UserDAO;
import com.mycompany.planifycontent.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
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
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.mycompany.planifycontent.TableUser;
import com.mycompany.planifycontent.database.UserDAO;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.scene.layout.AnchorPane;


public class UserController implements Initializable {

    @FXML
    private TableView<TableUser> tableView;

    @FXML
    private TableColumn<TableUser, Integer> no;

    @FXML
    private TableColumn<TableUser, String> user;
    
    @FXML
    private TableColumn<TableUser, String> email;

    @FXML
    private TextField nameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private TextField passwordField;

    private ObservableList<TableUser> userData;

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
private void bukaHalamanTambah(ActionEvent event) {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahUser.fxml"));
        Parent root = fxmlLoader.load();    
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tambah User");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
        initialize(null, null); // Refresh data setelah pop-up ditutup
    } catch (IOException e) {
        e.printStackTrace();
        // Tampilkan pesan kesalahan
        System.out.println("Error loading FXML: " + e.getMessage());
    }
}


    @FXML
    private TableColumn<TableUser, String> aksiColumn;
    

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    userData = FXCollections.observableArrayList();
    if (tableView != null) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            UserDAO dataUserDAO = new UserDAO(connection);
            List<TableUser> userData = dataUserDAO.getAllUsers();
            ObservableList<TableUser> observableUserData = FXCollections.observableArrayList(userData);

            tableView.setItems(observableUserData);

            // Inisialisasi kolom-kolom lain
            no.setCellValueFactory(new PropertyValueFactory<>("no"));
            user.setCellValueFactory(new PropertyValueFactory<>("user"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            
            // Inisialisasi kolom aksi
            aksiColumn.setCellFactory(new Callback<TableColumn<TableUser, String>, TableCell<TableUser, String>>() {
            @Override
            public TableCell<TableUser, String> call(TableColumn<TableUser, String> param) {
                return new TableCell<TableUser, String>() {
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
                            TableUser user = getTableView().getItems().get(getIndex());
                            showEditPopup(user);
                        });

                        btnDelete.setOnAction(event -> {
                            TableUser user = getTableView().getItems().get(getIndex()); 

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Konfirmasi Penghapusan");
                            alert.setHeaderText(null);
                            alert.setContentText("Apakah Anda yakin ingin menghapus user ini?");

                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.OK) {
                                    try {
                                        Connection connection = DatabaseConnection.getConnection();
                                        UserDAO userDAO = new UserDAO(connection);
                                        userDAO.deleteUser(user.getId()); // Menggunakan nomor user untuk penghapusan
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
            for (TableUser item : userData) {
                item.noProperty().set(index++);
            }
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
            UserDAO userDAO = new UserDAO(connection);
            List<TableUser> userList = userDAO.getAllUsers();
            ObservableList<TableUser> observableUserList = FXCollections.observableArrayList(userList);
            updateUserlds(observableUserList); // Reorder IDs before setting the items
            tableView.setItems(observableUserList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUserlds(ObservableList<TableUser> mediaList) {
        for (int i = 0; i < mediaList.size(); i++) {
            mediaList.get(i).setId(i + 1); // Reorder IDs to be sequential starting from 1
        }
    }
    @FXML
    private void handleTambah(ActionEvent event) {
        if (nameField != null && emailField != null && passwordField != null) { // Memastikan kedua field telah diinisialisasi
            String userName = nameField.getText();
            String email = emailField.getText(); // Mengambil nilai dari field email
            String password = passwordField.getText(); // Mengambil nilai dari field password
            if (userName != null && !userName.isEmpty() && email != null && !email.isEmpty()) { // Memastikan nilai userName dan email tidak kosong
                try {
                    Connection connection = DatabaseConnection.getConnection();
                    UserDAO dataUserDAO = new UserDAO(connection);
                    dataUserDAO.tambahUser(userName, email, password); // Memanggil metode tambahUser dengan dua parameter
                    List<TableUser> userList = dataUserDAO.getAllUsers();

                    if (userData != null) {
                        userData.setAll(userList);

                        int index = 1;
                        for (TableUser item : userData) {
                            item.setNo(index++);
                        }
                        refreshTable();
                    }
                } catch (SQLException e) {
                    // Tangani pengecualian SQL dan tampilkan pesan kesalahan
                    e.printStackTrace();
                    showErrorMessage("Error adding user", "An error occurred while adding the media. Please try again.");
                }
            } else {
                showErrorMessage("Peringatan", "Harap Lengkapi Semua Kolom Terlebih Dahulu!");
            }
            closeWindow();
        }
    }

    @FXML
    private void popupBtnBatal(ActionEvent event) {
        closeWindow();
    }
    
    @FXML
    private void logout(ActionEvent event) throws IOException {
        // Membuat dialog konfirmasi
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Logout");
        alert.setHeaderText(null);
        alert.setContentText("Apakah Anda yakin ingin logout?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            App.setRoot("login");
        }
    }

    private void closeWindow() {
        if (nameField != null) {
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        }
    }
        private void showErrorMessage(String title, String message) {
        // You can use an Alert to display error messages to the user
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showEditPopup(TableUser user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editUser.fxml"));
            Parent root = loader.load();

            EditUserController controller = loader.getController();
            controller.setUser(user);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Edit User");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshTable(); // Refresh table view to show updated data
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
