package com.mycompany.planifycontent.database;

import com.mycompany.planifycontent.TableClient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    private final Connection connection;

    public ClientDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableClient> getAllDataClients() throws SQLException {
        List<TableClient> clients = new ArrayList<>();
        Statement statement = connection.createStatement();
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
            TableClient clientItem = new TableClient(no++, namaClient, noTelp, usahaClient);
            filteredData.add(clientItem);
        }
    }
    return filteredData;
}
}
