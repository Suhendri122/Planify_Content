package com.mycompany.planitfycontent;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableDataClient {
    private final SimpleIntegerProperty no;
    private final SimpleStringProperty nama;
    private final SimpleStringProperty no_telp;
    private final SimpleStringProperty usaha;


    public TableDataClient(int no, String nama, String no_telp, String usaha) {
        this.no = new SimpleIntegerProperty(no);
        this.nama = new SimpleStringProperty(nama);
        this.no_telp = new SimpleStringProperty(no_telp);
        this.usaha = new SimpleStringProperty(usaha);
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
}