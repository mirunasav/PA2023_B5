package homework;

public class AlbumGenreAssociation {
    int albumID;
    int genreID;

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public int getGenreID() {
        return genreID;
    }

    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }

    public AlbumGenreAssociation(int albumID, int genreID) {
        this.albumID = albumID;
        this.genreID = genreID;
    }
}
