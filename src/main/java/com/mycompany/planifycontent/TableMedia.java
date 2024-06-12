package com.mycompany.planifycontent;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableMedia {
    private final SimpleIntegerProperty no;
    private final SimpleStringProperty media;

    public TableMedia(int no, String media) {
        this.no = new SimpleIntegerProperty(no);
        this.media = new SimpleStringProperty(media);
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

    public void setMedia(String media) {
        this.media.set(media);
    }

    public StringProperty mediaProperty() {
        return media;
    }
}
