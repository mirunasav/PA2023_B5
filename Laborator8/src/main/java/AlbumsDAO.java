import java.sql.*;

public class AlbumsDAO {
    public void create(Integer releaseYear, String title, Integer artistID) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "insert into albums(release_year, title, artist) values(?,?,?)")) {
            pstmt.setInt(1, releaseYear);
            pstmt.setString(2, title);
            pstmt.setInt(3, artistID);
            pstmt.executeUpdate();
        }
    }


    public Integer findByTitle(String title) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select title from albums where title='" + title + "'")) {
            return rs.next() ? rs.getInt(1) : null;
        }
    }

    public Integer findByReleaseYear(Integer releaseYear) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("select id from albums where release_year = ?")) {
            pstmt.setInt(1, releaseYear);
            ResultSet rs = pstmt.executeQuery();
            //returnez id, adica coloana 1
            return rs.next() ? rs.getInt(1) : null;
        }
    }

    public String findByID(Integer albumID) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("select title from albums where id = ?")) {
            pstmt.setInt(1, albumID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getString(1) : null;
        }
    }

    public void getGenres(Integer albumID) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("select * from album_genres where album_id = ?")) {
            pstmt.setInt(1, albumID);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Genres of album " + this.findByID(albumID) + ": ");
            while (rs.next()) {
                System.out.println(rs.getString(2));
            }
        }
    }
}
