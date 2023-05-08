import Repositories.AlbumRepository;
import Repositories.ArtistRepository;
import com.github.javafaker.Faker;

import java.util.stream.IntStream;

public class DatabaseInsertionTool {
    //I will use Faker to insert fake artists & genres
    public static void insertArtists(int numberOfArtists) {
        Faker faker = new Faker();
        ArtistRepository artistRepository = new ArtistRepository();
        IntStream.range(0, numberOfArtists).forEach(i ->
                artistRepository.createArtist(faker.name().fullName()));
    }

    //creeaza un album nou; in faker, daca incercam sa geneez o data ca sa extrag un an, imi genera
    //un Date care e deprecated asa ca am facut sa genereze un nr random si apoi adaug 1900
    public static void insertAlbums(int numberOfAlbums) {
        Faker faker = new Faker();
        AlbumRepository albumRepository = new AlbumRepository();
        IntStream.range(0, numberOfAlbums).forEach(i -> {
                    boolean foundValidArtistID = false;
                    while (!foundValidArtistID) {
                        try {
                            albumRepository.createAlbum(1900 + faker.number().numberBetween(0, 100), faker.pokemon().name(), faker.number().numberBetween(1, 498));
                            foundValidArtistID = true;
                        } catch (IllegalArgumentException e) {

                        }
                    }
                }
        );
    }
}
