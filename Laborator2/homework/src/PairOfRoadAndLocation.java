/**
 * perechi de drum si locatia in care duce
 */
public class PairOfRoadAndLocation {
    private PairOfLengthAndSpeedLimit pairOfLengthAndSpeedLimit;
    private Location location;

    public PairOfRoadAndLocation(PairOfLengthAndSpeedLimit pairOfLengthAndSpeedLimit, Location location) {
        this.pairOfLengthAndSpeedLimit = pairOfLengthAndSpeedLimit;
        this.location = location;
    }
    public PairOfLengthAndSpeedLimit getPairOfLengthAndSpeedLimit() {
        return pairOfLengthAndSpeedLimit;
    }

    public void setPairOfLengthAndSpeedLimit(PairOfLengthAndSpeedLimit pairOfLengthAndSpeedLimit) {
        this.pairOfLengthAndSpeedLimit = pairOfLengthAndSpeedLimit;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
