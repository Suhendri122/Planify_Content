package com.mycompany.planifycontent.database;


import com.mycompany.planifycontent.TableDashboard;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO {
    private final Connection connection;

    public DashboardDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TableDashboard> getDashboardData() throws SQLException {
        List<TableDashboard> dashboardData = new ArrayList<>();
        String query = "SELECT konten.id, proyek.nama_proyek, proyek.user_id AS pic_proyek, konten.tema, konten.media_id, konten.deadline, konten.tgl_post, konten.user_id AS pic_konten " +
                       "FROM proyek " +
                       "INNER JOIN konten ON proyek.id = konten.user_id;";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String namaProyek = resultSet.getString("nama_proyek");
                String picProyekId = resultSet.getString("pic_proyek");
                String tema = resultSet.getString("tema");
                String mediaId = resultSet.getString("media_id");
                String deadline = resultSet.getString("deadline");
                String tglPost = resultSet.getString("tgl_post");
                String picKontenId = resultSet.getString("pic_konten");

                // Mendapatkan nama pengguna berdasarkan ID
                String namaPicProyek = getNamaUserById(picProyekId);
                String namaPicKonten = getNamaUserById(picKontenId);

                // Mendapatkan nama media berdasarkan ID
                String namaMedia = getNamaMediaById(mediaId);
                
                TableDashboard data = new TableDashboard(id, namaProyek, namaPicProyek, tema, namaMedia, deadline, tglPost, namaPicKonten);

                dashboardData.add(data);
            }
        }

        return dashboardData;
    }

    private String getNamaUserById(String userId) throws SQLException {
        String query = "SELECT nama FROM user WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("nama");
            }
        }
        return ""; // Return empty string jika ID tidak ditemukan
    }

    private String getNamaMediaById(String mediaId) throws SQLException {
        String query = "SELECT nama_media FROM media WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mediaId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("nama_media");
            }
        }
        return ""; // Return empty string jika ID tidak ditemukan
    }
}
