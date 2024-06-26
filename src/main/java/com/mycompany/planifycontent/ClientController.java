package com.mycompany.planifycontent;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import com.mycompany.planifycontent.database.ClientDAO;
import com.mycompany.planifycontent.database.DatabaseConnection;
import java.util.List;
import javafx.scene.control.ButtonType;

public class ClientController implements Initializable {

    @FXML
    private TableView<TableClient> tableView;

    @FXML
    private TableColumn<TableClient, Integer> noColumn;

    @FXML
    private TableColumn<TableClient, String> namaColumn;

    @FXML
    private TableColumn<TableClient, String> noTelpColumn;

    @FXML
    private TableColumn<TableClient, String> usahaColumn;

    @FXML
    private TableColumn<TableClient, String> aksiColumn;

    @FXML
    private ChoiceBox<String> namaclient;

    @FXML
    private ChoiceBox<String> namausaha;

    @FXML
    private Button filtercari;

    @FXML
    private TextField namaField;

    @FXML
    private TextField noTelpField;

    @FXML
    private TextField usahaField;

    private ObservableList<TableClient> clientData = FXCollections.observableArrayList();

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahClient.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Tambah Data Client");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
            initialize(null, null);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (tableView != null) {
            try {
                Connection connection = DatabaseConnection.getConnection();
                ClientDAO clientDAO = new ClientDAO(connection);

                List<TableClient> clientList = clientDAO.getAllClients();
                clientData = FXCollections.observableArrayList(clientList);
                tableView.setItems(clientData);

                noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
                namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
                noTelpColumn.setCellValueFactory(new PropertyValueFactory<>("no_telp"));
                usahaColumn.setCellValueFactory(new PropertyValueFactory<>("usaha"));

                aksiColumn.setCellFactory(new Callback<TableColumn<TableClient, String>, TableCell<TableClient, String>>() {
                    @Override
                    public TableCell<TableClient, String> call(TableColumn<TableClient, String> param) {
                        return new TableCell<TableClient, String>() {
                            final Button btnEdit = new Button();
                            final Button btnDelete = new Button();
                            final HBox hbox = new HBox(btnEdit, btnDelete);
                            final AnchorPane anchorPane = new AnchorPane();

                            {
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

                                btnEdit.setOnAction(event -> {
                                    TableClient client = getTableView().getItems().get(getIndex());
                                    showEditPopup(client);
                                });

                                btnDelete.setOnAction(event -> {
                                    TableClient client = getTableView().getItems().get(getIndex());

                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Konfirmasi Penghapusan");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Apakah Anda yakin ingin menghapus client ini?");

                                    alert.showAndWait().ifPresent(response -> {
                                        if (response == ButtonType.OK) {
                                            try {
                                                Connection connection = DatabaseConnection.getConnection();
                                                ClientDAO clientDAO = new ClientDAO(connection);
                                                clientDAO.deleteClient(client.getId());
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

                int index = 1;
                for (TableClient item : clientList) {
                    item.noProperty().set(index++);
                }

                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (namaclient != null && namausaha != null) {
            try {
                Connection connection = DatabaseConnection.getConnection();
                ClientDAO clientDAO = new ClientDAO(connection);

                ObservableList<String> namaItems = FXCollections.observableArrayList();
                ObservableList<String> usahaItems = FXCollections.observableArrayList();

                List<TableClient> clientList = clientDAO.getAllClients();
                for (TableClient client : clientList) {
                    if (!namaItems.contains(client.getNama())) {
                        namaItems.add(client.getNama());
                    }
                    if (!usahaItems.contains(client.getUsaha())) {
                        usahaItems.add(client.getUsaha());
                    }
                }

                namaclient.setItems(namaItems);
                namausaha.setItems(usahaItems);

                namaclient.getItems().add(0, "Semua");
                namausaha.getItems().add(0, "Semua");

                namaclient.setValue("Semua");
                namausaha.setValue("Semua");

                filtercari.setOnAction(event -> {
                    filterCariAction(event);
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void filterCariAction(ActionEvent event) {
        String nama = namaclient.getSelectionModel().getSelectedItem();
        String usaha = namausaha.getSelectionModel().getSelectedItem();

        if ("Semua".equals(nama) && "Semua".equals(usaha)) {
            try {
                Connection connection = DatabaseConnection.getConnection();
                ClientDAO dataClientDAO = new ClientDAO(connection);
                List<TableClient> allData = dataClientDAO.getAllClients();
                tableView.setItems(FXCollections.observableArrayList(allData));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Connection connection = DatabaseConnection.getConnection();
                ClientDAO dataClientDAO = new ClientDAO(connection);
                List<TableClient> filteredData = dataClientDAO.getDataClientsByFilter(
                        "Semua".equals(nama) ? null : nama,
                        "Semua".equals(usaha) ? null : usaha
                );
                tableView.setItems(FXCollections.observableArrayList(filteredData));
            } catch (SQLException e) {
                e.printStackTrace();
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
            ClientDAO clientDAO = new ClientDAO(connection);
            List<TableClient> clientList = clientDAO.getAllClients();
            ObservableList<TableClient> observableClientList = FXCollections.observableArrayList(clientList);
            updateClientIds(observableClientList);
            tableView.setItems(observableClientList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateClientIds(ObservableList<TableClient> clientList) {
        for (int i = 0; i < clientList.size(); i++) {
            clientList.get(i).setNo(i + 1);
        }
    }

    @FXML
    private void handleTambah(ActionEvent event) {
        if (namaField != null && noTelpField != null && usahaField != null) {
            String nama = namaField.getText();
            String noTelp = noTelpField.getText();
            String usaha = usahaField.getText();
            if (!nama.isEmpty() && !noTelp.isEmpty() && !usaha.isEmpty()) {
                try {
                    Connection connection = DatabaseConnection.getConnection();
                    ClientDAO clientDAO = new ClientDAO(connection);
                    TableClient newClient = new TableClient(0, nama, noTelp, usaha, "");
                    clientDAO.addClient(newClient);
                    List<TableClient> clientList = clientDAO.getAllClients();

                    if (clientData != null) {
                        clientData.setAll(clientList);

                        int index = 1;
                        for (TableClient item : clientData) {
                            item.setNo(index++);
                        }
                        refreshTable();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showErrorMessage("Error adding client", "An error occurred while adding the client. Please try again.");
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Logout");
        alert.setHeaderText(null);
        alert.setContentText("Apakah Anda yakin ingin logout?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            App.setRoot("login");
        }
    }

    private void closeWindow() {
        if (namaField != null && noTelpField != null && usahaField != null) {
            Stage stage = (Stage) namaField.getScene().getWindow();
            stage.close();
        }
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showEditPopup(TableClient client) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editClient.fxml"));
            Parent root = loader.load();

            EditClientController controller = loader.getController();
            controller.setClient(client);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Edit Client");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFilterCari(ActionEvent event) {
        String nama = namaField.getText();
        String usaha = usahaField.getText();

        try {
            Connection connection = DatabaseConnection.getConnection();
            ClientDAO clientDAO = new ClientDAO(connection);
            List<TableClient> filteredClients = clientDAO.getDataClientsByFilter(nama, usaha);
            ObservableList<TableClient> observableFilteredClients = FXCollections.observableArrayList(filteredClients);
            tableView.setItems(observableFilteredClients);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
