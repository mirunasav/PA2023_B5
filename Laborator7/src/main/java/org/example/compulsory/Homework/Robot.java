package org.example.compulsory.Homework;

import javafx.util.Pair;

import java.util.*;

public class Robot implements Runnable {
    private String name;
    private volatile boolean running;
    private volatile boolean paused = false;
    private volatile boolean stopped = false;

    private Cell[][] matrix;
    private Stack<int[]> positionHistory;

    private Set <int[]> deadEndPositions;

    private int n;
    Exploration explore;
    static final int[] dx = {1, -1, 0, 0};
    static final int[] dy = {0, 0, 1, -1};

    @Override
    public void run() {

        matrix = this.explore.getMap().matrix;
        this.systematicRun();

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

    private Pair<Integer, Integer> generateNextAvailableCoordinates(int currentX, int currentY){
        List<Pair<Integer, Integer>> listOfPositions = new LinkedList<>();
       //daca pot sa merg in sus, merg in sus
        if(this.canGoTo(currentX, currentY+1))
          listOfPositions.add(new Pair<>(currentX, currentY+1));

        //jos
        if(this.canGoTo(currentX, currentY-1))
           listOfPositions.add( new Pair<>(currentX, currentY-1));

        //dreapta
        if(this.canGoTo(currentX+1, currentY))
            listOfPositions.add( new Pair<>(currentX+1, currentY));

        //stanga
        if(this.canGoTo(currentX-1, currentY))
            listOfPositions.add( new Pair<>(currentX-1, currentY));

        //acum am in lista toate pozitiile valabile; aleg una random

        if(listOfPositions.size()>0){
            Random rand = new Random();
            int index = rand.nextInt(listOfPositions.size());//aleg nr de la 0 la size, exclusiv
            return listOfPositions.get(index);
        }
        return null;
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
        if (!canGoTo(currentX, currentY + 1) && !canGoTo(currentX, currentY - 1) && !canGoTo(currentX - 1, currentY) && !canGoTo(currentX + 1, currentY))
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
                if (!explore.getMap().hasCellsLeft()) {
                    this.running = false;
                    System.out.println("Robotul " + this.name + " s-a oprit pentru ca s-a verificat toata matricea");
                } else {
                    //daca mai sunt casute de vizitat vad daca pe asta o vizitez sau nu
                    explore.getMap().visit(currentX, currentY, this);
                    //vad daca mai am unde sa merg
                    if(this.hasNowhereToGo(currentX, currentY)) {
                        this.running = false;
                        System.out.println("Robotul " + this.name + " s-a oprit pentru ca s-a blocat");
                    }
                    else{
                        //mai am unde sa merg deci generez urmatoarea pozitie
                        pair = this.generateNextAvailableCoordinates(currentX, currentY);
                        currentX = pair.getKey();
                         currentY = pair.getValue();
                    }
                }
                try {
                    Thread.sleep(0);
                } catch (Exception exception) {
                    System.out.println(exception);
                }


            }
            if (stopped) break;
        }
    }

    public void systematicRun() {
        this.positionHistory = new Stack<>();
        this.deadEndPositions = new HashSet<>();
        int numberVisited = this.getExplore().getMap().totalVisitedCells;

        this.running = true;
        Pair<Integer, Integer> pair = this.generateRandomCoordinates(this.explore.getMap().getTokensToExtract());
        int currentX = pair.getKey();
        int currentY = pair.getValue();

        while (running) {
            if (!paused) {
                //aici vizitez casuta, sau cel putin incerc. macar trec pe acolo
                if (!explore.getMap().hasCellsLeft()) {
                    this.running = false;
                    System.out.println("Robotul " + this.name + " s-a oprit pentru ca s-a verificat toata matricea");
                } else {
                    //daca mai sunt casute de vizitat vad daca pe asta o vizitez sau nu
                    if (explore.getMap().visit(currentX, currentY, this)) {
                        positionHistory.push(new int[]{currentX, currentY});
                    }
                    /*vad daca mai am unde sa merg de aici;*/
                    if (this.hasNowhereToGo(currentX, currentY)) {
                        this.deadEndPositions.add(new int [] {currentX, currentY});
                        //in loc sa il oprim, mergem la ultima pozitie in care totul era ok
                        do{
                            int[] coordinates = positionHistory.pop();
                            currentY = coordinates[1];
                            currentX = coordinates[0];
                        }
                        //cat timp primesc pozitii din astea dead end, tot sap
                        while(deadEndPositions.contains(new int[] {currentX, currentY}));
                        this.explore.getMap().unvisit(currentX, currentY, this);
                        continue;
                    } else {
                        //mai am unde sa merg deci generez urmatoarea pozitie
                        pair = this.generateNextAvailableCoordinates(currentX, currentY);
                        currentX = pair.getKey();
                        currentY = pair.getValue();
                    }
                }
                try {
                    //  Thread.sleep(10);
                } catch (Exception exception) {
                    System.out.println(exception);
                }

            }
            if (stopped) break;
        }
    }



    public boolean validPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < this.matrix.length - 1 && y < this.matrix.length - 1;
    }

}
