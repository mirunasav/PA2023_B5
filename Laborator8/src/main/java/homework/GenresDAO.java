package homework;

import compulsory.Database;

import java.sql.*;

public class GenresDAO {
    public void create(String name) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        Genre genre = new Genre(name);
        String QUERY = "insert into genres(name) values(?)";
        try (PreparedStatement pstmt = con.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                int id = rs.getInt(1);
                genre.setID(id);
            }
        }
        //acum i-a fost atribuit si un ID pe care il pot lua din baza de date
    }


    public Genre findByName(String name) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from genre where name='" + name + "'")) {
            int id = rs.next() ? rs.getInt(1) : 0;
            if (id == 0)
                return null; //not found
            else {
                Genre foundGenre = new Genre(rs.getString(2));
                foundGenre.setID(id);
                return foundGenre;
            }
        }
    }

    public Genre findByID(Integer genreID) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("select * from genres where id = ?")) {
            pstmt.setInt(1, genreID);
            ResultSet rs = pstmt.executeQuery();
            Genre foundGenre = null;
            while (rs.next()) {
                foundGenre = new Genre(rs.getString(2));
                foundGenre.setID(genreID);
            }
            return foundGenre;
        }
    }
}
