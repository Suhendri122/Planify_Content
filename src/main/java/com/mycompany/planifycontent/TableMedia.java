package com.mycompany.planifycontent;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableMedia {
    private final SimpleIntegerProperty no;
    private final SimpleStringProperty media;
    private final StringProperty aksi;
        private int id; // Tambahkan properti id


    public TableMedia(int no, String media, String aksi) {
        this.no = new SimpleIntegerProperty(no);
        this.media = new SimpleStringProperty(media);
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

    public String getMedia() {
        return media.get();
    }

    public StringProperty mediaProperty() {
        return media;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getAksi() {
        return aksi.get();
    }

    public void setAksi(String aksi) {
        this.aksi.set(aksi);
    }

    public StringProperty aksiProperty() {
        return aksi;
    }
}