package com.mycompany.planitfycontent;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableDashboard {
    private final SimpleIntegerProperty no;
    private final SimpleStringProperty proyek;
    private final SimpleStringProperty picProyek;
    private final SimpleStringProperty tema;
    private final SimpleStringProperty media;
    private final SimpleStringProperty deadline;
    private final SimpleStringProperty tglPost;
    private final SimpleStringProperty picKonten;

    public TableDashboard(int no, String proyek, String picProyek, String tema, String media, String deadline, String tglPost, String picKonten) {
        this.no = new SimpleIntegerProperty(no);
        this.proyek = new SimpleStringProperty(proyek);
        this.picProyek = new SimpleStringProperty(picProyek);
        this.tema = new SimpleStringProperty(tema);
        this.media = new SimpleStringProperty(media);
        this.deadline = new SimpleStringProperty(deadline);
        this.tglPost = new SimpleStringProperty(tglPost);
        this.picKonten = new SimpleStringProperty(picKonten);
    }

    public int getNo() {
        return no.get();
    }

    public void setNo(int no) {
        this.no.set(no);
    }

    public SimpleIntegerProperty noProperty() {
        return no;
    }

    public String getProyek() {
        return proyek.get();
    }

    public SimpleStringProperty proyekProperty() {
        return proyek;
    }

    public String getPicProyek() {
        return picProyek.get();
    }

    public SimpleStringProperty picProyekProperty() {
        return picProyek;
    }

    public String getTema() {
        return tema.get();
    }

    public SimpleStringProperty temaProperty() {
        return tema;
    }

    public String getMedia() {
        return media.get();
    }

    public SimpleStringProperty mediaProperty() {
        return media;
    }

    public String getDeadline() {
        return deadline.get();
    }

    public SimpleStringProperty deadlineProperty() {
        return deadline;
    }

    public String getTglPost() {
        return tglPost.get();
    }

    public SimpleStringProperty tglPostProperty() {
        return tglPost;
    }

    public String getPicKonten() {
        return picKonten.get();
    }

    public SimpleStringProperty picKontenProperty() {
        return picKonten;
    }
}
