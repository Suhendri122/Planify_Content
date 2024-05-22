package com.mycompany.planifycontent.database;

import com.mycompany.planifycontent.TablePlatform;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlatformDAO {
    private final Connection connection;

    public PlatformDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TablePlatform> getAllDataPlatforms() throws SQLException {
    List<TablePlatform> platforms = new ArrayList<>();
    String query = "SELECT id, nama_platform FROM platform";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        ResultSet resultSet = preparedStatement.executeQuery();
        int no = 1;
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nama = resultSet.getString("nama_platform");
            TablePlatform platform = new TablePlatform(no++, nama);
            platforms.add(platform);
        }
    }
    return platforms;
}

}
