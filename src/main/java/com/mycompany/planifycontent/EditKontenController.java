package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import com.mycompany.planifycontent.database.KontenDAO;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;

public class EditKontenController implements Initializable {
    
    @FXML
    private ChoiceBox<String> kontenUserField;
    @FXML
    private ChoiceBox<String> kontenMediaField;
    @FXML
    private ChoiceBox<String> kontenPlatformField;
    @FXML
    private TextField kontenLinkDesainField;
    @FXML
    private TextField kontenTemaField;
    @FXML
    private DatePicker kontenDeadlineField; // Menggunakan DatePicker
    @FXML
    private DatePicker kontenTglPostField; // Menggunakan DatePicker
    @FXML
    private ChoiceBox<String> kontenStatusField;

    private TableKonten konten;

        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            KontenDAO kontenDAO = new KontenDAO(connection);

            // Mengisi ChoiceBox dengan data dari database
            List<String> users = kontenDAO.getAllUsers();
            List<String> media = kontenDAO.getAllMedia();
            List<String> platforms = kontenDAO.getAllPlatforms();
            List<String> statuses = kontenDAO.getAllStatuses();

            kontenUserField.getItems().addAll(users);
            kontenMediaField.getItems().addAll(media);
            kontenPlatformField.getItems().addAll(platforms);
            kontenStatusField.getItems().addAll(statuses);

            if (konten != null) {
                fillForm();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
        }
    }

    public void setKonten(TableKonten konten) {
        this.konten = konten;
        if (konten != null) {
            fillForm();
        }
    }

private void fillForm() {
    kontenUserField.setValue(konten.getNamaUser());
    kontenMediaField.setValue(konten.getNamaMedia());
    kontenPlatformField.setValue(konten.getNamaPlatform());
    kontenLinkDesainField.setText(konten.getLinkDesain());
    kontenTemaField.setText(konten.getTema());
    kontenDeadlineField.setValue(LocalDate.parse(konten.getDeadline()));
    kontenTglPostField.setValue(LocalDate.parse(konten.getTglPost()));
    
    // Memastikan pilihan status yang tepat terpilih
    kontenStatusField.setValue(konten.getStatus());
}


    @FXML
private void saveChanges(ActionEvent event) {
    try {
        String newNamaUser = kontenUserField.getValue(); 
        String newNamaMedia = kontenMediaField.getValue(); 
        String newNamaPlatform = kontenPlatformField.getValue(); 
        String newLinkDesain = kontenLinkDesainField.getText();
        String newTema = kontenTemaField.getText();
        String newDeadline = kontenDeadlineField.getValue().toString(); 
        String newTglPost = kontenTglPostField.getValue().toString(); 
        String newStatus = kontenStatusField.getValue(); 

        if (!newNamaUser.isEmpty() && !newNamaMedia.isEmpty() && !newNamaPlatform.isEmpty() && !newLinkDesain.isEmpty() && !newTema.isEmpty() && !newDeadline.isEmpty() && !newTglPost.isEmpty() && !newStatus.isEmpty()) {
            // Set fields that can be changed by the user
            konten.setNamaUser(newNamaUser);
            konten.setNamaMedia(newNamaMedia);
            konten.setNamaPlatform(newNamaPlatform);
            konten.setLinkDesain(newLinkDesain);
            konten.setTema(newTema);
            konten.setDeadline(newDeadline);
            konten.setTglPost(newTglPost);
            konten.setStatus(newStatus);
            
            // Update the konten
            updateKonten(konten);
            
            // Close the window
            closeWindow();
        } else {
            // Handle empty input
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle database error
    }
}


    @FXML
    private void cancelEdit(ActionEvent event) {
        closeWindow();
    }

    private void updateKonten(TableKonten konten) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        KontenDAO kontenDAO = new KontenDAO(connection);
        kontenDAO.updateKonten(konten.getId(), konten.getNamaUser(), konten.getNamaMedia(), konten.getNamaPlatform(), konten.getLinkDesain(), konten.getTema(), konten.getDeadline(), konten.getTglPost(), konten.getStatus());
    }

    private void closeWindow() {
        Stage stage = (Stage) kontenUserField.getScene().getWindow();
        stage.close();
    }
}