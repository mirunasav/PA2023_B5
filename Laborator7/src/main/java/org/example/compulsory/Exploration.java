package org.example.compulsory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Exploration {
    private final SharedMemory memory;

    private final ExplorationMap map;
    private final List<Robot> listOfSlaves;
    private final int numberOfRobots;

    public Exploration(int n, int numberOfRobots) {
        this.memory = new SharedMemory(n);
        this.map = new ExplorationMap(n, memory);
        this.numberOfRobots = numberOfRobots;
        this.listOfSlaves = Arrays.asList(IntStream.range(0, numberOfRobots)
                .mapToObj(i -> new Robot("Robot" + i, false, this))
                .toArray(Robot[]::new));
        this.start();
    }

    public void start() {
        for (Robot robot : listOfSlaves) {
            //create new thread representing the robot
            Thread thread = new Thread(robot);
            thread.start();
            System.out.println("threadul pentru robotul  " + robot.getName() + " a inceput!");
        }
    }

    public SharedMemory getMemory() {
        return memory;
    }

    public ExplorationMap getMap() {
        return map;
    }

    public List<Robot> getListOfSlaves() {
        return listOfSlaves;
    }
}
