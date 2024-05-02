package com.mycompany.planitfycontent;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableDataClient {
    private ObservableList<TableDataClient> dataClient = FXCollections.observableArrayList();

    private final IntegerProperty no = new SimpleIntegerProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final StringProperty usaha = new SimpleStringProperty();

    public TableDataClient() {
        // Constructor tanpa parameter
    }

    public TableDataClient(int no, String nama, String noTelp, String usaha) {
        this.no.set(no);
        this.nama.set(nama);
        this.noTelp.set(noTelp);
        this.usaha.set(usaha);
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

    public StringProperty namaProperty() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    public String getNoTelp() {
        return noTelp.get();
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

    public StringProperty usahaProperty() {
        return usaha;
    }

    public void setUsaha(String usaha) {
        this.usaha.set(usaha);
    }

    public ObservableList<TableDataClient> getDataClient() {
        return dataClient;
    }

    public void addDataClient(TableDataClient dataClient) {
        this.dataClient.add(dataClient);
    }
}
