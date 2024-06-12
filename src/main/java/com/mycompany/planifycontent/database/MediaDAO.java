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

    public List<TableMedia> getAllDataMedia() throws SQLException {
        List<TableMedia> mediaList = new ArrayList<>();
        String query = "SELECT id, nama_media FROM media";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int no = 1;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nama = resultSet.getString("nama_media");
                TableMedia media = new TableMedia(no++, nama);
                mediaList.add(media);
            }
        }
        return mediaList;
    }

    public void tambahMedia(String namaMedia) throws SQLException {
        String query = "INSERT INTO media (nama_media) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, namaMedia);
            preparedStatement.executeUpdate();
        }
    }
}
