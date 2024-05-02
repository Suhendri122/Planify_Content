package com.mycompany.planitfycontent;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableHistori {
    private final SimpleIntegerProperty no;
    private final SimpleStringProperty proyek;
    private final SimpleStringProperty picProyek;
    private final SimpleStringProperty namaKonsumen;
    private final SimpleStringProperty tema;
    private final SimpleStringProperty media;
    private final SimpleStringProperty platform;
    private final SimpleStringProperty link;
    private final SimpleStringProperty deadline;
    private final SimpleStringProperty tglPost;
    private final SimpleStringProperty picKonten;
    private final SimpleStringProperty status;

    public TableHistori(int no, String proyek, String picProyek, String namaKonsumen, String tema,
                        String media, String platform, String link, String deadline, String tglPost,
                        String picKonten, String status) {
        this.no = new SimpleIntegerProperty(no);
        this.proyek = new SimpleStringProperty(proyek);
        this.picProyek = new SimpleStringProperty(picProyek);
        this.namaKonsumen = new SimpleStringProperty(namaKonsumen);
        this.tema = new SimpleStringProperty(tema);
        this.media = new SimpleStringProperty(media);
        this.platform = new SimpleStringProperty(platform);
        this.link = new SimpleStringProperty(link);
        this.deadline = new SimpleStringProperty(deadline);
        this.tglPost = new SimpleStringProperty(tglPost);
        this.picKonten = new SimpleStringProperty(picKonten);
        this.status = new SimpleStringProperty(status);
    }

    public int getNo() {
        return no.get();
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

    public String getNamaKonsumen() {
        return namaKonsumen.get();
    }

    public SimpleStringProperty namaKonsumenProperty() {
        return namaKonsumen;
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

    public String getPlatform() {
        return platform.get();
    }

    public SimpleStringProperty platformProperty() {
        return platform;
    }

    public String getLink() {
        return link.get();
    }

    public SimpleStringProperty linkProperty() {
        return link;
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

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }
}
