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
    private final Connection connection;

    public KontenDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableKonten> getAllKontens() throws SQLException {
        List<TableKonten> kontenList = new ArrayList<>();
        String query = "SELECT konten.id, user.nama AS nama_user, media.nama_media, platform.nama_platform, konten.link_desain, konten.tema, konten.deadline, konten.tgl_post, konten.status " +
                       "FROM konten " +
                       "INNER JOIN user ON konten.user_id = user.id " +
                       "INNER JOIN media ON konten.media_id = media.id " +
                       "INNER JOIN platform ON konten.platform_id = platform.id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            // Variable untuk menyimpan nomor urutan
            int no = 1;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String namaUser = resultSet.getString("nama_user");
                String namaMedia = resultSet.getString("nama_media");
                String namaPlatform = resultSet.getString("nama_platform");
                String linkDesain = resultSet.getString("link_desain");
                String tema = resultSet.getString("tema");
                String deadline = resultSet.getString("deadline");
                String tglPost = resultSet.getString("tgl_post");
                String status = resultSet.getString("status");
                String aksi = "";

                TableKonten konten = new TableKonten(no++, namaUser, namaMedia, namaPlatform, linkDesain, tema, deadline, tglPost, status, aksi);
                konten.setId(id);
                kontenList.add(konten);
            }
        }
        return kontenList;
    }

    public List<TableKonten> getAllKontens(String picKonten, String status, LocalDate tglPost, LocalDate deadline) throws SQLException {
        List<TableKonten> kontenList = new ArrayList<>();
        String query = "SELECT konten.id, user.nama AS nama_user, media.nama_media, platform.nama_platform, konten.link_desain, konten.tema, konten.deadline, konten.tgl_post, konten.status " +
                       "FROM konten " +
                       "INNER JOIN user ON konten.user_id = user.id " +
                       "INNER JOIN media ON konten.media_id = media.id " +
                       "INNER JOIN platform ON konten.platform_id = platform.id " +
                       "WHERE 1=1";

        if (picKonten != null && !picKonten.isEmpty()) {
            query += " AND user.nama =?";
        }
        if (status != null && !status.isEmpty()) {
            query += " AND konten.status =?";
        }
        if (tglPost != null) {
            query += " AND konten.tgl_post =?";
        }
        if (deadline != null) {
            query += " AND konten.deadline =?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int parameterIndex = 1;
            if (picKonten != null && !picKonten.isEmpty()) {
                preparedStatement.setString(parameterIndex++, picKonten);
            }
            if (status != null && !status.isEmpty()) {
                preparedStatement.setString(parameterIndex++, status);
            }
            if (tglPost != null) {
                preparedStatement.setDate(parameterIndex++, Date.valueOf(tglPost));
            }
            if (deadline != null) {
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

    public String getMediaNameById(int mediaId) throws SQLException {
        String query = "SELECT nama_media FROM media WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, mediaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nama_media");
                }
            }
        }
        return null;
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

    public List<String> getAllMedia() throws SQLException {
        List<String> mediaList = new ArrayList<>();
        String query = "SELECT nama_media FROM media";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String namaMedia = resultSet.getString("nama_media");
                mediaList.add(namaMedia);
            }
        }

        return mediaList;
    }

    public List<String> getAllPlatforms() throws SQLException {
        List<String> platforms = new ArrayList<>();
        String query = "SELECT nama_platform FROM platform";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String namaPlatform = resultSet.getString("nama_platform");
                platforms.add(namaPlatform);
            }
        }

        return platforms;
    }

    public List<String> getAllStatuses() throws SQLException {
        List<String> statuses = new ArrayList<>();
        String query = "SELECT DISTINCT status FROM konten";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String status = resultSet.getString("status");
                statuses.add(status);
            }
        }

        return statuses;
    }

    public void deleteKonten(int id) throws SQLException {
        String query = "DELETE FROM konten WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        updateKontenNumbers();
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
        updateKontenNumbers();
    }

    public List<String> getAllProjects() throws SQLException {
        List<String> projects = new ArrayList<>();
        String query = "SELECT nama_proyek FROM proyek";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String namaProyek = resultSet.getString("nama_proyek");
                projects.add(namaProyek);
            }
        }

        return projects;
    }

    public int getProyekIdByName(String namaProyek) throws SQLException {
        String query = "SELECT id FROM proyek WHERE nama_proyek = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, namaProyek);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }
        return -1;
    }
    
    private int getProyekIdForNextKonten() throws SQLException {
        String query = "SELECT id FROM proyek ORDER BY id DESC LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return 1;
    }

    public void insertKonten(String namaUser, String namaMedia, String namaPlatform, String linkDesain, String tema, String deadline, String tglPost, String status) throws SQLException {
        int proyekId = getProyekIdForNextKonten();

        String query = "INSERT INTO konten (user_id, media_id, platform_id, link_desain, tema, deadline, tgl_post, status, proyek_id) VALUES ((SELECT id FROM user WHERE nama = ?), (SELECT id FROM media WHERE nama_media = ?), (SELECT id FROM platform WHERE nama_platform = ?), ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, namaUser);
            stmt.setString(2, namaMedia);
            stmt.setString(3, namaPlatform);
            stmt.setString(4, linkDesain);
            stmt.setString(5, tema);
            stmt.setString(6, deadline);
            stmt.setString(7, tglPost);
            stmt.setString(8, status);
            stmt.setInt(9, proyekId);
            stmt.executeUpdate();
        }
        updateKontenNumbers();
    }

    public void updateKontenNumbers() throws SQLException {
        String resetQuery = "SET @row_number = 0";
        String updateQuery = "UPDATE konten SET id = (@row_number:=@row_number + 1) ORDER BY id";

        try (PreparedStatement resetStmt = connection.prepareStatement(resetQuery);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            resetStmt.execute(); 

            updateStmt.executeUpdate();
        }
    }
}
