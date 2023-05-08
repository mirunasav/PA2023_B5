import Entities.ArtistsEntity;
import Entities.GenresEntity;
import Repositories.AbstractRepository;
import Repositories.AlbumRepository;
import Repositories.ArtistRepository;
import Repositories.GenreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class testJPA {
    public static void test(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        ArtistsEntity a = (ArtistsEntity) em.createQuery("select e from ArtistsEntity e where e.name = 'Nirvana'").getSingleResult();
        a.setName("Nirvana");
        em.getTransaction().commit();
        System.out.println(a.getName());
        em.close();
        emf.close();
    }
    public static void testAlbumCreate(){
        AlbumRepository albumRepository = new AlbumRepository();
        albumRepository.createAlbum(2002, "titlu", 12);
    }
    public static void testAlbumFindByID(int ID) throws Exception {
        AlbumRepository albumRepository = new AlbumRepository();
        albumRepository.findById(ID);
    }
    public static void testAlbumFindByName(String name) throws Exception {
        AlbumRepository albumRepository = new AlbumRepository();
        albumRepository.findByName(name);
    }
    public static void testArtistFindByID(int ID) throws Exception {
        ArtistRepository artistRepository = new ArtistRepository();
        artistRepository.findById(ID);
    }

    public static void testArtistFindByName(String name) throws Exception{
        ArtistRepository artistRepository = new ArtistRepository();
        artistRepository.findByName(name);
    }
    public static void testGenreFindByID(int ID) throws Exception {
        GenreRepository genreRepository = new GenreRepository();
        genreRepository.findById(ID);
    }

    public static void testGenreFindByName(String name) throws Exception{
        GenreRepository genreRepository = new GenreRepository();
        genreRepository.findByName(name);
    }
    public static void testDatabaseArtistInsertion(int numberOfArtists){
        DatabaseInsertionTool.insertArtists(numberOfArtists);
    }
    public static void testAddArtist(String name){
        ArtistRepository artistRepository = new ArtistRepository();
        artistRepository.createArtist(name);
    }
    public static void testDatabaseAlbumInsertion (int numberOfAlbums){
        DatabaseInsertionTool.insertAlbums(numberOfAlbums);
    }

    public static void testDatabaseAlbumInsertionTime(int numberOfAlbums){
        long begin = System.nanoTime();
        DatabaseInsertionTool.insertAlbums(numberOfAlbums);
        long end = System.nanoTime();

        long timeInNanoseconds = end - begin;
        double timeInSeconds = (double) timeInNanoseconds / 1_000_000_000.0;

        System.out.println("time in nanoseconds : " + timeInNanoseconds);
        System.out.println("time in seconds : " + timeInSeconds);
    }
    public static void main(String[] args) {

        try{
          // testAlbumCreate();
           testAlbumFindByID(1);
           testAlbumFindByName("Faith");
           //testAlbumFindByID(1000); -> exemplu care arunca exceptie
            testArtistFindByID(1);
            testArtistFindByName("Nirvana");
            testGenreFindByID(1);
            testGenreFindByName("Rock");
          //  testDatabaseArtistInsertion(2);
            //testDatabaseAlbumInsertion(2);

            testDatabaseAlbumInsertionTime(20);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            AbstractRepository.closeEntityManager();
        }
    }
}
