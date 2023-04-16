package org.example.compulsory.bonus;

import com.sun.security.jgss.GSSUtil;
import org.jgrapht.Graphs;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Robot implements Runnable{

    private String name;

    private volatile boolean running;

    private ExplorationGraph explorationGraph;

    public Robot(String name, ExplorationGraph explorationGraph) {
        this.name = name;
        this.explorationGraph = explorationGraph;
        this.running = false;
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

    public ExplorationGraph getExplorationGraph() {
        return explorationGraph;
    }

    public void setExplorationGraph(ExplorationGraph explorationGraph) {
        this.explorationGraph = explorationGraph;
    }

    //fac iar un fel de DFS
    //1.generez o pozitie de start
    //2. vad de unde pot sa merg in continuare
    //3. aleg un vecin oarecare si de acolo continui sa merg sa vizitez
    @Override
    public void run() {
        this.running = true;

        while(running){
            int currentVertexIndex = generateRandomVertex();
            recursiveRun(currentVertexIndex);

           synchronized (System.out){
               System.out.println("Robotul " + this.name + " s- a oprit");
           }
            this.running = false;

            if(this.explorationGraph.getNumberOfVisitedVertices() == ExplorationGraph.numberOfVertices)
                System.out.println("s-a terminat de explorat graful");
        }
    }

    public void recursiveRun(int currentVertexIndex){
        if(running){
            if(this.explorationGraph.visitVertex(currentVertexIndex,this)){
                synchronized (System.out){
                    System.out.println("Robotul  " + this.name + " a explorat nodul " + currentVertexIndex);
                }
            };
            Iterator<Integer> it = this.generateNeighbourList(currentVertexIndex).listIterator();
            while (it.hasNext()){
                int nextIndex = it.next();
                //daca nu e vizitat
                if(!this.explorationGraph.findVertexById(nextIndex).isVisited())
                {
                    //System.out.println("Nodul " + nextIndex + "nu este vizitat; vizitez acum");
                    try {
                        Thread.sleep(1);
                    } catch (Exception exception) {
                        System.out.println(exception.toString());
                    }
                    recursiveRun(nextIndex);
                }
            }
            
        }
    }
    private int generateRandomVertex(){
        int vertexIndex;
        Random random = new Random();
        vertexIndex = random.nextInt(ExplorationGraph.numberOfVertices);
        while(ExplorationGraph.getInstance().findVertexById(vertexIndex).isVisited()){
            System.out.println("am incercat nodul " + this.explorationGraph.findVertexById(vertexIndex).getId());
            vertexIndex = random.nextInt(ExplorationGraph.numberOfVertices);
        }
        return vertexIndex;
    }

    private List<Integer> generateNeighbourList(int index){
        Vertex vertex = this.explorationGraph.findVertexById(index);
        List<Integer> neighbourList = new LinkedList<>();
        if(vertex!=null){
            List<Vertex> neighbours = Graphs.neighborListOf(this.explorationGraph.getGraph(), vertex);
            neighbours.forEach(neighbour -> {
                neighbourList.add(neighbour.getId());
            });
        }
        return neighbourList;
    }
}
