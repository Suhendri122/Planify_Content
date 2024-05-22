module com.mycompany.planifycontent {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.planifycontent to javafx.fxml;
    exports com.mycompany.planifycontent;
}
