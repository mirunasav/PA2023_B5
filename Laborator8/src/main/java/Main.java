import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try{
            var artists = new ArtistDAO();
//            artists.create("Pink Floyd");
//            artists.create("Michael Jackson");
            var genres = new GenresDAO();
//            genres.create("Rock");
//            genres.create("Indie");
//            genres.create("Metal");
//            genres.create("Pop");
//            genres.create("Jazz");
//            genres.create("Trap");

            var albums = new AlbumsDAO();
         //  albums.create(1979,"The Wall",1);

            var album_genres = new AlbumsGenresAssociation();
           // album_genres.create(1, 1);
            albums.getGenres(1);

            Database.getConnection().commit();
            DatabaseViewer.viewAllArtists();
            DatabaseViewer.viewAllGenres();
            DatabaseViewer.viewAllAlbums();
            DatabaseViewer.viewAllAlbumGenres();
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
