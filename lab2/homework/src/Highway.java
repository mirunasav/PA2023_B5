public class Highway extends Road {
    public Highway( Float length, Integer speedLimit, Location[] startEndLocations) {
        super(RoadType.HIGHWAY, length, speedLimit, startEndLocations);
    }
}
