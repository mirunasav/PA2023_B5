package homework;

import compulsory.Database;

import java.sql.*;

public class ArtistDAO {
    public void create(String name) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        Artist artist = new Artist(name);
        String QUERY = "insert into artists(name) values(?)";
        try (PreparedStatement pstmt = con.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                int id = rs.getInt(1);
                artist.setArtistID(id);
            }
        }
        //acum i-a fost atribuit si un ID pe care il pot lua din baza de date
    }


    public Artist findByName(String name) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from artists where name='" + name + "'")) {
            int id = rs.next() ? rs.getInt(1) : 0;
            if (id == 0){
                DBCPDataSource.getConnection().close();
                return null; //not found
            }
            else {
                Artist foundArtist = new Artist(rs.getString(2));
                foundArtist.setArtistID(id);
                DBCPDataSource.getConnection().close();
                return foundArtist;
            }

        }
    }

    public Artist findByID(Integer artistID) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("select * from artists where id = ?")) {
            pstmt.setInt(1, artistID);
            ResultSet rs = pstmt.executeQuery();
            Artist foundArtist = null;
            while (rs.next()) {
                foundArtist = new Artist(rs.getString(2));
                foundArtist.setArtistID(artistID);
            }
            return foundArtist;
        }
    }
}
