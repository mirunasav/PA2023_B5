package Repositories;

import Entities.AlbumsEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

//t = type of entity
public abstract class AbstractRepository<T> {
    protected static EntityManager em;

    public T findById(int ID) throws Exception {
        createEntityManager();
        try {
            return this.findByIdImplementation(ID);
        } finally {
            closeEntityManager();
        }
    }

   /* public static void createAlbum (int releaseYear, String title,int artistID){
        createEntityManager();
        em.getTransaction().begin();

        AlbumsEntity album = new AlbumsEntity();
        album.setArtist(artistID);
        album.setName(title);
        album.setReleaseYear(releaseYear);

        em.persist( album );
        em.getTransaction( ).commit();
        closeEntityManager();
    }*/

    protected abstract T findByIdImplementation(int ID) throws Exception;

    public List<T> findByName(String name) throws Exception {
       createEntityManager();
        try {
            return this.findByNameImplementation(name);
        } finally {
           closeEntityManager();
        }
    }

    protected abstract List<T> findByNameImplementation(String name) throws Exception;

    protected static void createEntityManager() {
        em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
    }

    protected static void closeEntityManager() {
        em.close();
        PersistenceManager.getInstance().closeEntityManagerFactory();
    }

}
