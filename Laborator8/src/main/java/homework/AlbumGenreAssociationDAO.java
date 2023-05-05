package homework;

import compulsory.Database;

import java.sql.*;

public class AlbumGenreAssociationDAO {
    //practic dau ca parametru doua id uri, mai intai verifica daca exista
    //albumul, apoi daca exista genre ul, apoi le insereaza in tabel
    public void createAssociation(int albumID, int genreID) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        //verific daca exista album cu acel id
        var albums = new AlbumsDAO();

        Album album = albums.findByID(albumID);
        if (album == null) {
            throw new SQLException("no such album");
        }
        var genres = new GenresDAO();
        Genre genre = genres.findByID(genreID);
        if (genre == null) {
            throw new SQLException("no such genre");
        }
        String QUERY = "insert into album_genres(album_id, genre_id) values(?)";
        AlbumGenreAssociation albumGenre = new AlbumGenreAssociation(albumID, genreID);

        try (PreparedStatement pstmt = con.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, albumID);
            pstmt.setInt(2, genreID);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
        }
    }

    public AlbumGenreAssociation findByAlbumID(Integer albumID) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("select * from album_genres where album_id = ?")) {
            pstmt.setInt(1, albumID);
            ResultSet rs = pstmt.executeQuery();
            AlbumGenreAssociation foundAssociation = null;
            while (rs.next()) {
                foundAssociation = new AlbumGenreAssociation(rs.getInt(1), rs.getInt(2));
            }
            return foundAssociation;
        }
    }

    public AlbumGenreAssociation findByGenreID(Integer genreID) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("select * from album_genres where album_id = ?")) {
            pstmt.setInt(1, genreID);
            ResultSet rs = pstmt.executeQuery();
            AlbumGenreAssociation foundAssociation = null;
            while (rs.next()) {
                foundAssociation = new AlbumGenreAssociation(rs.getInt(1), rs.getInt(2));
            }
            return foundAssociation;
        }
    }
}
