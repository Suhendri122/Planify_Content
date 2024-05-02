package com.mycompany.planitfycontent;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableKonten {
    private final SimpleIntegerProperty no;
    private final SimpleStringProperty tema;
    private final SimpleStringProperty media;
    private final SimpleStringProperty platform;
    private final SimpleStringProperty link;
    private final SimpleStringProperty deadline;
    private final SimpleStringProperty tglPost;
    private final SimpleStringProperty picKonten;
    private final SimpleStringProperty status;
    private final SimpleStringProperty aksi;

    public TableKonten(int no, String tema, String media, String platform, String link, String deadline, String tglPost, String picKonten, String status, String aksi) {
        this.no = new SimpleIntegerProperty(no);
        this.tema = new SimpleStringProperty(tema);
        this.media = new SimpleStringProperty(media);
        this.platform = new SimpleStringProperty(platform);
        this.link = new SimpleStringProperty(link);
        this.deadline = new SimpleStringProperty(deadline);
        this.tglPost = new SimpleStringProperty(tglPost);
        this.picKonten = new SimpleStringProperty(picKonten);
        this.status = new SimpleStringProperty(status);
        this.aksi = new SimpleStringProperty(aksi);
    }

    // Getters
    public int getNo() {
        return no.get();
    }

    public String getTema() {
        return tema.get();
    }

    public String getMedia() {
        return media.get();
    }

    public String getPlatform() {
        return platform.get();
    }

    public String getLink() {
        return link.get();
    }

    public String getDeadline() {
        return deadline.get();
    }

    public String getTglPost() {
        return tglPost.get();
    }

    public String getPicKonten() {
        return picKonten.get();
    }

    public String getStatus() {
        return status.get();
    }

    public String getAksi() {
        return aksi.get();
    }

    // Property getters
    public SimpleIntegerProperty noProperty() {
        return no;
    }

    public SimpleStringProperty temaProperty() {
        return tema;
    }

    public SimpleStringProperty mediaProperty() {
        return media;
    }

    public SimpleStringProperty platformProperty() {
        return platform;
    }

    public SimpleStringProperty linkProperty() {
        return link;
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

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public SimpleStringProperty aksiProperty() {
        return aksi;
    }
}
