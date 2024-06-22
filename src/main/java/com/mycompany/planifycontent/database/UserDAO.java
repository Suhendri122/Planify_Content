package com.mycompany.planifycontent.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.planifycontent.TableUser;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
                user.setId(id);
            }
        }
        return users;
    }
    
    public List<String> getAllUserNames() throws SQLException {
        List<String> userNames = new ArrayList<>();
        String query = "SELECT nama FROM user";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                userNames.add(resultSet.getString("nama"));
            }
        }
        return userNames;
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
        String selectQuery = "SELECT id FROM user ORDER BY id";
        String updateQuery = "UPDATE user SET id = ? WHERE id = ?";
        
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
        updateUserNumbers();
    }

    public int tambahUser(String nama, String email, String password) throws SQLException {
        String query = "INSERT INTO user (nama, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, nama);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, hashPasswordMD5(password));
            preparedStatement.executeUpdate();
            updateUserNumbers();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    private String hashPasswordMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
