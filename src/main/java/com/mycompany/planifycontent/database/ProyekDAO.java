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
    
        // Mendapatkan semua nama user dari database
    public List<String> getAllUsers() throws SQLException {
        List<String> users = new ArrayList<>();
        String query = "SELECT nama FROM user";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String namaUser = resultSet.getString("nama");
                users.add(namaUser);
            }
        }

        return users;
    }
    
    public List<String> getAllClients() throws SQLException {
        List<String> clientList = new ArrayList<>();
        String query = "SELECT nama FROM client";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String namaClient = resultSet.getString("nama");
                clientList.add(namaClient);
            }
        }

        return clientList;
    }
    
    public String getPhoneNumberByClientName(String clientName) throws SQLException {
        String phoneNumber = null;
        String query = "SELECT no_telp FROM client WHERE nama = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, clientName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                phoneNumber = resultSet.getString("no_telp");
            }
        }

        return phoneNumber;
    }
    
public void updateProyek(TableProyek proyek) throws SQLException {
    String query = "UPDATE proyek p " +
                   "INNER JOIN user u ON p.user_id = u.id " +
                   "INNER JOIN client c ON p.client_id = c.id " +
                   "SET p.nama_proyek=?, u.nama=?, c.nama=?, c.no_telp=?, p.harga=?, p.tgl_mulai=?, p.tgl_selesai=? " +
                   "WHERE p.id=?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, proyek.getNamaProyek());
        stmt.setString(2, proyek.getPicProyek()); // Assuming getPicProyek() gets the username
        stmt.setString(3, proyek.getNamaClient());
        stmt.setString(4, proyek.getNoTelepon());
        stmt.setString(5, proyek.getHarga());
        stmt.setDate(6, java.sql.Date.valueOf(proyek.getTglMulai()));
        stmt.setDate(7, java.sql.Date.valueOf(proyek.getTglSelesai()));
        stmt.setInt(8, proyek.getId());
        stmt.executeUpdate();
    }
}




    public void deleteProyek(int id) throws SQLException {
        String query = "DELETE FROM proyek WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
