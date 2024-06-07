package com.mycompany.planifycontent;

import com.mycompany.planifycontent.database.DatabaseConnection;
import com.mycompany.planifycontent.database.KontenDAO;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
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
        if (konten != null) {
            fillForm();
        }
    }

    public void setKonten(TableKonten konten) {
        this.konten = konten;
        if (konten != null) {
            fillForm();
        }
    }

    private void fillForm() {
        kontenUserField.setValue(konten.getNamaUser()); // Gunakan setValue untuk ChoiceBox
        kontenMediaField.setValue(konten.getNamaMedia()); // Gunakan setValue untuk ChoiceBox
        kontenPlatformField.setValue(konten.getNamaPlatform()); // Gunakan setValue untuk ChoiceBox
        kontenLinkDesainField.setText(konten.getLinkDesain());
        kontenTemaField.setText(konten.getTema());
        kontenDeadlineField.setValue(LocalDate.parse(konten.getDeadline())); // Gunakan setValue untuk DatePicker
        kontenTglPostField.setValue(LocalDate.parse(konten.getTglPost())); // Gunakan setValue untuk DatePicker
        kontenStatusField.setValue(konten.getStatus()); // Gunakan setValue untuk ChoiceBox
    }

    @FXML
    private void saveChanges(ActionEvent event) {
        try {
            String newNamaUser = kontenUserField.getValue(); // Menggunakan getValue untuk ChoiceBox
            String newNamaMedia = kontenMediaField.getValue(); // Menggunakan getValue untuk ChoiceBox
            String newNamaPlatform = kontenPlatformField.getValue(); // Menggunakan getValue untuk ChoiceBox
            String newLinkDesain = kontenLinkDesainField.getText();
            String newTema = kontenTemaField.getText();
            String newDeadline = kontenDeadlineField.getValue().toString(); // Menggunakan getValue untuk DatePicker
            String newTglPost = kontenTglPostField.getValue().toString(); // Menggunakan getValue untuk DatePicker
            String newStatus = kontenStatusField.getValue(); // Menggunakan getValue untuk ChoiceBox

            if (!newNamaUser.isEmpty() && !newNamaMedia.isEmpty() && !newNamaPlatform.isEmpty() && !newLinkDesain.isEmpty() && !newTema.isEmpty() && !newDeadline.isEmpty() && !newTglPost.isEmpty() && !newStatus.isEmpty()) {
                konten.setNamaUser(newNamaUser);
                konten.setNamaMedia(newNamaMedia);
                konten.setNamaPlatform(newNamaPlatform);
                konten.setLinkDesain(newLinkDesain);
                konten.setTema(newTema);
                konten.setDeadline(newDeadline);
                konten.setTglPost(newTglPost);
                konten.setStatus(newStatus);
                
                updateKonten(konten);
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