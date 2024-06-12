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

    public List<TableClient> getAllDataClients() throws SQLException {
        List<TableClient> clients = new ArrayList<>();
        String query = "SELECT id, nama, no_telp, usaha FROM Client";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int no = 1;
            while (resultSet.next()) {
                String nama = resultSet.getString("nama");
                String no_telp = resultSet.getString("no_telp");
                String usaha = resultSet.getString("usaha");
                TableClient clientItem = new TableClient(no++, nama, no_telp, usaha);
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
}
