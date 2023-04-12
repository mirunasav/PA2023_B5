package org.example.compulsory.Homework;

import javafx.util.Pair;

import java.util.*;

import static java.lang.System.exit;
import static java.lang.System.in;

public class Robot implements Runnable {
    private String name;
    private volatile boolean running;
    private volatile boolean paused = false;
    private volatile boolean stopped = false;

    private int numberOfTokensPlaced;

    private Cell[][] matrix;
    private Stack<int[]> positionHistory;

    private Set<int[]> deadEndPositions;

    private int n;
    Exploration explore;
    static final int[] dx = {1, -1, 0, 0};
    static final int[] dy = {0, 0, 1, -1};


    public int getNumberOfTokensPlaced() {
        return numberOfTokensPlaced;
    }

    public void setNumberOfTokensPlaced(int numberOfTokensPlaced) {
        this.numberOfTokensPlaced = numberOfTokensPlaced;
    }

    @Override
    public void run() {
        Exploration.threadCounter ++;
        System.out.println(this.name + " " + Exploration.threadCounter);
        matrix = this.explore.getMap().matrix;
        this.runAlgorithm();
        Exploration.threadCounter--;
        System.out.println(this.name + " " + Exploration.threadCounter);


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
        running = false;
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

    private List<int[]> generateNeighbourPositions(int currentX, int currentY) {
        //pozitiile in care pot merge

        List<int[]> listOfPositions = new LinkedList<>();
        //daca pot sa merg in sus, merg in sus
        if (this.canGoTo(currentX, currentY + 1))
            listOfPositions.add(new int[]{currentX, currentY + 1});

        //jos
        if (this.canGoTo(currentX, currentY - 1))
            listOfPositions.add(new int[]{currentX, currentY - 1});

        //dreapta
        if (this.canGoTo(currentX + 1, currentY))
            listOfPositions.add(new int[]{currentX + 1, currentY});

        //stanga
        if (this.canGoTo(currentX - 1, currentY))
            listOfPositions.add(new int[]{currentX - 1, currentY});

        return listOfPositions;
    }

    private Pair<Integer, Integer> generateNextAvailableCoordinates(int currentX, int currentY) {
        List<int[]> listOfPositions = this.generateNeighbourPositions(currentX, currentY);

        //acum am in lista toate pozitiile valabile; aleg una random
        if (listOfPositions.size() > 0) {
            Random rand = new Random();
            int index = rand.nextInt(listOfPositions.size());//aleg nr de la 0 la size, exclusiv
            int[] coordinates = listOfPositions.get(index);
            return new Pair<>(coordinates[0], coordinates[1]);
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
            if (!this.explore.getMap().getMatrix()[targetX][targetY].isVisited()) {
                return true;
            }
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
                    if (this.hasNowhereToGo(currentX, currentY)) {
                        this.running = false;
                        System.out.println("Robotul " + this.name + " s-a oprit pentru ca s-a blocat");
                    } else {
                        //mai am unde sa merg deci generez urmatoarea pozitie
                        pair = this.generateNextAvailableCoordinates(currentX, currentY);
                        currentX = pair.getKey();
                        currentY = pair.getValue();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception exception) {
                    System.out.println(exception);
                }


            }
            if (stopped) break;
        }
    }

   /* public void systematicRun() {
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
*/

    public void runAlgorithm() {
        //bun, refacem traseul pt ca altfel e cam stupid
        //o sa ii las sa calce in locurile pe care le au vizitat deja
        //si fiecare robot face un fel de dfs
        //adica fiecare merge as far as he can go, mentinand o stiva a ultimelor pozitii ale sale
        //practic fiecare face cate un dfs, dar in loc de lista de adiacenta a fiecarui nod
        //retin pozitiile unde pot sa merg
        //merg in ele, si daca sunt nevizitate, le si vizitez si plasez acolo tokeni.
        Pair<Integer, Integer> pair = this.generateRandomCoordinates(this.explore.getMap().getTokensToExtract());
        int currentX = pair.getKey();
        int currentY = pair.getValue();
        this.running = true;
        while (running) {
            if (!paused) {
                //adaug aceasta celula la setul de celule vizitate
                //aici o marchez ca vizitata, daca ea nu e deja vizitata
                //it shouldn't be visited tho
                recursiveRun(currentX, currentY);
                //mark all adjacent cells as unvisited
                System.out.println("robotul " + this.name + " a terminat de vizitat toate celulele accesibile lui");
                running = false;
                if (this.explore.getMap().totalVisitedCells == (this.explore.getMap().matrix.length) * (this.explore.getMap().matrix.length)) {
                    System.out.println("s-a terminat de vizitat matricea");
                    this.explore.robotController.stop();
                    Thread.currentThread().interrupt();
                }

            }
        }
    }

    public void recursiveRun(int currentX, int currentY) {
        if (running) {
            if (!paused) {
                this.explore.getMap().visit(currentX, currentY, this);

                Iterator<int[]> i = this.generateNeighbourPositions(currentX, currentY).listIterator();
                while (i.hasNext()) {
                    int[] next = i.next();
                    if (!this.explore.getMap().getMatrix()[next[0]][next[1]].isVisited()) {
                        try {
                            Thread.sleep(100);
                        } catch (Exception exception) {
                            System.out.println(exception);
                        }
                        recursiveRun(next[0], next[1]);
                    }
                }
            }

        }

    }


    public boolean validPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < this.matrix.length - 1 && y < this.matrix.length - 1;
    }

}
