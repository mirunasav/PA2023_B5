//Savin Miruna A5

import java.util.Arrays;
import java.util.Objects;

import static java.lang.System.exit;

public abstract class Road {
    private PairOfLengthAndSpeedLimit pairOfLengthAndSpeedLimit;
    private RoadType type;
    private Float length;
    private Integer speedLimit;
    private Location[] startEndLocations;


    public PairOfLengthAndSpeedLimit getPairOfLengthAndSpeedLimit() {
        return pairOfLengthAndSpeedLimit;
    }

    public void setPairOfLengthAndSpeedLimit(PairOfLengthAndSpeedLimit pairOfLengthAndSpeedLimit) {
        this.pairOfLengthAndSpeedLimit = pairOfLengthAndSpeedLimit;
    }

    public Road(RoadType type, Float length, Integer speedLimit, Location[] startEndLocations) {

        this.type = type;
        this.length = length;
        this.speedLimit = speedLimit;
        this.startEndLocations = startEndLocations;
        this.pairOfLengthAndSpeedLimit = new PairOfLengthAndSpeedLimit(this.length, this.speedLimit);

        /*
         * mai intai verific daca length e ok, si doar in acest caz continui
         * altfel nu creez strada si arunc exceptie
         */
        try {
            this.checkLengthValidity(startEndLocations);
        } catch (InvalidArgumentsException e) {
            System.out.println(e.toString());
            exit(1);
        }

    }

    /**
     * Functia care verifica validitatea argumentelor
     */
    private void checkLengthValidity(Location[] startEndLocations) throws InvalidArgumentsException {
        double minimumLength;
        int firstX = startEndLocations[0].getCoordinates()[0];
        int secondX = startEndLocations[1].getCoordinates()[0];
        int firstY = startEndLocations[0].getCoordinates()[1];
        int secondY = startEndLocations[1].getCoordinates()[1];

        minimumLength = Math.sqrt(Math.pow(firstX - secondX, 2) + Math.pow(firstY - secondY, 2));

        if (this.length < minimumLength)
            throw new InvalidArgumentsException("Eroare! Lungimea strazii dintre " +
                    startEndLocations[0].getName() + " si " + startEndLocations[1].getName()
                    + " este prea mica!\n");
    }

    public Location[] getStartEndLocations() {
        return startEndLocations;
    }

    public void setStartEndLocations(Location[] startEndLocations) {
        this.startEndLocations = startEndLocations;
    }


    public RoadType getType() {
        return type;
    }

    public void setType(RoadType type) {
        this.type = type;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Integer getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit) {
        this.speedLimit = speedLimit;
    }

    @Override
    public String toString() {
        return "Road{" +
                "type=" + type +
                ", length=" + length +
                ", speedLimit=" + speedLimit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Road)) return false;
        Road road = (Road) o;
        return getType() == road.getType() && getLength().equals(road.getLength()) && getSpeedLimit().equals(road.getSpeedLimit()) && Arrays.equals(getStartEndLocations(), road.getStartEndLocations());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getType(), getLength(), getSpeedLimit());
        result = 31 * result + Arrays.hashCode(getStartEndLocations());
        return result;
    }
}
