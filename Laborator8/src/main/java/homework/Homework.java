package homework;

import compulsory.Database;

import java.sql.SQLException;

public class Homework {
    public static void main(String[] args) {
        try{
            var albums = new AlbumsDAO();

            //albums.getAlbumCount();
            //albums.create(1983, "The final cut", 1);
            //albums.create(1983, "Thriller", 2);
            var someAlbum = albums.findByTitle("Thriller");
            someAlbum.print();

            //finding albums by release year
            var albumsByYear = albums.findByReleaseYear(1983);
            for(Album album : albumsByYear)
                album.print();

           albums.findByID(1).print();

           var albumsGenre = new AlbumGenreAssociationDAO();
           AlbumGenreAssociation albumGenreAssociation = albumsGenre.findByAlbumID(1);

           albumsGenre.createAssociation(12,23);//albume care nu exista
            Database.getConnection().commit();
            Database.getConnection().close();
        } catch (SQLException e) {
            System.err.println(e);
            try{
                Database.getConnection().rollback();
            }
            catch (SQLException error){
                System.err.println("yet another error");
            }
        }

    }
}
