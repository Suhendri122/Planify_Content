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

    public List<TablePlatform> getAllPlatforms() throws SQLException {
        List<TablePlatform> platforms = new ArrayList<>();
        String query = "SELECT id, nama_platform FROM platform";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int no = 1;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nama = resultSet.getString("nama_platform");
                TablePlatform platform = new TablePlatform(no++, nama, "");
                platforms.add(platform);
                platform.setId(id);
            }
        }
        return platforms;
    }

    public void tambahPlatform(String namaPlatform) throws SQLException {
        String query = "INSERT INTO platform (nama_platform) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, namaPlatform);
            preparedStatement.executeUpdate();
        }
    }
    public void deletePlatform(int id) throws SQLException {
    String query = "DELETE FROM platform WHERE id=?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
    updatePlatformNumbers(); // Update platform numbers after deletion
}

public void updatePlatformNumbers() throws SQLException {
    String selectQuery = "SELECT id FROM platform ORDER BY id";
    String updateQuery = "UPDATE platform SET id = ? WHERE id = ?";
    
    try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
         ResultSet resultSet = selectStmt.executeQuery()) {
         
        int newId = 1;
        while (resultSet.next()) {
            int oldId = resultSet.getInt("id");
            try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                updateStmt.setInt(1, newId);
                updateStmt.setInt(2, oldId);
                updateStmt.executeUpdate();
            }
            newId++;
        }
    }
}

public void updatePlatform(int id, String newName) throws SQLException {
    String query = "UPDATE platform SET nama_platform = ? WHERE id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, newName);
        stmt.setInt(2, id);
        stmt.executeUpdate();
    }
}


    public void insertPlatform(String name) throws SQLException {
        String query = "INSERT INTO platform (nama_platform) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }


   
}
