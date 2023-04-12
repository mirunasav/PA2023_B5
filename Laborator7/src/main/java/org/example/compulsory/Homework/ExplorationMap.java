package org.example.compulsory.Homework;

import java.util.*;

public class ExplorationMap {
    public final Cell[][] matrix;
    private final SharedMemory memory;
    private int tokensToExtract;
    public int totalVisitedCells;
    public Set<int[]> visitedCells;

    public Stack<int[]> unvisitedCells;
    public Cell[][] getMatrix() {
        return matrix;
    }

    public SharedMemory getMemory() {
        return memory;
    }

    public int getTokensToExtract() {
        return tokensToExtract;
    }

    public void setTokensToExtract(int tokensToExtract) {
        this.tokensToExtract = tokensToExtract;
    }

    public ExplorationMap(int n, SharedMemory memory) {

        this.visitedCells = new HashSet<int[]>();
        this.unvisitedCells = new Stack<>();

        this.matrix = new Cell[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++){
                matrix[i][j] = new Cell(new LinkedList<>());
                this.unvisitedCells.add(new int[]{i,j});
            }

        Collections.shuffle(unvisitedCells);

        this.memory = memory;
        this.tokensToExtract = n;
        this.totalVisitedCells = 0;

    }

    public boolean visit(int row, int column, Robot robot) {
        synchronized (matrix[row][column]) {
            if (!matrix[row][column].isVisited()) {
                //access shared memory
                //extract n random tokens
                //add tokens to cell
                List<Token> tokensToBeAdded = this.memory.extractTokens(tokensToExtract);
                robot.setNumberOfTokensPlaced(robot.getNumberOfTokensPlaced()+tokensToBeAdded.size());
                matrix[row][column].setTokenList(tokensToBeAdded);
                matrix[row][column].setVisited(true);
                this.totalVisitedCells++;
              //  this.markAsVisited(new int[]{row, column});
                System.out.println("Robotul " + robot.getName() + " a visitat randul " + row + ", coloana " + column);
                return true;
            }
            return true;
        }
    }

    public boolean hasCellsLeft(){
        if (totalVisitedCells == (matrix.length - 1) * (matrix.length - 1))
            return false;
        return true;
    }
    public void unvisit(int row, int column, Robot robot){
        synchronized ( matrix[row][column]) {
            this.memory.addTokensBack(matrix[row][column].getTokenList());
            matrix[row][column].resetTokenList();
            matrix[row][column].setVisited(false);
            this.totalVisitedCells--;
            System.out.println("celula "+ row + " " + column + " a fost desvizitata");
        }
    }

    public int[] popStack(){
        synchronized (this.unvisitedCells){
            return this.unvisitedCells.pop();
        }
    }

    public void markAsVisited(int[] coordinates){
        this.visitedCells.add(coordinates);
    }

}
