import Entities.ArtistsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class testJPA {
    public static void test(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        ArtistsEntity artist = new ArtistsEntity("Nirvana");
        em.persist(artist);

        ArtistsEntity a = (ArtistsEntity) em.createQuery("select e from ArtistsEntity e where e.name = 'Nirvana'").getSingleResult();
        a.setName("Nirvana");
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
    public static void main(String[] args) {
        test();
    }
}
