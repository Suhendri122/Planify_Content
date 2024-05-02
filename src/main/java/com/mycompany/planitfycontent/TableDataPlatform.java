package com.mycompany.planitfycontent;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableDataPlatform {
    private ObservableList<TableDataPlatform> dataPlatforms = FXCollections.observableArrayList();

    private final IntegerProperty no = new SimpleIntegerProperty();
    private final StringProperty platform = new SimpleStringProperty();

    public TableDataPlatform() {
        // Constructor tanpa parameter
    }

    public TableDataPlatform(int no, String platform) {
        this.no.set(no);
        this.platform.set(platform);
    }

    public int getNo() {
        return no.get();
    }

    public IntegerProperty noProperty() {
        return no;
    }

    public void setNo(int no) {
        this.no.set(no);
    }

    public String getPlatform() {
        return platform.get();
    }

    public StringProperty platformProperty() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform.set(platform);
    }

    public ObservableList<TableDataPlatform> getDataPlatforms() {
        return dataPlatforms;
    }

    public void addDataPlatform(TableDataPlatform dataPlatform) {
        dataPlatforms.add(dataPlatform);
    }
}
