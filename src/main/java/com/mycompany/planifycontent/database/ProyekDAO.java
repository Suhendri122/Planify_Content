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
        String query = "SELECT proyek.id, proyek.nama_proyek, proyek.user_id, user.nama AS pic_proyek, proyek.client_id, client.nama, client.no_telp, proyek.harga, proyek.tgl_mulai, proyek.tgl_selesai " +
                       "FROM proyek " +
                       "INNER JOIN client ON proyek.client_id = client.id " +
                       "INNER JOIN user ON proyek.user_id = user.id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int clientId = resultSet.getInt("client_id");
                String namaProyek = resultSet.getString("nama_proyek");
                String picProyek = resultSet.getString("pic_proyek");
                String namaClient = resultSet.getString("nama");
                String noTelepon = resultSet.getString("no_telp");
                String harga = resultSet.getString("harga");
                String tglMulai = resultSet.getString("tgl_mulai");
                String tglSelesai = resultSet.getString("tgl_selesai");

                TableProyek proyek = new TableProyek(id, userId, clientId, namaProyek, picProyek, namaClient, noTelepon, harga, tglMulai, tglSelesai, null);
                proyekList.add(proyek);
            }
        }

        return proyekList;
    }

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

    public int getUserIdByName(String userName) throws SQLException {
        String query = "SELECT id FROM user WHERE nama = ? LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        throw new SQLException("User not found");
    }

    public int getClientIdByName(String clientName) throws SQLException {
        String query = "SELECT id FROM client WHERE nama = ? LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, clientName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        throw new SQLException("Client not found");
    }

    public void updateProyek(TableProyek proyek) throws SQLException {
        String query = "UPDATE proyek " +
                       "SET nama_proyek=?, user_id=?, client_id=?, harga=?, tgl_mulai=?, tgl_selesai=? " +
                       "WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, proyek.getNamaProyek());
            stmt.setInt(2, proyek.getUserId());
            stmt.setInt(3, proyek.getClientId());
            stmt.setString(4, proyek.getHarga());
            stmt.setDate(5, java.sql.Date.valueOf(proyek.getTglMulai()));
            stmt.setDate(6, java.sql.Date.valueOf(proyek.getTglSelesai()));
            stmt.setInt(7, proyek.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteProyek(int id) throws SQLException {
        String query = "DELETE FROM proyek WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        updateProyekNumbers(); // Update project numbers after deletion
    }

    public void updateProyekNumbers() throws SQLException {
        String query = "SET @row_number = 0; " +
                       "UPDATE proyek SET id = (@row_number:=@row_number + 1) ORDER BY id;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.executeUpdate();
        }
    }
}
