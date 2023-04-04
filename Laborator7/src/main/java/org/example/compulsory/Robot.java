package org.example.compulsory;

import javafx.util.Pair;

import java.util.Random;

public class Robot implements Runnable {
    private String name;
    private boolean running;
    Exploration explore;
    static final int[] dx = {1,-1,0,0};
    static final int[] dy = {0,0,1,-1};
    @Override
    public void run() {

        this.running = true;
        Pair<Integer, Integer> pair = this.generateRandomCoordinates(this.explore.getMap().getTokensToExtract());
        int currentX = pair.getKey();
        int currentY = pair.getValue();
        while(running){
            //generate random row and column
            if( !explore.getMap().visit(pair.getKey(), pair.getValue(), this))
            {
                this.running= false;
                System.out.println("Robotul " + this.name + " s-a oprit pentru ca s-a verificat toata matricea");
            }
//            try
//            {
//                Thread.sleep(1000);
//            }
//
//            catch (Exception exception){
//                System.out.println(exception);
//            }
            pair = this.generateRandomCoordinates(4);
            currentY = currentY + dy[pair.getValue()];
            currentX = currentX + dx[pair.getKey()];
        }
    }

    private Pair<Integer, Integer> generateRandomCoordinates(int n){
        int x, y;
        Random rand = new Random();

        x = rand.nextInt(n);
        y = rand.nextInt(n);

        return new Pair<>(x,y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Exploration getExplore() {
        return explore;
    }

    public void setExplore(Exploration explore) {
        this.explore = explore;
    }

    public Robot(String name, boolean running, Exploration explore) {
        this.name = name;
        this.running = running;
        this.explore = explore;
    }
}
