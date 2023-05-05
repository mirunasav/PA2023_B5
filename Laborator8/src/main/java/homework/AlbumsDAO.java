package homework;

import compulsory.Database;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AlbumsDAO {
    public void create(Integer releaseYear, String title, Integer artistID) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        Album album = new Album(releaseYear, title, artistID);
        String QUERY = "insert into albums(release_year, title, artist) values(?,?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, releaseYear);
            pstmt.setString(2, title);
            pstmt.setInt(3, artistID);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                int id = rs.getInt(1);
                album.setID(id);
            }
        }
        //acum i-a fost atribuit si un ID pe care il pot lua din baza de date
    }

    public Album findByTitle(String title) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from albums where title='" + title + "'")) {
            int id = rs.next() ? rs.getInt(1) : 0;
            if (id == 0)
                return null; //not found
            else {
                Album album = new Album(rs.getInt(2), rs.getString(3), rs.getInt(4));
                album.setID(id);
                return album;
            }
        }
    }

    public List<Album> findByReleaseYear(Integer releaseYear) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("select * from albums where release_year = ?")) {
            pstmt.setInt(1, releaseYear);
            ResultSet rs = pstmt.executeQuery();
            List<Album> listOfAlbums = new LinkedList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                Album album = new Album(rs.getInt(2), rs.getString(3), rs.getInt(4));
                album.setID(id);
                listOfAlbums.add(album);
            }
            return listOfAlbums;
        }

    }

    public Album findByID(Integer albumID) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("select * from albums where id = ?")) {
            pstmt.setInt(1, albumID);
            ResultSet rs = pstmt.executeQuery();
            Album foundAlbum = null;
            while (rs.next()) {
                foundAlbum = new Album(rs.getInt(1), rs.getString(2), rs.getInt(4));
                foundAlbum.setID(albumID);
            }
            return foundAlbum;
        }
    }

    public void getAlbumCount() throws SQLException {
        Connection con = DBCPDataSource.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select count(*) from albums")) {
            rs.next();
            int number = rs.getInt(1);
            System.out.println("avem " + number + " albume");
        }
    }

}
