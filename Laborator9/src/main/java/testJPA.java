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
        AbstractRepository.createAlbum(2002, "titlu", 12);
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
    public static void testAddArtist(){
        ArtistRepository artistRepository = new ArtistRepository();
        artistRepository.createArtist("Eu");
    }
    public static void main(String[] args) {

        try{
        //    testAlbumCreate();
            testAlbumFindByID(1);
            testAlbumFindByName("Faith");
            //testAlbumFindByID(1000); -> exemplu care arunca exceptie
            testArtistFindByID(1);
            testArtistFindByName("Nirvana");
            testGenreFindByID(1);
            testGenreFindByName("Rock");

            //testDatabaseArtistInsertion(1);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
