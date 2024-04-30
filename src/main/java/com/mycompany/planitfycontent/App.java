package com.mycompany.planitfycontent;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Stage window;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        
        scene = new Scene(loadFXML("login"), 1000, 570);
        scene.getStylesheets().add(App.class.getResource("/style.css").toExternalForm());
        stage.setTitle("Planify Content");
        
         Image icon = new Image("/assets/logo.png");
         stage.getIcons().add(icon);
    
        window.setScene(scene);
        window.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void main(String[] args) {
        launch();
    }
}