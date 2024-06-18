package com.mycompany.planifycontent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableProyek {
    private final IntegerProperty no;
    private final IntegerProperty userId; // Add user ID property
    private final IntegerProperty clientId; // Add client ID property
    private final StringProperty namaProyek;
    private final StringProperty picProyek;
    private final StringProperty namaClient;
    private final StringProperty noTelepon;
    private final DoubleProperty harga;
    private final StringProperty tglMulai;
    private final StringProperty tglSelesai;
    private final StringProperty aksi;
    private int id;

    public TableProyek(int no, int userId, int clientId, String namaProyek, String picProyek, String namaClient, String noTelepon, Double harga, String tglMulai, String tglSelesai, String aksi) {
        this.no = new SimpleIntegerProperty(no);
        this.userId = new SimpleIntegerProperty(userId); // Initialize user ID
        this.clientId = new SimpleIntegerProperty(clientId); // Initialize client ID
        this.namaProyek = new SimpleStringProperty(namaProyek);
        this.picProyek = new SimpleStringProperty(picProyek);
        this.namaClient = new SimpleStringProperty(namaClient);
        this.noTelepon = new SimpleStringProperty(noTelepon);
        this.harga = new SimpleDoubleProperty(harga);
        this.tglMulai = new SimpleStringProperty(tglMulai);
        this.tglSelesai = new SimpleStringProperty(tglSelesai);
        this.aksi = new SimpleStringProperty(aksi);
    }

    // getters and setters...

    public int getNo() {
        return no.get();
    }

    public void setNo(int id) {
        this.no.set(id);
    }

    public IntegerProperty noProperty() {
        return no;
    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public int getClientId() {
        return clientId.get();
    }

    public void setClientId(int clientId) {
        this.clientId.set(clientId);
    }

    public IntegerProperty clientIdProperty() {
        return clientId;
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

    public double getHarga() {
        return harga.get();
    }

    public void setHarga(int harga) {
        this.harga.set(harga);
    }

    public DoubleProperty hargaProperty() {
        return harga;
    }
    
    public String getHargaFormatted() {
        return "Rp" + (int) harga.get();
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
    
    public String getTglMulaiFormatted() {
        LocalDate date = LocalDate.parse(this.tglMulai.getValue());
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String getTglSelesaiFormatted() {
        LocalDate date = LocalDate.parse(this.tglSelesai.getValue());
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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
}
