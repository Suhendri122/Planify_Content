package com.mycompany.planifycontent.database;

import com.mycompany.planifycontent.TableUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableUser> getAllDataUsers() throws SQLException {
        List<TableUser> users = new ArrayList<>();
        String query = "SELECT id, nama, email FROM user";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int no = 1;
            while (resultSet.next()) {
                String nama = resultSet.getString("nama");
                String email = resultSet.getString("email");
                TableUser userItem = new TableUser(no++, nama, email);
                users.add(userItem);
            }
        }
        return users;
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
