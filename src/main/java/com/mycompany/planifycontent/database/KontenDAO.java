package com.mycompany.planifycontent.database;

import com.mycompany.planifycontent.TableKonten;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KontenDAO {
    private final Connection connection;

    public KontenDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableKonten> getAllKonten() throws SQLException {
        List<TableKonten> kontenList = new ArrayList<>();
        String query = "SELECT konten.id, user.nama AS nama_user, media.nama_media, platform.nama_platform, konten.link_desain, konten.tema, konten.deadline, konten.tgl_post, konten.status " +
                       "FROM konten " +
                       "INNER JOIN user ON konten.user_id = user.id " +
                       "INNER JOIN media ON konten.media_id = media.id " +
                       "INNER JOIN platform ON konten.platform_id = platform.id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String namaUser = resultSet.getString("nama_user");
                String namaMedia = resultSet.getString("nama_media");
                String namaPlatform = resultSet.getString("nama_platform");
                String linkDesain = resultSet.getString("link_desain");
                String tema = resultSet.getString("tema");
                String deadline = resultSet.getString("deadline");
                String tglPost = resultSet.getString("tgl_post");
                String status = resultSet.getString("status");

                TableKonten konten = new TableKonten(id, namaUser, namaMedia, namaPlatform, linkDesain, tema, deadline, tglPost, status);
                kontenList.add(konten);
            }
        }

        return kontenList;
    }
}
