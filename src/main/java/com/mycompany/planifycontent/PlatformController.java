package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.PlatformDAO;
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
import com.mycompany.planifycontent.TablePlatform;
import com.mycompany.planifycontent.database.PlatformDAO;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.scene.layout.AnchorPane;


public class PlatformController implements Initializable {

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Logout");
        alert.setHeaderText(null);
        alert.setContentText("Apakah Anda yakin ingin logout?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            App.setRoot("login");
        }
    }

    @FXML
    private TableView<TablePlatform> tableView;

    @FXML
    private TableColumn<TablePlatform, Integer> noColumn;

    @FXML
    private TableColumn<TablePlatform, String> platformColumn;

    @FXML
    private TableColumn<TablePlatform, String> aksiColumn;
    

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    if (tableView != null) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PlatformDAO dataPlatformDAO = new PlatformDAO(connection);
            List<TablePlatform> platformData = dataPlatformDAO.getAllPlatforms();
            ObservableList<TablePlatform> observablePlatformData = FXCollections.observableArrayList(platformData);

            tableView.setItems(observablePlatformData);

            // Inisialisasi kolom-kolom lain
            noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
            platformColumn.setCellValueFactory(new PropertyValueFactory<>("platform"));
            
            // Inisialisasi kolom aksi
            aksiColumn.setCellFactory(new Callback<TableColumn<TablePlatform, String>, TableCell<TablePlatform, String>>() {
                @Override
                public TableCell<TablePlatform, String> call(TableColumn<TablePlatform, String> param) {
                    return new TableCell<TablePlatform, String>() {
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
                                TablePlatform platform = getTableView().getItems().get(getIndex());
                                showEditPopup(platform);
                            });

                            btnDelete.setOnAction(event -> {
                            TablePlatform platform = getTableView().getItems().get(getIndex());

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Konfirmasi Penghapusan");
                            alert.setHeaderText(null);
                            alert.setContentText("Apakah Anda yakin ingin menghapus platform ini?");

                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.OK) {
                                    try {
                                        Connection connection = DatabaseConnection.getConnection();
                                        PlatformDAO platformDAO = new PlatformDAO(connection);
                                        platformDAO.deletePlatform(platform.getNo()); // Menggunakan nomor platform untuk penghapusan
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
            for (TablePlatform item : platformData) {
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
            PlatformDAO platformDAO = new PlatformDAO(connection);
            List<TablePlatform> platformList = platformDAO.getAllPlatforms();
            ObservableList<TablePlatform> observablePlatformList = FXCollections.observableArrayList(platformList);
            updatePlatformIds(observablePlatformList); // Reorder IDs before setting the items
            tableView.setItems(observablePlatformList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePlatformIds(ObservableList<TablePlatform> platformList) {
        for (int i = 0; i < platformList.size(); i++) {
            platformList.get(i).setId(i + 1); // Reorder IDs to be sequential starting from 1
        }
    }

    private void showEditPopup(TablePlatform platform) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editPlatform.fxml"));
            Parent root = loader.load();

            EditPlatformController controller = loader.getController();
            controller.setPlatform(platform);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Edit Platform");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshTable(); // Refresh table view to show updated data
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


