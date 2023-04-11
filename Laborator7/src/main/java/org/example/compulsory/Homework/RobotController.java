package org.example.compulsory.Homework;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class RobotController {
    List<Robot> listOfSlaves;

    int numberOfStoppedRobots;

    public RobotController(List<Robot>listOfSlaves){
        this.listOfSlaves = listOfSlaves;
        numberOfStoppedRobots = 0;
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (numberOfStoppedRobots!= this.listOfSlaves.size()) {
            System.out.println("Enter a command: ");
            String command = scanner.nextLine();
            this.assertCommand(command);
        }
        System.out.println("s-au oprit toti robotii!");
    }

    private void assertCommand(String command) {
        String targetedRobot = this.getRobotName(command);
        String typeOfCommand = this.getTypeOfCommand(command);

        //find targeted robot and apply command

        switch (typeOfCommand) {
            case "pause" -> {
                Objects.requireNonNull(this.findRobot(targetedRobot)).pauseExecution();
            }
            case "resume" -> {
                Objects.requireNonNull(this.findRobot(targetedRobot)).resumeExecution();
            }
            case "stop" -> {
                Objects.requireNonNull(this.findRobot(targetedRobot)).stopExecution();
                numberOfStoppedRobots++;
            }
        }
    }

    private String getRobotName(String command) {
        //gasesc numele
        return command.split(" ")[1];
    }

    private String getTypeOfCommand(String command) {
        //gasesc numele
        return command.split(" ")[0];
    }

    private Robot findRobot(String name) {
        for (Robot robot : listOfSlaves)
            if (robot.getName().equals(name))
                return robot;
        return null;
    }

}
