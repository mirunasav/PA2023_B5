package Repositories;

import Entities.ArtistsEntity;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ArtistRepository extends AbstractRepository<ArtistsEntity>{

    public void createArtist(String name){
        em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        ArtistsEntity artist = new ArtistsEntity();
        artist.setName(name);

        em.persist( artist );
        em.getTransaction( ).commit();
        em.close();
        PersistenceManager.getInstance().closeEntityManagerFactory();
    }
    @Override
    protected ArtistsEntity findByIdImplementation(int ID) throws Exception {
        ArtistsEntity artistsEntity = em.find(ArtistsEntity.class,ID);
        if(artistsEntity == null)
            throw new Exception("Artist with this id does not exist");
        System.out.println("id: " + artistsEntity.getId() );
        System.out.println("name: " + artistsEntity.getName() );
        return artistsEntity;
    }

    @Override
    protected List<ArtistsEntity> findByNameImplementation(String name) throws Exception {
        TypedQuery<ArtistsEntity> query = em.createNamedQuery("ArtistsEntity.findByName", ArtistsEntity.class).setParameter("name",name);
        List<ArtistsEntity> results = query.getResultList();
        if(results.size() == 0)
            throw new Exception("Artist with this name does not exists");
        results.forEach(result -> System.out.println(result.getName()));
        return results;
    }
}
