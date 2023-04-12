package org.example.compulsory.Homework;

public class Timer extends Thread {

    private volatile boolean running = true;
    private final int timeLimitSeconds;
    private long startTimeMillis;
    private long totalElapsedSeconds;

    public static final Object lock = new Object();

    public Timer(int timeLimitSeconds) {
        this.timeLimitSeconds = timeLimitSeconds;
        this.setDaemon(true);
    }

    public void run() {
        startTimeMillis = System.currentTimeMillis();

        while (running && !Thread.currentThread().isInterrupted()) {
            long elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;
            totalElapsedSeconds = elapsedTimeMillis / 1000;

            if (totalElapsedSeconds >= timeLimitSeconds) {
                System.out.println("Exploration time limit exceeded!");
                stopExploration();
            }

            System.out.println("Elapsed time: " + totalElapsedSeconds + " seconds");
            if (Exploration.threadCounter == 0) {
                Thread.currentThread().interrupt();
            }
            try {
                Thread.sleep(1000); // wait for 1 second
            } catch (InterruptedException ignored) {
                System.out.println("au trecut " + totalElapsedSeconds + " secunde in total");
                running = false;
            }
        }
    }

    public void stopTimeKeeper() {
        running = false;
        interrupt();
    }

    public long getTotalElapsedSeconds() {
        return totalElapsedSeconds;
    }

    private void stopExploration() {
        // Stop the player threads
        // ...
        System.out.println("Time is up!");
        this.running = false;
        for (Robot robot : Exploration.listOfSlaves)
            robot.stopExecution();
        Thread.currentThread().interrupt();
    }
}


