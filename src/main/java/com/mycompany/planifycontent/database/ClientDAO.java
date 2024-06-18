package com.mycompany.planifycontent.database;

import com.mycompany.planifycontent.TableClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    private final Connection connection;

    public ClientDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableClient> getAllClients() throws SQLException {
        List<TableClient> clients = new ArrayList<>();
        String query = "SELECT id, nama, no_telp, usaha FROM Client";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            int no = 1;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nama = resultSet.getString("nama");
                String no_telp = resultSet.getString("no_telp");
                String usaha = resultSet.getString("usaha");
                TableClient clientItem = new TableClient(no++, nama, no_telp, usaha, "");
                clients.add(clientItem);
                clientItem.setId(id);
            }
        }
        return clients;
    }

    public void addClient(TableClient client) throws SQLException {
        String query = "INSERT INTO Client (nama, no_telp, usaha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, client.getNama());
            stmt.setString(2, client.getNo_telp());
            stmt.setString(3, client.getUsaha());
            stmt.executeUpdate();
        }
        updateClientNumbers(); // Update client numbers after adding
    }

    public List<TableClient> getDataClientsByFilter(String nama, String usaha) throws SQLException {
        List<TableClient> filteredData = new ArrayList<>();
        String query = "SELECT id, nama, no_telp, usaha FROM Client";

        if ((nama != null && !nama.isEmpty()) || (usaha != null && !usaha.isEmpty())) {
            query += " WHERE ";
        }

        if (nama != null && !nama.isEmpty()) {
            query += " nama LIKE ?";
            if (usaha != null && !usaha.isEmpty()) {
                query += " AND usaha LIKE ?";
            }
        } else if (usaha != null && !usaha.isEmpty()) {
            query += " usaha LIKE ?";
        }

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            int parameterIndex = 1;
            if (nama != null && !nama.isEmpty()) {
                pstmt.setString(parameterIndex++, "%" + nama + "%");
            }
            if (usaha != null && !usaha.isEmpty()) {
                pstmt.setString(parameterIndex++, "%" + usaha + "%");
            }

            ResultSet resultSet = pstmt.executeQuery();
            int no = 1;
            while (resultSet.next()) {
                String namaClient = resultSet.getString("nama");
                String noTelp = resultSet.getString("no_telp");
                String usahaClient = resultSet.getString("usaha");
                TableClient clientItem = new TableClient(no++, namaClient, noTelp, usahaClient, "");
                filteredData.add(clientItem);
            }
        }
        return filteredData;
    }

    public void deleteClient(int id) throws SQLException {
        String query = "DELETE FROM client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        updateClientNumbers(); // Update client numbers after deletion
    }

    public void updateClientNumbers() throws SQLException {
        String selectQuery = "SELECT id FROM client ORDER BY id";
        String updateQuery = "UPDATE client SET id = ? WHERE id = ?";
        
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

    public void updateClient(int id, String newName, String newNoTelp, String newUsaha) throws SQLException {
        String query = "UPDATE client SET nama = ?, no_telp = ?, usaha = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newName);
            stmt.setString(2, newNoTelp);
            stmt.setString(3, newUsaha);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    public void insertClient(String name) throws SQLException {
        String query = "INSERT INTO client (nama) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
        updateClientNumbers(); // Update client numbers after insertion
    }
}
