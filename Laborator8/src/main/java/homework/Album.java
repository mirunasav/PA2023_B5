package homework;

public class Album implements IEntry{

    private Integer ID;
    private Integer releaseYear;
    private String title;
    private Integer artistID;
    @Override
    public void create() {

    }

    public void print(){
        System.out.println("Albumul are id: " + this.getID() + ", release Year: " +releaseYear +
                ", titlu: \"" + this.title + "\" si artistul cu id " + this.artistID);
    }
    public Album( Integer releaseYear, String title, Integer artistID) {
        this.ID = ID;
        this.releaseYear = releaseYear;
        this.title = title;
        this.artistID = artistID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getArtistID() {
        return artistID;
    }

    public void setArtistID(Integer artistID) {
        this.artistID = artistID;
    }
}
