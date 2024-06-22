package com.mycompany.planifycontent;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableKonten {
    private final IntegerProperty no;
    private final StringProperty namaUser;
    private final StringProperty namaMedia;
    private final StringProperty namaPlatform;
    private final StringProperty linkDesain;
    private final StringProperty tema;
    private final StringProperty deadline;
    private final StringProperty tglPost;
    private final StringProperty status;
    private final StringProperty aksi;
    private int id;
    private int proyekId; // Menyimpan id proyek terkait

    public TableKonten(int no, String namaUser, String namaMedia, String namaPlatform, String linkDesain, String tema, String deadline, String tglPost, String status, String aksi) {
        this.no = new SimpleIntegerProperty(no);
        this.namaUser = new SimpleStringProperty(namaUser);
        this.namaMedia = new SimpleStringProperty(namaMedia);
        this.namaPlatform = new SimpleStringProperty(namaPlatform);
        this.linkDesain = new SimpleStringProperty(linkDesain);
        this.tema = new SimpleStringProperty(tema);
        this.deadline = new SimpleStringProperty(deadline);
        this.tglPost = new SimpleStringProperty(tglPost);
        this.status = new SimpleStringProperty(status);
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

    public String getNamaUser() {
        return namaUser.get();
    }

    public void setNamaUser(String namaUser) {
        this.namaUser.set(namaUser);
    }

    public StringProperty namaUserProperty() {
        return namaUser;
    }

    public String getNamaMedia() {
        return namaMedia.get();
    }

    public void setNamaMedia(String namaMedia) {
        this.namaMedia.set(namaMedia);
    }

    public StringProperty namaMediaProperty() {
        return namaMedia;
    }

    public String getNamaPlatform() {
        return namaPlatform.get();
    }

    public void setNamaPlatform(String namaPlatform) {
        this.namaPlatform.set(namaPlatform);
    }

    public StringProperty namaPlatformProperty() {
        return namaPlatform;
    }

    public String getLinkDesain() {
        return linkDesain.get();
    }

    public void setLinkDesain(String linkDesain) {
        this.linkDesain.set(linkDesain);
    }

    public StringProperty linkDesainProperty() {
        return linkDesain;
    }

    public String getTema() {
        return tema.get();
    }

    public void setTema(String tema) {
        this.tema.set(tema);
    }

    public StringProperty temaProperty() {
        return tema;
    }

    public String getDeadline() {
        return deadline.get();
    }

    public void setDeadline(String deadline) {
        this.deadline.set(deadline);
    }

    public StringProperty deadlineProperty() {
        return deadline;
    }

    public String getTglPost() {
        return tglPost.get();
    }

    public void setTglPost(String tglPost) {
        this.tglPost.set(tglPost);
    }

    public StringProperty tglPostProperty() {
        return tglPost;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProyekId() {
        return proyekId;
    }

    public void setProyekId(int proyekId) {
        this.proyekId = proyekId;
    }
}
