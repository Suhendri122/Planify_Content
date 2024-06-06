package com.mycompany.planifycontent.database;

import com.mycompany.planifycontent.TableHistori;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.DatePicker;

public class HistoriDAO {
    private final Connection connection;

    public HistoriDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableHistori> getHistoriData(String startDate, String endDate) throws SQLException {
    List<TableHistori> historiList = new ArrayList<>();
    String query = "SELECT proyek.id AS id, proyek.nama_proyek, user.nama AS pic_proyek, client.nama AS nama_client, " +
                   "konten.tema, media.nama_media, platform.nama_platform, konten.link_desain, " +
                   "konten.deadline, konten.tgl_post, user_konten.nama AS pic_konten, konten.status " +
                   "FROM konten " +
                   "INNER JOIN proyek ON konten.id = proyek.id " +
                   "INNER JOIN user ON proyek.user_id = user.id " +
                   "INNER JOIN client ON proyek.client_id = client.id " +
                   "INNER JOIN media ON konten.media_id = media.id " +
                   "INNER JOIN platform ON konten.platform_id = platform.id " +
                   "INNER JOIN user AS user_konten ON konten.user_id = user_konten.id";

    if (!startDate.isEmpty() && !endDate.isEmpty()) {
        query += " WHERE konten.tgl_post BETWEEN ? AND ?";
    } else if (!startDate.isEmpty()) {
        query += " WHERE konten.tgl_post >= ?";
    } else if (!endDate.isEmpty()) {
        query += " WHERE konten.tgl_post <= ?";
    }

    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
        } else if (!startDate.isEmpty()) {
            preparedStatement.setString(1, startDate);
        } else if (!endDate.isEmpty()) {
            preparedStatement.setString(1, endDate);
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String namaProyek = resultSet.getString("nama_proyek");
            String picProyek = resultSet.getString("pic_proyek");
            String namaClient = resultSet.getString("nama_client");
            String tema = resultSet.getString("tema");
            String media = resultSet.getString("nama_media");
            String platform = resultSet.getString("nama_platform");
            String link = resultSet.getString("link_desain");
            String deadline = resultSet.getString("deadline");
            String tglPost = resultSet.getString("tgl_post");
            String picKonten = resultSet.getString("pic_konten");
            String status = resultSet.getString("status");

            TableHistori histori = new TableHistori(id, namaProyek, picProyek, namaClient, tema, media, platform, link, deadline, tglPost, picKonten, status);
            historiList.add(histori);
        }
    }
    return historiList;
}
}
