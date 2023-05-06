package homework;

import compulsory.Database;
import org.postgresql.util.PSQLException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DatabaseInsertionTool {
    static String csvFilePath = "C:\\Users\\Miruna Savin\\IdeaProjects\\PA2023_B5\\Laborator8\\src\\main\\java\\homework\\albumlist.csv";

    static int batchSize = 20;
    static ArtistDAO artistDAO = new ArtistDAO();

    public static void addAlbums() {
        try {
            Connection connection = DBCPDataSource.getConnection();

            String insertAlbumSQL = "INSERT INTO albums (release_year, title, artist) VALUES (?,?,?)";

            PreparedStatement albumStatement = connection.prepareStatement(insertAlbumSQL);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            int numberOfAlbumsAdded = 0;
            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                int releaseYear = Integer.parseInt(data[1]);
                String albumTitle = data[2];
                String artistName = data[3];
                //caut artistul
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("select * from artists where name='" + artistName + "'");
                int id = rs.next() ? rs.getInt(1) : 0;
                if (id == 0){
                    continue;
                }
                //altfel:

                albumStatement.setInt(1,releaseYear);
                albumStatement.setString(2,albumTitle);
                albumStatement.setInt(3,id);

                albumStatement.addBatch();
                count++;
                numberOfAlbumsAdded++;

                System.out.println("inca 1");
                if(count% batchSize == 0) {
                    albumStatement.executeBatch();
                    count = 0;
                }
            }

            lineReader.close();
            albumStatement.executeBatch();
            DBCPDataSource.getConnection().commit();
            DBCPDataSource.getConnection().close();
        } catch (SQLException e) {
            System.err.println(e);
            try {
                Database.getConnection().rollback();
            } catch (SQLException error) {
                System.err.println("yet another error");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addAlbumGenreAssociations(){
        try {
            Connection connection = DBCPDataSource.getConnection();
            String insertAssociation = "INSERT INTO album_genres (album_id, genre_id) VALUES (?,?)";

            PreparedStatement associationStatement = connection.prepareStatement(insertAssociation);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            int numberOfAlbumsAdded = 0;
            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");

                String albumTitle = data[2];
                String genreName = data[4];
                //caut genre-ul
                Statement searchGenre = connection.createStatement();
                ResultSet rs = searchGenre.executeQuery("select * from genres where name ='" + genreName + "'");
                int genreID =  rs.next() ? rs.getInt(1) : 0;
                rs.close();
                if (genreID == 0){
                    continue;
                }
                System.out.println("am gasit genre id " + genreID);
                //caut albumul
                Statement searchAlbum = connection.createStatement();
                ResultSet rsAlbum = searchAlbum.executeQuery("select * from albums where title='" + albumTitle + "'");
                int albumId = rsAlbum.next() ? rsAlbum.getInt(1) : 0;
                rsAlbum.close();

                if (albumId == 0){
                    continue;
                }
                System.out.println("am gasit album id "+ albumId);
                //altfel: am gasit atat album id cat si artist i

                associationStatement.setInt(1,albumId);
                associationStatement.setInt(2,genreID);


                count++;
                try{
                    associationStatement.executeUpdate();
                }
                catch (PSQLException e){
                    continue;
                }
                System.out.println("inca 1");
                System.out.println(count);

              /*  associationStatement.addBatch();
                count++;
                numberOfAlbumsAdded++;

                if(count% batchSize == 0) {
                    associationStatement.executeBatch();
                    count = 0;
                }*/
            }

            lineReader.close();
            DBCPDataSource.getConnection().commit();
            DBCPDataSource.getConnection().close();
        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
            try {
                Database.getConnection().rollback();
            } catch (SQLException error) {
                System.err.println("yet another error");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //DatabaseInsertionTool.addAlbums();
       DatabaseInsertionTool.addAlbumGenreAssociations();
    }
}
