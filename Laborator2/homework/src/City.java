//Savin Miruna A5
public class City extends Location{
    private int population;

    public City(String name,  Integer[] coordinates, int population) {
        super(name, LocationType.CITY, coordinates);
        this.population = population;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
