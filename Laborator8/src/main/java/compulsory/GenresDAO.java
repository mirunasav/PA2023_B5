package compulsory;

import java.sql.*;

public class GenresDAO {
    public void create(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "insert into genres(name) values(?)")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }
    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id from genres where name='" + name + "'")) {
            return rs.next() ? rs.getInt(1) : null;
        }
    }

    public String findByID(Integer ID) throws SQLException {
        Connection con = Database.getConnection();
        //pot face asta si cu un statement simplu ? am incercat cu where id = ID si nu e ok; //
        try (PreparedStatement pstmt = con.prepareStatement("select title from albums where id = ?")){
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getString(1) : null;
        }
    }
}
