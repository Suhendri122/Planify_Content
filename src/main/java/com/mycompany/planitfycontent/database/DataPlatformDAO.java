package com.mycompany.planitfycontent.database;

import com.mycompany.planitfycontent.TableDataPlatform;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataPlatformDAO {
    private final Connection connection;

    public DataPlatformDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableDataPlatform> getAllDataPlatforms() throws SQLException {
    List<TableDataPlatform> platforms = new ArrayList<>();
    String query = "SELECT id, nama_platform FROM platform";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        ResultSet resultSet = preparedStatement.executeQuery();
        int no = 1;
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nama = resultSet.getString("nama_platform");
            TableDataPlatform platform = new TableDataPlatform(no++, nama);
            platforms.add(platform);
        }
    }
    return platforms;
}

}
