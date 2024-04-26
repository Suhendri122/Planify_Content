module com.mycompany.planitfycontent {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.planitfycontent to javafx.fxml;
    exports com.mycompany.planitfycontent;
}
