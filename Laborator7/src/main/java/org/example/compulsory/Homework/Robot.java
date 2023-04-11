package org.example.compulsory.Homework;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class Robot implements Runnable {
    private String name;
    private volatile boolean running;
    private volatile boolean paused = false;
    private volatile boolean stopped = false;

    private Cell[][] matrix;
    private Stack<int[]> positionHistory;

    private int n;
    Exploration explore;
    static final int[] dx = {1, -1, 0, 0};
    static final int[] dy = {0, 0, 1, -1};

    @Override
    public void run() {

        matrix = this.explore.getMap().matrix;
        this.randomRun();

    }

    public void pauseExecution() {
        paused = true;
        System.out.println("robotul " + this.name + " este pe pauza");
    }

    public void resumeExecution() {
        paused = false;
        System.out.println("robotul " + this.name + " nu mai este pe pauza");
    }

    public void stopExecution() {
        stopped = true;
        System.out.println("robotul " + this.name + " este oprit");
    }

    private Pair<Integer, Integer> generateRandomCoordinates(int n) {
        int x, y;
        Random rand = new Random();

        x = rand.nextInt(n);
        y = rand.nextInt(n);
        //ma asigur ca nu am generat o pozitie pe care se afla deja un robot
        while (this.explore.getMap().getMatrix()[x][y].isVisited()) {
            x = rand.nextInt(n);
            y = rand.nextInt(n);
        }

        return new Pair<>(x, y);
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

    /**
     * @param currentX pozitia pe x
     * @param currentY pozitia pe y
     * @return true daca s a blocat
     */
    private boolean hasNowhereToGo(int currentX, int currentY) {
        int size = this.explore.getMap().getMatrix().length;
        //verific daca poate merge in sus :y+1,x
        //verific daca poate merge in jos :y-1,x
        //verific daca poate merge in stanga :x-1,y
        //verific daca poate merge in dreapta :x+1,y
        //verific si daca exista acesti indecsi
        if (!canGoTo(currentX, currentY + 1)
                && !canGoTo(currentX, currentY - 1)
                && !canGoTo(currentX - 1, currentY)
                && !canGoTo(currentX + 1, currentY))
            return true;
        return false;
    }

    private boolean canGoTo(int targetX, int targetY) {
        int size = this.explore.getMap().getMatrix().length;
        //verific daca e o pozitie valida
        if ((targetX >= 0 && targetX <= size - 1) && (targetY >= 0 && targetY <= size - 1)) {
            //verific daca e vizitata celula
            return !this.explore.getMap().getMatrix()[targetX][targetY].isVisited();
        }
        return false;
    }

    public void randomRun() {
        this.running = true;
        //genereaza coordonate random pana la n(tokens to extract = n)
        Pair<Integer, Integer> pair = this.generateRandomCoordinates(this.explore.getMap().getTokensToExtract());
        int currentX = pair.getKey();
        int currentY = pair.getValue();
        while (running) {
            if (!paused) {
                if (!explore.getMap().visit(pair.getKey(), pair.getValue(), this)) {
                    this.running = false;
                    System.out.println("Robotul " + this.name + " s-a oprit pentru ca s-a verificat toata matricea");
                } else {
                    if (this.hasNowhereToGo(currentX, currentY)) {
                        this.running = false;
                        System.out.println("Robotul " + this.name + " s-a oprit pentru ca s-a blocat");
                    }
                }

                try {
                    Thread.sleep(0);
                } catch (Exception exception) {
                    System.out.println(exception);
                }
                pair = this.generateRandomCoordinates(4);
                currentY = currentY + dy[pair.getValue()];
                currentX = currentX + dx[pair.getKey()];
            }
            if (stopped)
                break;
        }
    }

    public void systematicRun() {
        this.positionHistory = new Stack<>();
        int numberVisited = this.getExplore().getMap().totalVisitedCells;

        this.running = true;
        Pair<Integer, Integer> pair = this.generateRandomCoordinates(this.explore.getMap().getTokensToExtract());
        int currentX = pair.getKey();
        int currentY = pair.getValue();
        while (running) {
            if (!paused) {
                //aici vizitez casuta, sau cel putin incerc. macar trec pe acolo
                if (!explore.getMap().visit(pair.getKey(), pair.getValue(), this)) {
                    this.running = false;
                    System.out.println("Robotul " + this.name + " s-a oprit pentru ca s-a verificat toata matricea");
                }
                else {//daca e o pozitie dead end
                    if (this.hasNowhereToGo(currentX, currentY)) {
                        //in loc sa il oprim, mergem la ultima pozitie in care totul era ok
                        int[] coordinates = positionHistory.pop();
                        currentY = coordinates[1];
                        currentX = coordinates[0];
                        this.explore.getMap().unvisit(currentX,currentY, this);
                    }

                }
                try {
                  //  Thread.sleep(10);
                } catch (Exception exception) {
                    System.out.println(exception);
                }
                do {
                    pair = this.generateRandomCoordinates(matrix.length-1);
                    currentY = currentY + dy[pair.getValue()];
                    currentX = currentX + dx[pair.getKey()];
                }
                while(!validPosition(currentX, currentY));
                positionHistory.push(new int[]{currentX,currentY});
            }
            if (stopped)
                break;
        }
    }


    public void dfs(int row, int col) {

    }

    public boolean validPosition(int x, int y){
        return x >= 0 && y >= 0 && x < this.matrix.length - 1 && y < this.matrix.length - 1;
    }

}
