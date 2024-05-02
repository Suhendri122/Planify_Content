package com.mycompany.planitfycontent;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableProyek {
    private final SimpleIntegerProperty no;
    private final SimpleStringProperty namaProyek;
    private final SimpleStringProperty picProyek;
    private final SimpleStringProperty namaClient;
    private final SimpleStringProperty noTelepon;
    private final SimpleStringProperty harga;
    private final SimpleStringProperty tglMulai;
    private final SimpleStringProperty tglSelesai;
    private final SimpleStringProperty aksi;

    public TableProyek(int no, String namaProyek, String picProyek, String namaClient, String noTelepon, String harga, String tglMulai, String tglSelesai, String aksi) {
        this.no = new SimpleIntegerProperty(no);
        this.namaProyek = new SimpleStringProperty(namaProyek);
        this.picProyek = new SimpleStringProperty(picProyek);
        this.namaClient = new SimpleStringProperty(namaClient);
        this.noTelepon = new SimpleStringProperty(noTelepon);
        this.harga = new SimpleStringProperty(harga);
        this.tglMulai = new SimpleStringProperty(tglMulai);
        this.tglSelesai = new SimpleStringProperty(tglSelesai);
        this.aksi = new SimpleStringProperty(aksi);
    }


    // getters and setters
    
    
    public SimpleIntegerProperty noProperty() {
        return no;
    }

    public SimpleStringProperty namaProyekProperty() {
        return namaProyek;
    }

    public SimpleStringProperty picProyekProperty() {
        return picProyek;
    }

    public SimpleStringProperty namaClientProperty() {
        return namaClient;
    }

    public SimpleStringProperty noTeleponProperty() {
        return noTelepon;
    }

    public SimpleStringProperty hargaProperty() {
        return harga;
    }

    public SimpleStringProperty tglMulaiProperty() {
        return tglMulai;
    }

    public SimpleStringProperty tglSelesaiProperty() {
        return tglSelesai;
    }
    
    public SimpleStringProperty aksiProperty() {
        return aksi;
    }
}
