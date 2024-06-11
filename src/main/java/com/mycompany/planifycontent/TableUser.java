package com.mycompany.planifycontent;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableUser {
    private final SimpleIntegerProperty no;
    private final SimpleStringProperty user;
    private final SimpleStringProperty email;
    private final StringProperty aksi;
        private int id; // Tambahkan properti id


    public TableUser(int no, String user, String email, String aksi) {
        this.no = new SimpleIntegerProperty(no);
        this.user = new SimpleStringProperty(user);
        this.email = new SimpleStringProperty(email);
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

    public String getUser() {
        return user.get();
    }

    public StringProperty userProperty() {
        return user;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
