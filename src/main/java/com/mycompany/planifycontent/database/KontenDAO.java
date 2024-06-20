package com.mycompany.planifycontent.database;

import com.mycompany.planifycontent.TableKonten;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KontenDAO {
    public final Connection connection;

    public KontenDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableKonten> getAllKontens(String picKonten, String status, LocalDate tglPost, LocalDate deadline) throws SQLException {
    List<TableKonten> kontenList = new ArrayList<>();
    String query = "SELECT konten.id, user.nama AS nama_user, media.nama_media, platform.nama_platform, konten.link_desain, konten.tema, konten.deadline, konten.tgl_post, konten.status " +
                   "FROM konten " +
                   "INNER JOIN user ON konten.user_id = user.id " +
                   "INNER JOIN media ON konten.media_id = media.id " +
                   "INNER JOIN platform ON konten.platform_id = platform.id " +
                   "WHERE 1=1";

    if (picKonten!= null &&!picKonten.isEmpty()) {
        query += " AND user.nama =?";
    }
    if (status!= null &&!status.isEmpty()) {
        query += " AND konten.status =?";
    }
    if (tglPost!= null) {
        query += " AND konten.tgl_post =?";
    }
    if (deadline!= null) {
        query += " AND konten.deadline =?";
    }

    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        int parameterIndex = 1;
        if (picKonten!= null &&!picKonten.isEmpty()) {
            preparedStatement.setString(parameterIndex++, picKonten);
        }
        if (status!= null &&!status.isEmpty()) {
            preparedStatement.setString(parameterIndex++, status);
        }
        if (tglPost!= null) {
            preparedStatement.setDate(parameterIndex++, Date.valueOf(tglPost));
        }
        if (deadline!= null) {
            preparedStatement.setDate(parameterIndex++, Date.valueOf(deadline));
        }

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String namaUser = resultSet.getString("nama_user");
                String namaMedia = resultSet.getString("nama_media");
                String namaPlatform = resultSet.getString("nama_platform");
                String linkDesain = resultSet.getString("link_desain");
                String tema = resultSet.getString("tema");
                String deadlineStr = resultSet.getString("deadline");
                String tglPostStr = resultSet.getString("tgl_post");
                String statusStr = resultSet.getString("status");
                String aksi = ""; // Default value for aksi

                TableKonten konten = new TableKonten(id, namaUser, namaMedia, namaPlatform, linkDesain, tema, deadlineStr, tglPostStr, statusStr, aksi);
                kontenList.add(konten);
            }
        }
    }
    return kontenList;
}
    
    
    public List<String> getAllUsers() throws SQLException {
        String query = "SELECT nama FROM user";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(resultSet.getString("nama"));
            }
            return users;
        }
    }
    
    
    public List<String> getAllMedia() throws SQLException {
        String query = "SELECT nama_media FROM media";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> media = new ArrayList<>();
            while (resultSet.next()) {
                media.add(resultSet.getString("nama_media"));
            }
            return media;
        }
    }
    
    public List<String> getAllPlatforms() throws SQLException {
        String query = "SELECT nama_platform FROM platform";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> platforms = new ArrayList<>();
            while (resultSet.next()) {
                platforms.add(resultSet.getString("nama_platform"));
            }
            return platforms;
        }
    }

    public List<String> getAllStatuses() throws SQLException {
        String query = "SELECT status FROM konten_status"; // Assuming you have a table called konten_status
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> statuses = new ArrayList<>();
            while (resultSet.next()) {
                statuses.add(resultSet.getString("status"));
            }
            return statuses;
        }
    }

public void deleteKonten(int id, String picKonten, String status, LocalDate tglPost, LocalDate deadline) throws SQLException {
    String query = "DELETE FROM konten WHERE id=?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
    updateKontenNumbers(picKonten, status, tglPost, deadline);
}

public void updateKontenNumbers(String picKonten, String status, LocalDate tglPost, LocalDate deadline) throws SQLException {
    List<TableKonten> kontenList = getAllKontens(picKonten, status, tglPost, deadline);
    String sql = "UPDATE konten SET id = ? WHERE id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        for (int i = 0; i < kontenList.size(); i++) {
            ps.setInt(1, i + 1); // Set nomor ID baru
            ps.setInt(2, kontenList.get(i).getId()); // Cari ID lama
            ps.executeUpdate();
        }
    }
}






    public void updateKonten(int id, String namaUser, String namaMedia, String namaPlatform, String linkDesain, String tema, String deadline, String tglPost, String status) throws SQLException {
        String query = "UPDATE konten SET user_id = (SELECT id FROM user WHERE nama = ?), media_id = (SELECT id FROM media WHERE nama_media = ?), platform_id = (SELECT id FROM platform WHERE nama_platform = ?), link_desain = ?, tema = ?, deadline = ?, tgl_post = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, namaUser);
            stmt.setString(2, namaMedia);
            stmt.setString(3, namaPlatform);
            stmt.setString(4, linkDesain);
            stmt.setString(5, tema);
            stmt.setString(6, deadline);
            stmt.setString(7, tglPost);
            stmt.setString(8, status);
            stmt.setInt(9, id);
            stmt.executeUpdate();
        }
    }


    public void insertKonten(String namaUser, String namaMedia, String namaPlatform, String linkDesain, String tema, String deadline, String tglPost, String status) throws SQLException {
        String query = "INSERT INTO konten (user_id, media_id, platform_id, link_desain, tema, deadline, tgl_post, status) VALUES ((SELECT id FROM user WHERE nama = ?), (SELECT id FROM media WHERE nama_media = ?), (SELECT id FROM platform WHERE nama_platform = ?), ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, namaUser);
            stmt.setString(2, namaMedia);
            stmt.setString(3, namaPlatform);
            stmt.setString(4, linkDesain);
            stmt.setString(5, tema);
            stmt.setString(6, deadline);
            stmt.setString(7, tglPost);
            stmt.setString(8, status);
            stmt.executeUpdate();
        }
    }
    
    
    
    public List<String> getAllUserNames() throws SQLException {
        List<String> userNameList = new ArrayList<>();
        String query = "SELECT nama FROM user";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString("nama");
                userNameList.add(userName);
            }
        }
        return userNameList;
    }
}

