package com.mycompany.planifycontent;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableProyek {
    private final IntegerProperty id;
    private final StringProperty namaProyek;
    private final StringProperty picProyek;
    private final StringProperty namaClient;
    private final StringProperty noTelepon;
    private final StringProperty harga;
    private final StringProperty tglMulai;
    private final StringProperty tglSelesai;
    private final StringProperty aksi;

    public TableProyek(int id, String namaProyek, String picProyek, String namaClient, String noTelepon, String harga, String tglMulai, String tglSelesai, String aksi) {
        this.id = new SimpleIntegerProperty(id);
        this.namaProyek = new SimpleStringProperty(namaProyek);
        this.picProyek = new SimpleStringProperty(picProyek);
        this.namaClient = new SimpleStringProperty(namaClient);
        this.noTelepon = new SimpleStringProperty(noTelepon);
        this.harga = new SimpleStringProperty(harga);
        this.tglMulai = new SimpleStringProperty(tglMulai);
        this.tglSelesai = new SimpleStringProperty(tglSelesai);
        this.aksi = new SimpleStringProperty(aksi);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNamaProyek() {
        return namaProyek.get();
    }

    public void setNamaProyek(String namaProyek) {
        this.namaProyek.set(namaProyek);
    }

    public StringProperty namaProyekProperty() {
        return namaProyek;
    }

    public String getPicProyek() {
        return picProyek.get();
    }

    public void setPicProyek(String picProyek) {
        this.picProyek.set(picProyek);
    }

    public StringProperty picProyekProperty() {
        return picProyek;
    }

    public String getNamaClient() {
        return namaClient.get();
    }

    public void setNamaClient(String namaClient) {
        this.namaClient.set(namaClient);
    }

    public StringProperty namaClientProperty() {
        return namaClient;
    }

    public String getNoTelepon() {
        return noTelepon.get();
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon.set(noTelepon);
    }

    public StringProperty noTeleponProperty() {
        return noTelepon;
    }

    public String getHarga() {
        return harga.get();
    }

    public void setHarga(String harga) {
        this.harga.set(harga);
    }

    public StringProperty hargaProperty() {
        return harga;
    }

    public String getTglMulai() {
        return tglMulai.get();
    }

    public void setTglMulai(String tglMulai) {
        this.tglMulai.set(tglMulai);
    }

    public StringProperty tglMulaiProperty() {
        return tglMulai;
    }

    public String getTglSelesai() {
        return tglSelesai.get();
    }

    public void setTglSelesai(String tglSelesai) {
        this.tglSelesai.set(tglSelesai);
    }

    public StringProperty tglSelesaiProperty() {
        return tglSelesai;
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
