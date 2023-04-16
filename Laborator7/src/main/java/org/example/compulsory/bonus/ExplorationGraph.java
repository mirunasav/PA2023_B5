package org.example.compulsory.bonus;

import javafx.util.Pair;
import org.jgrapht.Graph;
import org.jgrapht.generate.GnmRandomGraphGenerator;
import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.generate.ScaleFreeGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.*;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import java.util.*;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * creez un graf ce va fi explorat intr-o maniera concurenta. Zona asta de memorie o sa fie shared;
 * fiecare nod o sa primeasca si un id(adica un numar banuiesc, gen nodul 1, nodul 2 etc)
 */
public class ExplorationGraph {
    private static ExplorationGraph instance;

    private  Graph<Vertex, DefaultEdge> graph;
    public static final int numberOfVertices = 10;
    public static  int numberOfEdges = 10;

    private int numberOfVisitedVertices = 0;

    public static ExplorationGraph getInstance() {
        if (instance == null)
           instance =  new ExplorationGraph();
        return instance;
    }

    private ExplorationGraph() {
       this.generateRandomConnectedGraph();
    }

    private void addVertices() {
        IntStream.range(0, numberOfVertices)
                .mapToObj(Vertex::new)
                .forEach(graph::addVertex);
    }

    private void addEdges() {
        //o sa fac sa fie adaugate random;
        //generez indecsi random
        for (int i = 0; i < numberOfEdges; i++) {
            int[] adjacentvertices = generateRandomVertices();
            this.addEdge(adjacentvertices[0], adjacentvertices[1]);
        }
    }

    private int[] generateRandomVertices() {
        Random rand = new Random();
        int firstIndex = 0, secondIndex = 0;
        do {
            firstIndex = rand.nextInt(numberOfVertices);
            secondIndex = rand.nextInt(numberOfVertices);
        }
        while (!isEdgeValid(firstIndex, secondIndex));
        return new int[]{firstIndex, secondIndex};
    }

    private boolean isEdgeValid(int firstIndex, int secondIndex) {
        if (firstIndex == secondIndex)
            return false;
        if (this.graphHasEdge(firstIndex, secondIndex))
            return false;
        return true;
    }

    private boolean graphHasEdge(int firstIndex, int secondIndex) {
        for (DefaultEdge edge : graph.edgeSet()) {
            Vertex source = graph.getEdgeSource(edge);
            Vertex target = graph.getEdgeTarget(edge);
            if (source.getId() == firstIndex && target.getId() == secondIndex)
                return true;
        }
        return false;
    }

    private void addEdge(int firstIndex, int secondIndex) {
        Vertex source = (Vertex) graph.vertexSet().toArray()[0];
        Vertex target = (Vertex) graph.vertexSet().toArray()[0];
        for (Vertex vertex : graph.vertexSet()) {
            if (vertex.getId() == firstIndex)
                source = vertex;
            else if (vertex.getId() == secondIndex)
                target = vertex;
        }
        graph.addEdge(source, target);
    }

    public Vertex findVertexById(int id) {
        for (Vertex vertex : graph.vertexSet()) {
            if (vertex.getId() == id)
                return vertex;
        }
        return null;
    }

    /**
     * @param id vertex id
     * @return true, if the vertex was not previously visited;false, if it was already visited
     */
    public boolean visitVertex(int id, Robot robot) {
        Vertex vertexToVisit = instance.findVertexById(id);
        if (vertexToVisit != null) {
            if (vertexToVisit.visit()) {
                //System.out.println("Robotul "+  robot.getName() + " a vizitat nodul " + vertexToVisit.getId());
                this.numberOfVisitedVertices++;
                return true;
            }
        }
        return false;
    }

    public void printGraph() {
        System.out.println(graph);
    }

    public int getNumberOfVisitedVertices() {
        return numberOfVisitedVertices;
    }

    public void setNumberOfVisitedVertices(int numberOfVisitedVertices) {
        this.numberOfVisitedVertices = numberOfVisitedVertices;
    }

    public Graph<Vertex, DefaultEdge> getGraph() {
        return graph;
    }

    public void setGraph(Graph<Vertex, DefaultEdge> graph) {
        this.graph = graph;
    }

    public void generateRandomConnectedGraph(){
        Supplier<Vertex> vertexSupplier = new VertexFactory();
        Supplier<DefaultEdge> edgeSupplier = DefaultEdge::new;
        graph = new DefaultUndirectedGraph<>(vertexSupplier,edgeSupplier,false);

        Random rand = new Random();
        numberOfEdges =   rand.nextInt((numberOfVertices*(numberOfVertices-1))/2 - numberOfVertices + 2) + numberOfVertices - 1;
        GnmRandomGraphGenerator<Vertex, DefaultEdge> generator = new GnmRandomGraphGenerator<>(numberOfVertices, numberOfEdges);
        generator.generateGraph(graph);
        this.printGraph();
    }
}
