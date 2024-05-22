package com.mycompany.planifycontent;

import javafx.beans.property.SimpleStringProperty;


public class Proyek {
    private final SimpleStringProperty no;
    private final SimpleStringProperty namaProyek;
    private final SimpleStringProperty picProyek;
    private final SimpleStringProperty namaClient;
    private final SimpleStringProperty noTelepon;
    private final SimpleStringProperty harga;
    private final SimpleStringProperty tglMulai;
    private final SimpleStringProperty tglSelesai;

    public Proyek(String no, String namaProyek, String picProyek, String namaClient, String noTelepon, String harga, String tglMulai, String tglSelesai) {
        this.no = new SimpleStringProperty(no);
        this.namaProyek = new SimpleStringProperty(namaProyek);
        this.picProyek = new SimpleStringProperty(picProyek);
        this.namaClient = new SimpleStringProperty(namaClient);
        this.noTelepon = new SimpleStringProperty(noTelepon);
        this.harga = new SimpleStringProperty(harga);
        this.tglMulai = new SimpleStringProperty(tglMulai);
        this.tglSelesai = new SimpleStringProperty(tglSelesai);
    }

    public String getNo() {
        return no.get();
    }

    public String getNamaProyek() {
        return namaProyek.get();
    }

    public String getPicProyek() {
        return picProyek.get();
    }

    public String getNamaClient() {
        return namaClient.get();
    }

    public String getNoTelepon() {
        return noTelepon.get();
    }

    public String getHarga() {
        return harga.get();
    }

    public String getTglMulai() {
        return tglMulai.get();
    }

    public String getTglSelesai() {
        return tglSelesai.get();
    }
}