package com.mycompany.planifycontent;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public void setNo(int no) {
        this.no.set(no);
    }

    public IntegerProperty noProperty() {
        return no;
    }

    public String getNama() {
        return nama.get();
    }

    public StringProperty namaProperty() {
        return nama;
    }
    
    public String getNo_telp() {
        return no_telp.get();
    }

    public StringProperty no_telpProperty() {
        return no_telp;
    }
    
    public String getUsaha() {
        return usaha.get();
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
}