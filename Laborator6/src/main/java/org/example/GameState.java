package org.example;

import java.io.Serializable;

public class GameState implements Serializable {
    public int numVertices;
    public double edgeProbability;
    public int[] x, y;

    // 0 - no edge, 1 - grey edge, 2 - blue edge, 3 - red edge
    public int[][] edge;

    public int turn, selected;

    public boolean won = false;

    public int winner = 0;
    public GameState() {
        turn = 0;
        selected = -1;
    }

    public boolean win(){
        for(int i=0;i<numVertices;i++)
            for(int j=i+1;j<numVertices;j++)
                for(int k=j+1;k<numVertices;k++)
                    if(edge[i][j] == edge[j][k] && edge[i][j] == edge[i][k] && edge[i][j]>1)
                    {
                        System.out.println("Jucatorul " + (turn+1) +" a castigat!");
                        return true;
                    }

        return false;
    }

    public boolean addEdge(int x, int y){
        if(edge[x][y] == 1)
            return true;
        else return false;
    }

    public void createVertices(int W, int H){
        int x0 = W / 2; int y0 = H / 2; //middle of the board
        int radius = H / 2 - 10; //board radius
        double alpha = 2 * Math.PI / numVertices; // the angle
        x = new int[numVertices];
        y = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            x[i] = x0 + (int) (radius * Math.cos(alpha * i));
            y[i] = y0 + (int) (radius * Math.sin(alpha * i));
        }
    }

    public void createEdges(){
        edge = new int[numVertices][numVertices];
        for(int i=0;i<numVertices;i++)
            for(int j=0;j<numVertices;j++)
                if(Math.random() < edgeProbability)
                    edge[i][j] = edge[j][i] = 1;
    }

}
