package com.mycompany.planitfycontent.database;

import com.mycompany.planitfycontent.TableDataUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataUserDAO {
    private final Connection connection;

    public DataUserDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableDataUser> getAllDataUsers() throws SQLException {
        List<TableDataUser> users = new ArrayList<>();
        String query = "SELECT id, nama, email FROM user";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int no = 1;
            while (resultSet.next()) {
                String nama = resultSet.getString("nama");
                String email = resultSet.getString("email");
                TableDataUser userItem = new TableDataUser(no++, nama, email);
                users.add(userItem);
            }
        }
        return users;
    }

}
