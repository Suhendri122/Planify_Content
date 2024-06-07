package com.mycompany.planifycontent;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TablePlatform {
    private final SimpleIntegerProperty no;
    private final SimpleStringProperty platform;
    private final StringProperty aksi;
        private int id; // Tambahkan properti id


    public TablePlatform(int no, String platform, String aksi) {
        this.no = new SimpleIntegerProperty(no);
        this.platform = new SimpleStringProperty(platform);
        this.aksi = new SimpleStringProperty(aksi);
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
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
