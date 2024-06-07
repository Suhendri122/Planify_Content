package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DashboardDAO;
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
import com.mycompany.planifycontent.TableMedia;
import com.mycompany.planifycontent.database.MediaDAO;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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



public class MediaController implements Initializable{
    
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

    
    
    //popup tambah, edit, dan filter
    
    @FXML
    private void bukaHalamanTambah(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahDataMedia.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tambah Data Media");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
    }

    
    @FXML
    private void popupBtnBatal(ActionEvent event) {
        // Mendapatkan stage dari event
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Menutup stage (popup)
        stage.close();
    }
    
    
    @FXML
    private TableView<TableMedia> tableView;
     
     @FXML
    private TableColumn<TableMedia, Integer> noColumn;

    @FXML
    private TableColumn<TableMedia, String> mediaColumn;
    
    @FXML
    private TableColumn<TableMedia, String> aksiColumn;
     
    @Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    if (tableView != null) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            MediaDAO MediaDAO = new MediaDAO(connection);
            List<TableMedia> mediaData = MediaDAO.getAllMedia();
            ObservableList<TableMedia> observableMediaData = FXCollections.observableArrayList(mediaData);

            tableView.setItems(observableMediaData);

            // Inisialisasi kolom-kolom lain
            noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
            mediaColumn.setCellValueFactory(new PropertyValueFactory<>("media"));
            
            // Inisialisasi kolom aksi
            aksiColumn.setCellFactory(new Callback<TableColumn<TableMedia, String>, TableCell<TableMedia, String>>() {
                @Override
                public TableCell<TableMedia, String> call(TableColumn<TableMedia, String> param) {
                    return new TableCell<TableMedia, String>() {
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
                                TableMedia media = getTableView().getItems().get(getIndex());
                                showEditPopup(media);
                            });

                            btnDelete.setOnAction(event -> {
                                TableMedia media = getTableView().getItems().get(getIndex());

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Konfirmasi Penghapusan");
                                alert.setHeaderText(null);
                                alert.setContentText("Apakah Anda yakin ingin menghapus proyek ini?");

                                alert.showAndWait().ifPresent(response -> {
                                    if (response == ButtonType.OK) {
                                        try {
                                            Connection connection = DatabaseConnection.getConnection();
                                            MediaDAO mediaDAO = new MediaDAO(connection);
                                            mediaDAO.deleteMedia(media.getNo());
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
            for (TableMedia item : mediaData) {
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
            MediaDAO mediaDAO = new MediaDAO(connection);
            List<TableMedia> mediaList = mediaDAO.getAllMedia();
            ObservableList<TableMedia> observableMediaList = FXCollections.observableArrayList(mediaList);
            updateMediaIds(observableMediaList); // Reorder IDs before setting the items
            tableView.setItems(observableMediaList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateMediaIds(ObservableList<TableMedia> mediaList) {
        for (int i = 0; i < mediaList.size(); i++) {
            mediaList.get(i).setId(i + 1); // Reorder IDs to be sequential starting from 1
        }
    }

    private void showEditPopup(TableMedia media) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editMedia.fxml"));
            Parent root = loader.load();

            EditMediaController controller = loader.getController();
            controller.setMedia(media);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Edit Media");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshTable(); // Refresh table view to show updated data
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}