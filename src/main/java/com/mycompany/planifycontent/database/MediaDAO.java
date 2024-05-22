package com.mycompany.planifycontent.database;

import com.mycompany.planifycontent.TableMedia;
import com.mycompany.planifycontent.TablePlatform;
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
    List<TableMedia> media = new ArrayList<>();
    String query = "SELECT id, nama_media FROM media";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        ResultSet resultSet = preparedStatement.executeQuery();
        int no = 1;
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nama = resultSet.getString("nama_media");
            TableMedia mediaItem = new TableMedia(no++, nama);
            media.add(mediaItem);

        }
    }
    return media;
}

}
