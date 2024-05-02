package com.mycompany.planitfycontent;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableDataMedia {
    private ObservableList<TableDataMedia> dataMedias = FXCollections.observableArrayList();

    private final IntegerProperty no = new SimpleIntegerProperty();
    private final StringProperty media = new SimpleStringProperty();

    public TableDataMedia() {
        // Constructor tanpa parameter
    }

    public TableDataMedia(int no, String media) {
        this.no.set(no);
        this.media.set(media);
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

    public String getMedia() {
        return media.get();
    }

    public StringProperty mediaProperty() {
        return media;
    }

    public void setMedia(String media) {
        this.media.set(media);
    }

    public ObservableList<TableDataMedia> getDataMedias() {
        return dataMedias;
    }

    public void addDataMedia(TableDataMedia dataMedia) {
        dataMedias.add(dataMedia);
    }
}
