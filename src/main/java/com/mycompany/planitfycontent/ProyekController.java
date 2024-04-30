package com.mycompany.planitfycontent;

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

public class ProyekController {

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
    private void bukaHalamanDataPlatform(ActionEvent event) throws IOException {
        App.setRoot("dataPlatform");
    }

    @FXML
    private void bukaHalamanDataMedia(ActionEvent event) throws IOException {
        App.setRoot("dataMedia");
    }

    @FXML
    private void bukaHalamanDataClient(ActionEvent event) throws IOException {
        App.setRoot("dataClient");
    }

    @FXML
    private void bukaHalamanDataUser(ActionEvent event) throws IOException {
        App.setRoot("dataUser");
    }
    
    
    //popup tambah, edit, dan filter
    
    @FXML
    private void bukaHalamanTambah(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tambahDataProyek.fxml"));
        Parent root = fxmlLoader.load();
        
        // Get the controller of the popup
        TambahDataProyekController popupController = fxmlLoader.getController();
        // Set the parent controller
        popupController.setParentController(this);
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tambah Data Proyek"); // Set the popup title
        stage.setScene(new Scene(root));
        stage.showAndWait(); // Show the popup and wait until it's closed
    }

    // Method to handle cancel action in the popup
    public void popupBtnBatal() {
        System.out.println("Batal button clicked");
    }



    
    
}