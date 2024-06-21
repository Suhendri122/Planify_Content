package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import com.mycompany.planifycontent.database.ProyekDAO;
import com.mycompany.planifycontent.database.UserDAO;
import com.mycompany.planifycontent.database.ClientDAO;
import com.mycompany.planifycontent.TableProyek;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ProyekController implements Initializable {
    Map<Integer, String> users = new HashMap<Integer, String>();
    Map<Integer, String> clients = new HashMap<Integer, String>();
    
    @FXML
    private Button btnTambah;
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
    private TableColumn<TableProyek, Double> harga;
    @FXML
    private TableColumn<TableProyek, String> tglMulai;
    @FXML
    private TableColumn<TableProyek, String> tglSelesai;
    @FXML
    private TableColumn<TableProyek, String> aksi;
    
    @FXML
    private ChoiceBox<String> userBox;
    @FXML
    private ChoiceBox<String> clientBox;
    @FXML
    private ChoiceBox<String> picProyekBox;
    @FXML
    private DatePicker tglMulaiDatePicker;
    @FXML
    private DatePicker tglSelesaiDatePicker;
    @FXML
    private Button filterButton;
    @FXML
    private TextField txtNamaProyek;
    @FXML
    private ChoiceBox<String> choicePicProyek;
    @FXML
    private ChoiceBox<String> choiceNamaClient;
    @FXML
    private TextField txtNoTelepon;
    @FXML
    private TextField txtHarga;
    @FXML
    private DatePicker dpTglMulai;
    @FXML
    private DatePicker dpTglSelesai;

    private ProyekDAO proyekDAO;
    private Stage mainStage;
    private TableProyek proyek;

    String pic = "0";  // Semua PIC
    String client = "0";  // Semua Client
    String startDate = "0";  // Tanggal mulai tidak di-filter
    String endDate = "0";  // Tanggal selesai tidak di-filter

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tglMulaiDatePicker.setValue(LocalDate.now());
        tglSelesaiDatePicker.setValue(LocalDate.now());
        
        if (tableView != null){
            try {
                Connection connection = DatabaseConnection.getConnection();
                ProyekDAO proyekDAO = new ProyekDAO(connection);
                List<TableProyek> proyekDataList = proyekDAO.getAllProyek(pic, client, startDate, endDate);
                ObservableList<TableProyek> observableProyekData = FXCollections.observableArrayList(proyekDataList);
                
                int index = 1;
                for (TableProyek item : proyekDataList) {
                    item.setId(index++);
                }
            
                tableView.setItems(observableProyekData);
        
                no.setCellValueFactory(new PropertyValueFactory<TableProyek, Integer>("id"));
                namaProyek.setCellValueFactory(new PropertyValueFactory<>("namaProyek"));
                picProyek.setCellValueFactory(new PropertyValueFactory<>("picProyek"));
                namaClient.setCellValueFactory(new PropertyValueFactory<>("namaClient"));
                noTelepon.setCellValueFactory(new PropertyValueFactory<>("noTelepon"));
                harga.setCellValueFactory(new PropertyValueFactory<>("hargaFormatted"));
                tglMulai.setCellValueFactory(new PropertyValueFactory<>("tglMulaiFormatted"));
                tglSelesai.setCellValueFactory(new PropertyValueFactory<>("tglSelesaiFormatted"));

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
                                        refreshTable();
                                        
                                    });

                                    btnDelete.setOnAction(event -> {
                                        TableProyek proyek = getTableView().getItems().get(getIndex());

                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
                
            List<String> users = proyekDAO.getAllUsers();
            List<String> clients = proyekDAO.getAllClients();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            userBox.getItems().add("Semua");
            clientBox.getItems().add("Semua");

            userBox.setValue("Semua");
            clientBox.setValue("Semua");

            users.put(0, "Semua");
            clients.put(0, "Semua");

            try{
                getListMahasiswa();
            }
            catch(Exception e){
                System.out.println("error");
            }


            try{
                getListKonsumen();
            }
            catch(Exception e){
                System.out.println("error");
            }

            userBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    for(Map.Entry<Integer, String> u : users.entrySet()){
                        if(u.getValue().equals(newValue)){
                            pic = u.getKey().toString();
                        }
                    }
                }
            });

            clientBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue!= null) {
                    for(Map.Entry<Integer, String> c : clients.entrySet()){
                        if(c.getValue().equals(newValue)){
                            client = c.getKey().toString();
                        }
                    }
                }
            });

            filterButton.setOnAction((e -> {
                refreshFilter();
            }));
            
        }
}

    private void refreshTable() {
        if (tableView == null) {
            System.out.println("Error: tableView is null in refreshTable()");
            return;
        }
        
        try {
            Connection connection = DatabaseConnection.getConnection();
            ProyekDAO proyekDAO = new ProyekDAO(connection);
            List<TableProyek> proyekList = proyekDAO.getAllProyek("0", "0", "", ""); // Sesuaikan dengan kebutuhan Anda
            ObservableList<TableProyek> observableProyekList = FXCollections.observableArrayList(proyekList);
            updateProyekIds(observableProyekList); // Reorder IDs before setting the items
            tableView.setItems(observableProyekList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void refreshFilter() {
        try {
            String startDate = tglMulaiDatePicker.getValue() == null ? "" : tglMulaiDatePicker.getValue().toString();
            String endDate = tglSelesaiDatePicker.getValue() == null ? "" : tglSelesaiDatePicker.getValue().toString();

            if ("Semua".equals(userBox.getValue())) {
                pic = "0";  // Semua PIC
            }

            if ("Semua".equals(clientBox.getValue())) {
                client = "0";  // Semua Client
            }

            Connection connection = DatabaseConnection.getConnection();
            ProyekDAO proyekDAO = new ProyekDAO(connection);
            List<TableProyek> proyekList = proyekDAO.getAllProyek(pic, client, startDate, endDate);
            ObservableList<TableProyek> observableProyekList = FXCollections.observableArrayList(proyekList);

            updateProyekIds(observableProyekList);  // Reorder IDs before setting the items
            tableView.setItems(observableProyekList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    private void updateProyekIds(ObservableList<TableProyek> proyekList) {
        for (int i = 0; i < proyekList.size(); i++) {
            proyekList.get(i).setId(i + 1);
        }
    }

     private void filterDataByPIC(String picProyek) {
    try {
        Connection connection = DatabaseConnection.getConnection();
        ProyekDAO proyekDAO = new ProyekDAO(connection);
        List<TableProyek> proyekList = proyekDAO.getProyekByPIC(picProyek);
        ObservableList<TableProyek> observableProyekList = FXCollections.observableArrayList(proyekList);
        tableView.setItems(observableProyekList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void filterDataByClient(String clientName) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            ProyekDAO proyekDAO = new ProyekDAO(connection);
            List<TableProyek> proyekList = proyekDAO.getProyekByClient(clientName);
            ObservableList<TableProyek> observableProyekList = FXCollections.observableArrayList(proyekList);
            tableView.setItems(observableProyekList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void filterDataByTglMulai(LocalDate tglMulai) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            ProyekDAO proyekDAO = new ProyekDAO(connection);
            List<TableProyek> proyekList = proyekDAO.getProyekByTglMulai(tglMulai);
            ObservableList<TableProyek> observableProyekList = FXCollections.observableList(proyekList); // Konversi daftar ke ObservableList
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getListMahasiswa() throws Exception{
        try{
            Connection connection = DatabaseConnection.getConnection();
            UserDAO dataUserDAO = new UserDAO(connection);
            List<TableUser> userData = dataUserDAO.getAllUsers();

            for(TableUser user : userData){
                Map<Integer, String> x = new HashMap<Integer, String>();
                x.put(user.getNo(), user.getUser());
            
                users.put(user.getNo(), user.getUser());

                userBox.getItems().addAll(user.getUser());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void getListKonsumen() throws Exception{
        try{
            Connection connection = DatabaseConnection.getConnection();
            ClientDAO dataClientDAO = new ClientDAO(connection);

            List<TableClient> clientData = dataClientDAO.getAllClients();
            ObservableList<String> users  = FXCollections.observableArrayList();

            for(TableClient client : clientData){
                clients.put(client.getNo(), client.getNama());
                clientBox.getItems().add(client.getNama());
            }
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
        App.setRoot("konten") ;
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
    private void bukaHalamanTambah(ActionEvent event) throws IOException {
        try {
            // Memuat FXML untuk Tambah Proyek
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tambahProyek.fxml"));
            Parent root = loader.load();

            // Mengambil controller dari loader
            TambahProyekController controller = loader.getController();
            controller.setProyek(proyek); // Mengeset proyek jika diperlukan

            // Membuat Stage baru untuk menampilkan form Tambah Proyek
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Tambah Proyek");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTable();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setProyek(TableProyek proyek) {
        this.proyek = proyek;
        }

}
