package Repositories;

import Entities.AlbumsEntity;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AlbumRepository extends AbstractRepository<AlbumsEntity> {
    //private EntityManager em;

    public AlbumRepository() {
    }

    public void createAlbum(int releaseYear, String title, int artistID) throws IllegalArgumentException {
        AbstractRepository.createEntityManager();
        while (true) {
            if (!em.getTransaction().isActive()) {

                em.getTransaction().begin();

                AlbumsEntity album = new AlbumsEntity();
                album.setArtist(artistID);
                album.setName(title);
                album.setReleaseYear(releaseYear);

                em.persist(album);
                em.getTransaction().commit();
                break;
            } else {
                System.out.println("something");
            }
        }


    }

    @Override
    protected AlbumsEntity findByIdImplementation(int ID) throws Exception {
        AlbumsEntity albumsEntity = em.find(AlbumsEntity.class, ID);
        if (albumsEntity == null)
            throw new Exception("Album with this id does not exist");
        System.out.println("id: " + albumsEntity.getId());
        System.out.println("year: " + albumsEntity.getReleaseYear());
        System.out.println("title: " + albumsEntity.getName());
        System.out.println("artist: " + albumsEntity.getArtist());
        return albumsEntity;
    }

    @Override
    protected List<AlbumsEntity> findByNameImplementation(String name) throws Exception {
        TypedQuery<AlbumsEntity> query = em.createNamedQuery("AlbumsEntity.findByName", AlbumsEntity.class).setParameter("name", name);
        List<AlbumsEntity> results = query.getResultList();
        if (results.size() == 0)
            throw new Exception("Album with this title does not exists");
        results.forEach(result -> System.out.println(result.getName()));
        return results;
    }

}
