package com.mycompany.planifycontent.database;

import com.mycompany.planifycontent.TableProyek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProyekDAO {
    private final Connection connection;

    public ProyekDAO(Connection connection) {
        this.connection = connection;
    }
    
    public List<TableProyek> getAllProyek() throws SQLException {
        List<TableProyek> proyekList = new ArrayList<>();
        String query = "SELECT proyek.id, proyek.nama_proyek, user.nama AS pic_proyek, client.nama, client.no_telp, proyek.harga, proyek.tgl_mulai, proyek.tgl_selesai " +
                       "FROM proyek " +
                       "INNER JOIN client ON proyek.client_id = client.id " +
                       "INNER JOIN user ON proyek.user_id = user.id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String namaProyek = resultSet.getString("nama_proyek");
                String picProyek = resultSet.getString("pic_proyek");
                String namaClient = resultSet.getString("nama");
                String noTelepon = resultSet.getString("no_telp");
                String harga = resultSet.getString("harga");
                String tglMulai = resultSet.getString("tgl_mulai");
                String tglSelesai = resultSet.getString("tgl_selesai");

                // Buat objek TableProyek dan tambahkan ke daftar
                TableProyek proyek = new TableProyek(id, namaProyek, picProyek, namaClient, noTelepon, harga, tglMulai, tglSelesai, null);
                proyekList.add(proyek); // Tambahkan proyek ke dalam list
            }
        }

        return proyekList;
    }
}
