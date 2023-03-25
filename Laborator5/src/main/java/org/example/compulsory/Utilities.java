package org.example.compulsory;

import javafx.util.Pair;
import org.jgrapht.Graph;
import org.jgrapht.alg.color.BrownBacktrackColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Utilities {
    public static boolean isPathValid(String path) {
        try {
            Paths.get(path);

        } catch (InvalidPathException ex) {
            return false;
        }
        return true;
    }

    private static Graph<Document, DefaultEdge> createGraph(Catalog catalog){
        Graph<Document, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);

        catalog.getEntries().forEach(graph::addVertex);

        //vreau sa parcurg toate aceste entries; pentru asta, vreau sa le iau tagurile si sa le pun in acest map
        Map<Pair<TypesOfTags, String>, List<Document>> tagMap = new HashMap<>();
        //dupa aceasta operatie, am creeat si muchiile.
        catalog.getEntries().forEach(document -> {
            for (var entry : document.getTags().entrySet()) {
                //creez o noua pereche
                Pair<TypesOfTags, String> pair = new Pair<>(entry.getKey(), entry.getValue());

                //verific daca perechea exista in map
                if (!tagMap.containsKey(pair)) {
                    //daca nu exista, fac o lista noua de documente cu acest tag si adaug documentul
                    List<Document> documents = new LinkedList<>();
                    documents.add(document);
                    tagMap.put(pair, documents);
                } else {
                    //daca deja exista documente cu acest tag, doar adaug documentul nou printre ele
                    tagMap.get(pair).add(document);
                }
            }
            //am terminat de facut mapul; acum il parcurg si creez muchii intre toate documentele din aceeasi lsita
        });
        catalog.getEntries().forEach(document -> {
            for (var entry : tagMap.entrySet()) {
                if (entry.getValue().contains(document)) {
                    entry.getValue().forEach(relatedDocument -> {
                        if (!(graph.containsEdge(document, relatedDocument)) && (document != relatedDocument)) {
                            graph.addEdge(document, relatedDocument);
                        }
                    });
                }
            }
        });
        return graph;
    }
    /**
     * @param catalog catalogul al carui graf il fac; fiecare nod va fi un document; pentru fiecare document,
     *                verific daca are taguri in comun cu altele; daca da, fac muchii;
     */
    public static void minimumColoring(Catalog catalog) {
        Graph<Document, DefaultEdge> graph = createGraph(catalog);
        VertexColoringAlgorithm<Document> algorithm = new BrownBacktrackColoring<>(graph);
        VertexColoringAlgorithm.Coloring<Document> coloring = algorithm.getColoring();
        System.out.println("numarul de culori folosit este: " + coloring.getNumberColors());
      //  printGraph(graph);
    }
    public static void printGraph(Graph <Document, DefaultEdge> graph){
        for(var edge : graph.edgeSet()){
            System.out.println(graph.getEdgeSource(edge).getName() + " " + graph.getEdgeTarget(edge).getName());
        }
    }
}
