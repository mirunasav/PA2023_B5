package homework;

import compulsory.Database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
                var artist = artistDAO.findByName(artistName);
                //daca nu gasesc artistul
                //altfel:
                if(artist==null)
                    continue;

                int artistID = artist.getArtistID();

                albumStatement.setInt(1,releaseYear);
                albumStatement.setString(2,albumTitle);
                albumStatement.setInt(3,artistID);

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

    public static void main(String[] args) {
        DatabaseInsertionTool.addAlbums();
        /*try {
            Connection connection = DBCPDataSource.getConnection();

            String insertArtistSQL = "INSERT INTO artists (name) VALUES (?)";
            String insertAlbumSQL = "insert into albums(release_year,title,artist) values(?,?,?)";
            String insertGenreSQL = "insert into genres(name) values(?)";
            String insertGenreAlbumAssociation = "insert into album_genres(album_id, genre_id) values(?,?)";

            PreparedStatement artistStatemet = connection.prepareStatement(insertArtistSQL);
            PreparedStatement albumStatement = connection.prepareStatement(insertAlbumSQL);
            PreparedStatement genreStatement = connection.prepareStatement(insertGenreSQL);
            PreparedStatement genreAlbumStatement = connection.prepareStatement(insertGenreAlbumAssociation);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String releaseYear = data[1];//asta tb sa fac cast la int
                int releaseYearInt = Integer.parseInt(releaseYear);
                String albumName = data[2];
                String artistName = data[3];
                String genreName = data[4];

                artistStatemet.setString(1, artistName);
                artistStatemet.executeUpdate();

                Artist artist =
                        albumStatement.setInt(1, releaseYearInt);
                albumStatement.setString(2, albumName);
                albumStatement.setInt(3, albumName);//fac pe rand mai bine?
                statement.setString(2, studentName);


                statement.setString(5, comment);

                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }

            lineReader.close();
            Database.getConnection().commit();
            Database.getConnection().close();
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
        }*/
    }
}
