package Entities;

import Repositories.AbstractRepository;
import Repositories.ArtistRepository;
import jakarta.persistence.*;

import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = "AlbumsEntity.findByName",
        query = "Select a from AlbumsEntity a where a.name = :name"),
})
@Entity
@Table(name = "albums", schema = "public", catalog = "postgres")
public class AlbumsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "release_year")
    private Integer releaseYear;
    @Basic
    @Column(name = "title")
    private String name;

    @OneToOne(targetEntity = ArtistsEntity.class)
    @JoinColumn(name = "artist", referencedColumnName = "id")
    private ArtistsEntity artist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public Integer getArtist() {
        return this.artist.getId();
    }

    public void setArtist(Integer artistID) throws IllegalArgumentException{
        ArtistRepository  artistRepository = new ArtistRepository();
        try{
            this.artist = artistRepository.findById(artistID);
        }
        catch (Exception e){
            AbstractRepository.closeEntityManager();
            throw new IllegalArgumentException("Artist with this id does not exist");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumsEntity that = (AlbumsEntity) o;
        return id == that.id && Objects.equals(releaseYear, that.releaseYear) && Objects.equals(name, that.name) && Objects.equals(artist, that.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, releaseYear, name, artist);
    }
}
