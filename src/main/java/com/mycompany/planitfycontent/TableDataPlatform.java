package com.mycompany.planitfycontent;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableDataPlatform {
    private final SimpleIntegerProperty no;
    private final SimpleStringProperty platform;


    public TableDataPlatform(int no, String platform) {
        this.no = new SimpleIntegerProperty(no);
        this.platform = new SimpleStringProperty(platform);
    }

    public int getNo() {
        return no.get();
    }

    public void setNo(int no) {
        this.no.set(no);
    }

    public IntegerProperty noProperty() {
        return no;
    }

    public String getPlatform() {
        return platform.get();
    }

    public StringProperty platformProperty() {
        return platform;
    }
}
