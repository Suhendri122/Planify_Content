package com.mycompany.planitfycontent;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class KontenTerdekat {
    private final SimpleIntegerProperty no;
    private final SimpleStringProperty proyek;
    private final SimpleStringProperty picProyek;
    private final SimpleStringProperty tema;
    private final SimpleStringProperty media;
    private final SimpleStringProperty deadline;
    private final SimpleStringProperty tglPost;
    private final SimpleStringProperty picKonten;

    public KontenTerdekat(int no, String proyek, String picProyek, String tema, String media, String deadline, String tglPost, String picKonten) {
        this.no = new SimpleIntegerProperty(no);
        this.proyek = new SimpleStringProperty(proyek);
        this.picProyek = new SimpleStringProperty(picProyek);
        this.tema = new SimpleStringProperty(tema);
        this.media = new SimpleStringProperty(media);
        this.deadline = new SimpleStringProperty(deadline);
        this.tglPost = new SimpleStringProperty(tglPost);
        this.picKonten = new SimpleStringProperty(picKonten);
    }

    public SimpleIntegerProperty noProperty() {
        return no;
    }

    public SimpleStringProperty proyekProperty() {
        return proyek;
    }

    public SimpleStringProperty picProyekProperty() {
        return picProyek;
    }

    public SimpleStringProperty temaProperty() {
        return tema;
    }

    public SimpleStringProperty mediaProperty() {
        return media;
    }

    public SimpleStringProperty deadlineProperty() {
        return deadline;
    }

    public SimpleStringProperty tglPostProperty() {
        return tglPost;
    }

    public SimpleStringProperty picKontenProperty() {
        return picKonten;
    }
}
