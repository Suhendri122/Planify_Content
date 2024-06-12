package com.mycompany.planifycontent;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableClient {
    private final SimpleIntegerProperty no;
    private final SimpleStringProperty nama;
    private final SimpleStringProperty no_telp;
    private final SimpleStringProperty usaha;
    private final StringProperty aksi;
    private int id;

    public TableClient(int no, String nama, String no_telp, String usaha, String aksi) {
        this.no = new SimpleIntegerProperty(no);
        this.nama = new SimpleStringProperty(nama);
        this.no_telp = new SimpleStringProperty(no_telp);
        this.usaha = new SimpleStringProperty(usaha);
        this.aksi = new SimpleStringProperty(aksi);
    }

    public int getNo() {
        return no.get();
    }

    public IntegerProperty noProperty() {
        return no;
    }

    public void setNo(int no) {
        this.no.set(no);
    }

    public String getNama() {
        return nama.get();
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public String getNo_telp() {
        return no_telp.get();
    }

    public void setNo_telp(String no_telp) {
        this.no_telp.set(no_telp);
    }

    public StringProperty noTelpProperty() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp.set(noTelp);
    }

    public String getUsaha() {
        return usaha.get();
    }

    public void setUsaha(String usaha) {
        this.usaha.set(usaha);
    }

    public StringProperty usahaProperty() {
        return usaha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
