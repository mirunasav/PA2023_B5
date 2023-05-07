import Entities.ArtistsEntity;
import Repositories.AlbumRepository;
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
        albumRepository.createAlbum(2002,"Ceva titlu", 1);
    }
    public static void testAlbumFindByID(int ID) throws Exception {
        AlbumRepository albumRepository = new AlbumRepository();
        albumRepository.findById(ID);
    }
    public static void testAlbumFindByName() throws Exception {
        AlbumRepository albumRepository = new AlbumRepository();
        albumRepository.findByTitle("Faith");
    }
    public static void main(String[] args) {

        try{
            testAlbumFindByID(1);
            testAlbumFindByName();
            testAlbumFindByID(1000);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
