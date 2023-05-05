package compulsory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AlbumsGenresAssociation {
    public void create(Integer albumID, Integer artistID) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "insert into album_genres(album_id, genre_id) values(?,?)")) {
            pstmt.setInt(1, albumID);
            pstmt.setInt(2, artistID);
            pstmt.executeUpdate();
        }
    }
}
