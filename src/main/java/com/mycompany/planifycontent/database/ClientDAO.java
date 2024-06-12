package com.mycompany.planifycontent.database;

import com.mycompany.planifycontent.TableClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.planifycontent.TableClient;

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
                String nama = resultSet.getString("nama");
            String no_telp = resultSet.getString("no_telp");
            String usaha = resultSet.getString("usaha");
            TableClient clientItem = new TableClient(no++, nama, no_telp, usaha, "");
            clients.add(clientItem);
        }
    }
    return clients;
}

    public void addClient(TableClient client) throws SQLException {
        String query = "INSERT INTO Client (nama, no_telp, usaha) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, client.getNama());
            preparedStatement.setString(2, client.getNoTelp());
            preparedStatement.setString(3, client.getUsaha());
            preparedStatement.executeUpdate();
        }
    }

   public List<TableClient> getDataClientsByFilter(String nama, String usaha) throws SQLException {
    List<TableClient> filteredData = new ArrayList<>();
    String query = "SELECT id, nama, no_telp, usaha FROM Client";

    if (nama!= null &&!nama.isEmpty() || usaha!= null &&!usaha.isEmpty()) {
        query += " WHERE ";
    }

    if (nama!= null &&!nama.isEmpty()) {
        query += " nama LIKE?";
        if (usaha!= null &&!usaha.isEmpty()) {
            query += " AND usaha LIKE?";
        }
    } else if (usaha!= null &&!usaha.isEmpty()) {
        query += " usaha LIKE?";
    }

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        int parameterIndex = 1;
        if (nama!= null &&!nama.isEmpty()) {
            pstmt.setString(parameterIndex++, "%" + nama + "%");
        }
        if (usaha!= null &&!usaha.isEmpty()) {
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
        String query = "DELETE FROM client WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        updateClientNumbers();
    }

    public void updateClientNumbers() throws SQLException {
        String query = "SET @row_number = 0; " +
                       "UPDATE platform SET id = (@row_number:=@row_number + 1) ORDER BY id;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.executeUpdate();
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
    }


}
