package Repositories;


import Entities.AlbumsEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.eclipse.persistence.jpa.config.PersistenceUnitMetadata;

import java.util.List;

public class AlbumRepository  {
    private EntityManager em;

    public AlbumRepository(){
    }
    public void createAlbum (int releaseYear, String title,int artistID){
        em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        AlbumsEntity album = new AlbumsEntity();
        album.setArtist(artistID);
        album.setTitle(title);
        album.setReleaseYear(releaseYear);

        em.persist( album );
        em.getTransaction( ).commit();
        em.close();
        PersistenceManager.getInstance().closeEntityManagerFactory();
    }

    public AlbumsEntity findById(int ID) throws Exception {
        em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        AlbumsEntity albumsEntity = em.find(AlbumsEntity.class,ID);
        if(albumsEntity == null)
            throw new Exception("Album with this id does not exist");
        System.out.println("id: " + albumsEntity.getId() );
        System.out.println("year: " + albumsEntity.getReleaseYear() );
        System.out.println("title: " + albumsEntity.getTitle() );
        System.out.println("artist: " + albumsEntity.getArtist() );
        em.close();
        PersistenceManager.getInstance().closeEntityManagerFactory();
        return albumsEntity;
    }
    public void findByTitle(String title) throws Exception {
        em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        TypedQuery<AlbumsEntity> query = em.createNamedQuery("AlbumsEntity.findByName", AlbumsEntity.class).setParameter("name",title);
        List<AlbumsEntity> results = query.getResultList();
        if(results.size() == 0)
            throw new Exception("Album with this title does not exists");
        results.forEach(result -> System.out.println(result.getTitle()));
    }
}
