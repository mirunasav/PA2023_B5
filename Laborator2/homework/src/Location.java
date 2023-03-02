//Savin Miruna A5
import java.util.Arrays;
import java.util.Objects;

public abstract class Location {
    private String name;
    private LocationType type;
    private Integer [] coordinates;

    public Location(String name, LocationType type, Integer[] coordinates) {
        this.name = name;
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public Integer[] getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Integer[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return getName().equals(location.getName()) && getType() == location.getType() && Arrays.equals(getCoordinates(), location.getCoordinates());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getName(), getType());
        result = 31 * result + Arrays.hashCode(getCoordinates());
        return result;
    }
}
