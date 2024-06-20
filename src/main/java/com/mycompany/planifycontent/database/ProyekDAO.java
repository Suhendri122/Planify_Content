package com.mycompany.planifycontent.database;

import com.mycompany.planifycontent.TableProyek;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.DatePicker;


public class ProyekDAO {
    private final Connection connection;

    public ProyekDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableProyek> getAllProyek(String pic, String client, String startDate, String endDate) throws SQLException {
        List<TableProyek> proyekList = new ArrayList<>();
        String query = "SELECT proyek.id, proyek.nama_proyek, proyek.user_id, user.nama AS pic_proyek, proyek.client_id, client.nama, client.no_telp, proyek.harga, proyek.tgl_mulai, proyek.tgl_selesai " +
                       "FROM proyek " +
                       "INNER JOIN client ON proyek.client_id = client.id " +
                       "INNER JOIN user ON proyek.user_id = user.id";


        // Bikin sistem filtering berdasarkan isi data parameter
        List<String> filter = new ArrayList<String>();

        // Jika PIC tidak 0 atau "Semua" maka cari berdasarkan ID PIC
        if(!pic.equals("0")){
            filter.add("user.id = '" + pic + "'");
        }
        
        // Jika PIC tidak 0 atau "Semua" maka cari berdasarkan ID Client
        if(!client.equals("0")){
            filter.add("client.id = '" + client + "'");
        }

        // Jika startDate bukan string kosong maka cari tanggal setelah tanggal mulai
        if(!startDate.equals("")){
            filter.add("tgl_mulai >= '" + startDate.toString() + "'");
        }

        // Jika endDate bukan string kosong maka cari tanggal sebelum tanggal selesai
        if(!endDate.equals("")){
            filter.add("tgl_selesai <= '" + endDate.toString() + "'");
        }

        // Jika filter tidak kosong, tambahkan keyword WHERE di depan dan kumpulkan semua filter yang digunakan jadi satu dengan "AND" sebagai pemisah
        if(filter.size() != 0){
            query += " WHERE " + String.join(" AND ", filter);
        }

        // Sebagai pengecek query, comment kalau ga dipakai
        // System.out.println(query);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int no =1;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int clientId = resultSet.getInt("client_id");
                String namaProyek = resultSet.getString("nama_proyek");
                String picProyek = resultSet.getString("pic_proyek");
                String namaClient = resultSet.getString("nama");
                String noTelepon = resultSet.getString("no_telp");
                double harga = Double.parseDouble(resultSet.getString("harga"));
                String tglMulai = resultSet.getString("tgl_mulai");
                String tglSelesai = resultSet.getString("tgl_selesai");

                TableProyek proyek = new TableProyek(id, userId, clientId, namaProyek, picProyek, namaClient, noTelepon, harga, tglMulai, tglSelesai, null);
                proyekList.add(proyek);
            }
        }

        return proyekList;
    }
    
    public List<TableProyek> getProyekByPIC(String picProyek) throws SQLException {
    List<TableProyek> proyekList = new ArrayList<>();
    String query = "SELECT p.id, p.user_id, p.client_id, p.nama_proyek, u.nama AS pic_proyek, c.nama AS nama_client, c.no_telp, p.harga, p.tgl_mulai, p.tgl_selesai " +
                   "FROM proyek p " +
                   "INNER JOIN user u ON p.user_id = u.id " +
                   "INNER JOIN client c ON p.client_id = c.id " +
                   "WHERE u.nama = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, picProyek);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int clientId = resultSet.getInt("client_id");
                String namaProyek = resultSet.getString("nama_proyek");
                String picProyekName = resultSet.getString("pic_proyek");
                String namaClient = resultSet.getString("nama_client");
                String noTelepon = resultSet.getString("no_telp");
                double harga = Double.parseDouble(resultSet.getString("harga"));
                String tglMulai = resultSet.getString("tgl_mulai");
                String tglSelesai = resultSet.getString("tgl_selesai");

                TableProyek proyek = new TableProyek(id, userId, clientId, namaProyek, picProyekName, namaClient, noTelepon, harga, tglMulai, tglSelesai, null);
                proyekList.add(proyek);
            }
        }
    }

    return proyekList;
}

public List<TableProyek> getProyekByClient(String clientName) throws SQLException {
    List<TableProyek> proyekList = new ArrayList<>();
    String query = "SELECT p.id, p.user_id, p.client_id, p.nama_proyek, u.nama AS pic_proyek, c.nama AS nama_client, c.no_telp, p.harga, p.tgl_mulai, p.tgl_selesai " +
                   "FROM proyek p " +
                   "INNER JOIN user u ON p.user_id = u.id " +
                   "INNER JOIN client c ON p.client_id = c.id " +
                   "WHERE c.nama = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, clientName);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int clientId = resultSet.getInt("client_id");
                String namaProyek = resultSet.getString("nama_proyek");
                String picProyekName = resultSet.getString("pic_proyek");
                String namaClient = resultSet.getString("nama_client");
                String noTelepon = resultSet.getString("no_telp");
                double harga = Double.parseDouble(resultSet.getString("harga"));
                String tglMulai = resultSet.getString("tgl_mulai");
                String tglSelesai = resultSet.getString("tgl_selesai");

                TableProyek proyek = new TableProyek(id, userId, clientId, namaProyek, picProyekName, namaClient, noTelepon, harga, tglMulai, tglSelesai, null);
                proyekList.add(proyek);
            }
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

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String namaClient = resultSet.getString("nama");
                clientList.add(namaClient);
            }
        }

        return clientList;
    }

    public List<TableProyek> getProyekByTglMulai(LocalDate tglMulai) throws SQLException {
        List<TableProyek> proyekList = new ArrayList<>();
        String query = "SELECT * FROM proyek WHERE tgl_mulai = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(tglMulai));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int userId = resultSet.getInt("user_id");
                    int clientId = resultSet.getInt("client_id");
                    String namaProyek = resultSet.getString("nama_proyek");
                    String picProyekName = resultSet.getString("user_id");
                    String namaClient = resultSet.getString("client_id");
                    String noTelepon = resultSet.getString("user_id");
                    double harga = Double.parseDouble(resultSet.getString("harga"));
                    String tglSelesai = resultSet.getString("tgl_selesai");

                    TableProyek proyek = new TableProyek(id, userId, clientId, namaProyek, picProyekName, namaClient, noTelepon, harga, tglMulai.toString(), tglSelesai, null);
                    proyekList.add(proyek);
                }
            }
        }

        return proyekList;
    }

    public String getPhoneNumberByClientName(String clientName) throws SQLException {
        String phoneNumber = null;
        String query = "SELECT no_telp FROM client WHERE nama = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, clientName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    phoneNumber = resultSet.getString("no_telp");
                }
            }
        }

        return phoneNumber;
    }


    public int getUserIdByName(String userName) throws SQLException {
        String query = "SELECT id FROM user WHERE nama = ? LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }
        throw new SQLException("User not found");
    }

    public int getClientIdByName(String clientName) throws SQLException {
        String query = "SELECT id FROM client WHERE nama = ? LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, clientName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
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
            stmt.setDouble(4, proyek.getHarga());
            stmt.setDate(5, java.sql.Date.valueOf(proyek.getTglMulai()));
            stmt.setDate(6, java.sql.Date.valueOf(proyek.getTglSelesai()));
            stmt.setInt(7, proyek.getNo());
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
    String resetQuery = "SET @row_number = 0";
    String updateQuery = "UPDATE proyek SET id = (@row_number:=@row_number + 1) ORDER BY id";
    
    try (PreparedStatement resetStmt = connection.prepareStatement(resetQuery);
         PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
        
        resetStmt.execute(); // Execute the reset query
        
        updateStmt.executeUpdate(); // Execute the update query
    }
}

    
    public void addProyek(TableProyek proyek) throws SQLException {
    String query = "INSERT INTO proyek (nama_proyek, user_id, client_id, harga, tgl_mulai, tgl_selesai) VALUES (?, ?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, proyek.getNamaProyek());
        stmt.setInt(2, proyek.getUserId());
        stmt.setInt(3, proyek.getClientId());
        stmt.setDouble(4, proyek.getHarga());
        stmt.setDate(5, java.sql.Date.valueOf(proyek.getTglMulai()));
        stmt.setDate(6, java.sql.Date.valueOf(proyek.getTglSelesai()));
        stmt.executeUpdate();
    }
    updateProyekNumbers();
}

}
