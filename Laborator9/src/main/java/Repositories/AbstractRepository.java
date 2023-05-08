package Repositories;

import Entities.AlbumsEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

//t = type of entity
public abstract class AbstractRepository<T> {
    protected static EntityManager em;

    public T findById(int ID) throws Exception {
        createEntityManager();
            return this.findByIdImplementation(ID);
    }

    protected abstract T findByIdImplementation(int ID) throws Exception;

    public List<T> findByName(String name) throws Exception {
       createEntityManager();
            return this.findByNameImplementation(name);
    }

    protected abstract List<T> findByNameImplementation(String name) throws Exception;

    protected static void createEntityManager() {
        if(em== null)
            em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
    }

    public static void closeEntityManager() {
        em.close();
        PersistenceManager.getInstance().closeEntityManagerFactory();
    }

}
