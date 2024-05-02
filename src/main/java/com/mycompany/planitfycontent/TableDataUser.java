package com.mycompany.planitfycontent;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableDataUser {
    private ObservableList<TableDataUser> dataUsers = FXCollections.observableArrayList();

    private final IntegerProperty no = new SimpleIntegerProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    public TableDataUser() {
        // Constructor tanpa parameter
    }

    public TableDataUser(int no, String nama, String email) {
        this.no.set(no);
        this.nama.set(nama);
        this.email.set(email);
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

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public ObservableList<TableDataUser> getDataUsers() {
        return dataUsers;
    }

    public void addDataUser(TableDataUser dataUser) {
        dataUsers.add(dataUser);
    }
}
