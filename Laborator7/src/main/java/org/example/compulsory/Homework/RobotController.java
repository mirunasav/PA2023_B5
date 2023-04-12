package org.example.compulsory.Homework;

import org.example.compulsory.Exploration;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.exit;

public class RobotController {
    List<Robot> listOfSlaves;

    public int numberOfStoppedRobots;

    public boolean running = true;

    public RobotController(List<Robot> listOfSlaves) {
        this.listOfSlaves = listOfSlaves;
        numberOfStoppedRobots = 0;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (running) {
            synchronized (System.out) {
                System.out.println("Enter a command: ");
            }
            String command = scanner.nextLine();
            this.assertCommand(command);
        }

        System.out.print("Robot controller s-a oprit pentru ca ");
        if (this.numberOfStoppedRobots == this.listOfSlaves.size())
            System.out.println(" s-au oprit toti robotii");
        else System.out.println(" s-a terminat de vizitat matricea");

        for (Robot robot : listOfSlaves) {
            System.out.println("robotul " + robot.getName() + " a plasat " + robot.getNumberOfTokensPlaced() + " tokeni");
            robot.stopExecution();
        }

    }

    public void stop(){
        for (Robot robot : listOfSlaves) {
            System.out.println("robotul " + robot.getName() + " a plasat " + robot.getNumberOfTokensPlaced() + " tokeni");
            robot.stopExecution();
        }
    }
    private void assertCommand(String command) {

        String targetedRobot = this.getRobotName(command);
        String typeOfCommand = this.getTypeOfCommand(command);

        //find targeted robot and apply command

        switch (typeOfCommand) {
            case "pause" -> {
                if (this.findRobot(targetedRobot) != null) {
                    Objects.requireNonNull(this.findRobot(targetedRobot)).pauseExecution();
                }
            }
            case "resume" -> {
                if (this.findRobot(targetedRobot) != null) {
                    Objects.requireNonNull(this.findRobot(targetedRobot)).resumeExecution();
                }
            }
            case "stop" -> {
                if (this.findRobot(targetedRobot) != null) {
                    Objects.requireNonNull(this.findRobot(targetedRobot)).stopExecution();
                    this.numberOfStoppedRobots++;
                    if (numberOfStoppedRobots == this.listOfSlaves.size())
                        this.running = false;
                }
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

    private boolean isCommandValid(String command) {
        String[] words = command.split(" ");
        if (words.length != 2)
            return false;
        return true;
    }

}
