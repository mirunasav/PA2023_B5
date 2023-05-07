package Repositories;

import Entities.ArtistsEntity;
import Entities.GenresEntity;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class GenreRepository extends AbstractRepository{
    public void createArtist(String name){
        em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        GenresEntity genresEntity = new GenresEntity();
        genresEntity.setName(name);

        em.persist( genresEntity );
        em.getTransaction( ).commit();
        em.close();
        PersistenceManager.getInstance().closeEntityManagerFactory();
    }
    @Override
    protected Object findByIdImplementation(int ID) throws Exception {
        GenresEntity genresEntity = em.find(GenresEntity.class,ID);
        if(genresEntity == null)
            throw new Exception("Genre with this id does not exist");
        System.out.println("id: " + genresEntity.getId() );
        System.out.println("name: " + genresEntity.getName() );
        return genresEntity;
    }

    @Override
    protected List findByNameImplementation(String name) throws Exception {
        TypedQuery<GenresEntity> query = em.createNamedQuery("GenresEntity.findByName", GenresEntity.class).setParameter("name",name);
        List<GenresEntity> results = query.getResultList();
        if(results.size() == 0)
            throw new Exception("Genre with this name does not exists");
        results.forEach(result -> System.out.println(result.getName()));
        return results;
    }
}
