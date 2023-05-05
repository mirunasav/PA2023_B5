package compulsory;

import java.sql.*;

public class DatabaseViewer {

    public static void viewAllArtists() {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from artists")) {
            System.out.println("Artists: ");
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                System.out.println("id: " + id + " name: " + name);
            }
        } catch (SQLException e) {
            System.out.println("Eroare la viewAllArtists" + e.toString());
        }
    }

    public static void viewAllGenres() {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from genres")) {
            System.out.println("Genres : ");
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                System.out.println("id: " + id + " name: " + name);
            }
        } catch (SQLException e) {
            System.out.println("Eroare la viewAllGenres" + e.toString());
        }
    }

    public static void viewAllAlbums() {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from albums")) {
            System.out.println("Albums : ");
            while (rs.next()) {
                int id = rs.getInt(1);
                int releaseYear = rs.getInt(2);
                String title = rs.getString(3);
                int artistID = rs.getInt(4);
                String artistName = new ArtistDAO().findByID(artistID);
                System.out.println("id: " + id + " release year : " + releaseYear + " title: " + title + " artist : "+ artistName);
            }
        } catch (SQLException e) {
            System.out.println("Eroare la viewAllAlbums" + e.toString());
        }
    }

    public static void viewAllAlbumGenres() {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from album_genres")) {
            System.out.println("Albums-Genre Associations : ");
            while (rs.next()) {
                int albumID = rs.getInt(1);
                int artistID = rs.getInt(2);
                String albumName = new AlbumsDAO().findByID(albumID);
                String artistName = new ArtistDAO().findByID(artistID);
                System.out.println("album: " + albumName + ";  artist : " + artistName);
            }
        } catch (SQLException e) {
            System.out.println("Eroare la viewAllAlbums" + e.toString());
        }
    }
}
