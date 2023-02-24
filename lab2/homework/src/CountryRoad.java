public class CountryRoad extends Road{
    public CountryRoad( Float length, Integer speedLimit, Location[] startEndLocations) {
        super(RoadType.COUNTRY, length, speedLimit, startEndLocations);
    }
}
