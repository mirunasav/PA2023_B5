package org.example.compulsory;

import javafx.util.Pair;

import java.util.Random;

public class Robot implements Runnable {
    private String name;
    private volatile boolean running;
    private volatile boolean paused = false;
    private volatile boolean stopped = false;
    Exploration explore;
    static final int[] dx = {1, -1, 0, 0};
    static final int[] dy = {0, 0, 1, -1};

    @Override
    public void run() {
        this.randomRun();

    }

    public void pauseExecution(){
        paused = true;
        System.out.println("robotul "+this.name + " este pe pauza");
    }
    public void resumeExecution() {
        paused = false;
        System.out.println("robotul "+this.name + " nu mai este pe pauza");
    }
    public void stopExecution(){
        stopped = true;
        System.out.println("robotul "+this.name + " este oprit");
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
        if(!canGoTo(currentX, currentY+1)
        && !canGoTo(currentX, currentY-1)
        && !canGoTo(currentX-1, currentY)
        && !canGoTo(currentX+1, currentY))
            return true;
        return false;
    }

    private boolean canGoTo(int targetX, int targetY){
        int size = this.explore.getMap().getMatrix().length;
        //verific daca e o pozitie valida
        if((targetX >=0 && targetX <= size-1) && (targetY >=0 && targetY <= size-1)){
            //verific daca e vizitata celula
            return !this.explore.getMap().getMatrix()[targetX][targetY].isVisited();
        }
        return false;
    }

    public void randomRun(){
        this.running = true;
        //genereaza coordonate random pana la n(tokens to extract = n)
        Pair<Integer, Integer> pair = this.generateRandomCoordinates(this.explore.getMap().getTokensToExtract());
        int currentX = pair.getKey();
        int currentY = pair.getValue();
        while (running) {
            if(!paused){
                if (!explore.getMap().visit(pair.getKey(), pair.getValue(), this)) {
                    this.running = false;
                    System.out.println("Robotul " + this.name + " s-a oprit pentru ca s-a verificat toata matricea");
                } else {
                    if (this.hasNowhereToGo(currentX, currentY)){
                        this.running = false;
                        System.out.println("Robotul " + this.name + " s-a oprit pentru ca s-a blocat");
                    }
                }

                try{
                    Thread.sleep(10000);
                }
                catch(Exception exception){
                    System.out.println(exception);
                }
                pair = this.generateRandomCoordinates(4);
                currentY = currentY + dy[pair.getValue()];
                currentX = currentX + dx[pair.getKey()];
            }
            if(stopped)
                break;
        }
    }

    public void systematicRun(){
        //start all robots at 0,0
      int numberVisited = this.getExplore().getMap().totalVisitedCells;
    }

}
