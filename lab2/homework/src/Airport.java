public class Airport extends Location{

    private int numberOfTerminals;

    public int getNumberOfTerminals() {
        return numberOfTerminals;
    }

    public void setNumberOfTerminals(int numberOfTerminals) {
        this.numberOfTerminals = numberOfTerminals;
    }

    public Airport(String name, Integer[] coordinates, int numberOfTerminals) {
        super(name, LocationType.AIRPORT, coordinates);
        this.numberOfTerminals = numberOfTerminals;
    }
}
