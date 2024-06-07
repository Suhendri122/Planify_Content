package com.mycompany.planifycontent;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.SQLException;
import com.mycompany.planifycontent.database.UserDAO;
import com.mycompany.planifycontent.database.DatabaseConnection;
import java.util.List;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class UserController implements Initializable {
    
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

    
    // Popup tambah, edit, dan filter
    @FXML
    private void bukaHalamanTambah(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahDataUser.fxml"));
        Parent root = fxmlLoader.load();    
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tambah Data User"); // Mengatur judul window popup
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
    private TableView<TableUser> tableView;

    @FXML
    private TableColumn<TableUser, Integer> no;

    @FXML
    private TableColumn<TableUser, String> user;

    @FXML
    private TableColumn<TableUser, String> email;
    
    @FXML
    private TableColumn<TableUser, String> aksiColumn;
    
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    if (tableView != null) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            UserDAO UserDAO = new UserDAO(connection);
            List<TableUser> userData = UserDAO.getAllUsers();
            ObservableList<TableUser> observableUserData = FXCollections.observableArrayList(userData);

            tableView.setItems(observableUserData);

            // Initialize columns
            no.setCellValueFactory(new PropertyValueFactory<>("no"));
            user.setCellValueFactory(new PropertyValueFactory<>("user"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            
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
                                alert.setContentText("Apakah Anda yakin ingin menghapus proyek ini?");

                                alert.showAndWait().ifPresent(response -> {
                                    if (response == ButtonType.OK) {
                                        try {
                                            Connection connection = DatabaseConnection.getConnection();
                                            UserDAO userDAO = new UserDAO(connection);
                                            userDAO.deleteUser(user.getNo()); 
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
        try {
            Connection connection = DatabaseConnection.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            List<TableUser> userList = userDAO.getAllUsers();
            ObservableList<TableUser> observableUserList = FXCollections.observableArrayList(userList);
            updateUserIds(observableUserList); // Reorder IDs before setting the items
            tableView.setItems(observableUserList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUserIds(ObservableList<TableUser> userList) {
        for (int i = 0; i < userList.size(); i++) {
            userList.get(i).setId(i + 1); // Reorder IDs to be sequential starting from 1
        }
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
