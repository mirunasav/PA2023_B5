public class Express extends Road{
    public Express( Float length, Integer speedLimit, Location[] startEndLocations) {
        super(RoadType.EXPRESS, length, speedLimit, startEndLocations);
    }
}
