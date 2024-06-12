package com.mycompany.planifycontent.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.planifycontent.TableUser;

public class UserDAO {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableUser> getAllUsers() throws SQLException {
        List<TableUser> users = new ArrayList<>();
        String query = "SELECT id, nama , email FROM user";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int no = 1;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nama = resultSet.getString("nama");
                String email = resultSet.getString("email");
                TableUser user = new TableUser(no++, nama, email, "");
                users.add(user);
            }
        }
        return users;
    }
    
    public void deleteUser(int id) throws SQLException {
        String query = "DELETE FROM user WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        updateUserNumbers();
    }

    public void updateUserNumbers() throws SQLException {
        String query = "SET @row_number = 0; " +
                       "UPDATE platform SET id = (@row_number:=@row_number + 1) ORDER BY id;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.executeUpdate();
        }
    }

    public void updateUser(int id, String newName) throws SQLException {
        String query = "UPDATE user SET nama = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newName);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void insertUser(String name) throws SQLException {
        String query = "INSERT INTO user (nama) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }

    public int tambahUser(String nama, String email) throws SQLException {
        String query = "INSERT INTO user (nama, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, nama);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, "defaultPassword"); // Nilai tetap untuk kolom password
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the generated user ID
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }
}
