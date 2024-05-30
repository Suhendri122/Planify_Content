package com.mycompany.planifycontent;

public class TableKonten {
    private int id;
    private String namaUser;
    private String namaMedia;
    private String namaPlatform;
    private String linkDesain;
    private String tema;
    private String deadline;
    private String tglPost;
    private String status;

    public TableKonten(int id, String namaUser, String namaMedia, String namaPlatform, String linkDesain, String tema, String deadline, String tglPost, String status) {
        this.id = id;
        this.namaUser = namaUser;
        this.namaMedia = namaMedia;
        this.namaPlatform = namaPlatform;
        this.linkDesain = linkDesain;
        this.tema = tema;
        this.deadline = deadline;
        this.tglPost = tglPost;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getNamaMedia() {
        return namaMedia;
    }

    public void setNamaMedia(String namaMedia) {
        this.namaMedia = namaMedia;
    }

    public String getNamaPlatform() {
        return namaPlatform;
    }

    public void setNamaPlatform(String namaPlatform) {
        this.namaPlatform = namaPlatform;
    }

    public String getLinkDesain() {
        return linkDesain;
    }

    public void setLinkDesain(String linkDesain) {
        this.linkDesain = linkDesain;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getTglPost() {
        return tglPost;
    }

    public void setTglPost(String tglPost) {
        this.tglPost = tglPost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
