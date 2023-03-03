/**
 * Clasa in care retin perechi de lungime / speedlimit pentru strazi
 */
public class PairOfLengthAndSpeedLimit {
    private Float length;
    private Integer speedLimit;

    public Float getLength() {
        return length;
    }

    public PairOfLengthAndSpeedLimit(Float length, Integer speedLimit) {
        this.length = length;
        this.speedLimit = speedLimit;
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
}
