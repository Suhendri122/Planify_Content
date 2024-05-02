package com.mycompany.planitfycontent.database;

import com.mycompany.planitfycontent.TableDataMedia;
import com.mycompany.planitfycontent.TableDataPlatform;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataMediaDAO {
    private final Connection connection;

    public DataMediaDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableDataMedia> getAllDataMedia() throws SQLException {
    List<TableDataMedia> media = new ArrayList<>();
    String query = "SELECT id, nama_media FROM media";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        ResultSet resultSet = preparedStatement.executeQuery();
        int no = 1;
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nama = resultSet.getString("nama_media");
            TableDataMedia mediaItem = new TableDataMedia(no++, nama);
            media.add(mediaItem);

        }
    }
    return media;
}

}
