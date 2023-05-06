import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * the singleton responsible for managing the entitymanagerfactory
 */
public class PersistenceManager {
    private static PersistenceManager instance = null ;
    protected EntityManagerFactory entityManagerFactory;

    public static PersistenceManager getInstance(){
        if(instance==null)
            instance = new PersistenceManager();
        return instance;
    }

    private PersistenceManager(){
    }

    public EntityManagerFactory getEntityManagerFactory(){
        if(entityManagerFactory == null)
            createEntityManagerFactory();
        return entityManagerFactory;
    }

    protected void createEntityManagerFactory(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory("Laborator9PU");
    }

    public void closeEntityManagerFactory(){
        if(entityManagerFactory!=null){
            entityManagerFactory.close();
            entityManagerFactory = null;
            System.out.println("closed entity manager factory");
        }
    }
}
