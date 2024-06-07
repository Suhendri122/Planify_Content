package com.mycompany.planifycontent.database;

import com.mycompany.planifycontent.TableMedia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MediaDAO {
    private final Connection connection;

    public MediaDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableMedia> getAllMedia() throws SQLException {
        List<TableMedia> medias = new ArrayList<>();
        String query = "SELECT id, nama_media FROM media";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int no = 1;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nama = resultSet.getString("nama_media");
                TableMedia media = new TableMedia(no++, nama, "");
                medias.add(media);
            }
        }
    return medias;
}

    public void deleteMedia(int id) throws SQLException {
        String query = "DELETE FROM media WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        updateMediaNumbers(); // Update platform numbers after deletion
    }

    public void updateMediaNumbers() throws SQLException {
        String query = "SET @row_number = 0; " +
                       "UPDATE media SET id = (@row_number:=@row_number + 1) ORDER BY id;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.executeUpdate();
        }
    }

    public void updateMedia(int id, String newName) throws SQLException {
        String query = "UPDATE media SET nama_media = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newName);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void insertMedia(String name) throws SQLException {
        String query = "INSERT INTO media (nama_media) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }
}
