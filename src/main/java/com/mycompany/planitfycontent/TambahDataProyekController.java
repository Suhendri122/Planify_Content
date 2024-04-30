package com.mycompany.planitfycontent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TambahDataProyekController {

    private ProyekController parentController;

    // Method to set the parent controller
    public void setParentController(ProyekController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void popupBtnBatal(ActionEvent event) {
        // Close the popup
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
        
        // Call the method in the parent controller
        parentController.popupBtnBatal();
    }
}
