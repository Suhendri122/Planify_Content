package com.mycompany.planitfycontent.database;

import com.mycompany.planitfycontent.TableDataClient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataClientDAO {
    private final Connection connection;

    public DataClientDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableDataClient> getAllDataClients() throws SQLException {
        List<TableDataClient> clients = new ArrayList<>();
        String query = "SELECT id, nama, no_telp, usaha FROM Client";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int no = 1;
            while (resultSet.next()) {
            String nama = resultSet.getString("nama");
                String no_telp = resultSet.getString("no_telp");
                String usaha = resultSet.getString("usaha");
                TableDataClient clientItem = new TableDataClient(no++, nama, no_telp, usaha);
                clients.add(clientItem);
            }
        }
        return clients;
    }
}
