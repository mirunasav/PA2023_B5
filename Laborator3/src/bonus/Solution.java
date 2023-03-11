package bonus;

import compulsory.Node;
import compulsory.Person;
import homework.Network;
import utilities.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Solution {
    Network network;
    static int time = 0;

    public Solution(Network network, TypeOfSolutions typeOfsolution) {
        this.network = network;
        switch (typeOfsolution) {
            case FIND_ARTICULATION_POINTS -> {
                this.printArticulationPoints(this.findArticulationPoints(this.getNetwork().getListOfEntities()));
            }
            case FIND_2CONNECTED_COMPONENTS -> this.print2ConnectedComponents(this.find2ConnectedComponents());
            case FIND_MAXIMALLY2CONNECTED_COMPONENTS ->
                    this.print2ConnectedComponents(this.findMaximally2ConnectedComponents());
        }
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    /**
     * @return O lista cu nodurile care sunt puncte de articulatie in retea (daca le scot, se deconecteaza reteaua);
     * Folosesc algoritmul lui Tarjan: intr-un arbore DFS, un nod este punct de articulatie, DACA:
     * 1.este radacina arborelui DFS si are minim 2 fii
     * 2.nu e radacina, dar are un copil v, astfel incat in
     * subarborele lui v nu exista noduri cu un backedge la un stramos al lui u
     */
    private List<Node> findArticulationPoints(List<Node> listOfEntities) {
        //1.fac o parcurgere DFS in care retin un vector de parinti (parent[]);

        //2.Iau fiecare nod in parte, u , si verific:
        //a) daca e radacina si are minim 2 copii
        //b) altfel:
        //3.Mentin si un vector discovered[] care retine momentul in care "descopar" fiecare nod
        //4.pentru fiecare nod u, gasesc nodul descoperit cel mai devreme v in care pot ajunge din subarborele lui u

        //5.creez un al treilea array, low[], astfel incat low[u] = min(disc[u],disc[w]), unde w e stramos al lui u pentru care
        //exista backedge din subarborele lui u
        int numberOfVertices = listOfEntities.size();
        boolean[] visited = new boolean[numberOfVertices];
        int[] discovered = new int[numberOfVertices];
        int[] lowest = new int[numberOfVertices];
        boolean[] isArticulationPoint = new boolean[numberOfVertices];
        List<Node> articulationPoints = new LinkedList<>();

        int parent = -1;

        for (int i = 0; i < numberOfVertices; i++)
            if (!visited[i])
                checkIfItIsArticulationPoint(i, visited, discovered, lowest, parent, isArticulationPoint, listOfEntities);

        for (int i = 0; i < numberOfVertices; i++)
            if (isArticulationPoint[i]) {
                articulationPoints.add(this.network.getListOfEntities().get(i));
            }
        return articulationPoints;
    }

    private void checkIfItIsArticulationPoint(int u, boolean[] visited,
                                              int[] discovered, int[] lowest, int parent, boolean[] isArticulationPoint, List<Node> listOfEntities) {
        //numaram copiii din arborele DFS
        int children = 0;

        visited[u] = true;

        discovered[u] = ++time;
        lowest[u] = time;

        //gasesc lista de adiacenta din graful meu
        //cheia este obiectul aflat la indexul u in lista de entitati
        for (Node node : this.network.getConnectionsGraph().get(listOfEntities.get(u))) {
            //daca nodul nu e visited, il marchez ca si copil al lui u in arborele DFS si
            // continui parcurgerea de la el, recursiv
            int index = this.network.getListOfEntities().indexOf(node);
            if (!visited[index]) {
                children++;
                checkIfItIsArticulationPoint(index, visited, discovered, lowest, u, isArticulationPoint, listOfEntities);

                //verific daca subarborele marcat cu index are o conexiune cu unul dintre stramosii lui u
                lowest[u] = Math.min(lowest[u], lowest[index]);

                //daca u nu este root si valoarea low a unuia dintre copii e mai mare decat disc [u]
                if (parent != -1 && lowest[index] >= discovered[u])
                    isArticulationPoint[u] = true;
            }
            //Daca nodul e deja vizitat, facem update la valoarea low a lui u
            else if (index != parent)
                lowest[u] = Math.max(lowest[u], discovered[index]);

        }
        //verific daca u e root si are mai mult de doi copii
        if (parent == -1 && children > 1)
            isArticulationPoint[u] = true;
    }

    private void printArticulationPoints(List<Node> articulationPoints) {
        if (articulationPoints.size() == 0)
            System.out.println("Reteaua nu are puncte de articulatie!");
        else
            for (Node node : articulationPoints)
                System.out.println("Nodul " + node.getName() + " este punct de articulatie in retea!");
    }

    private List<List<Node>> createConnectedComponents() {
        List<List<Node>> listOf2ConnectedComponents = new LinkedList<>();

        boolean[] visited = new boolean[this.getNetwork().getListOfEntities().size()];

        for (int index = 0; index < this.getNetwork().getListOfEntities().size(); index++) {
            if (!visited[index]) {
                List<Node> connectedComponents = new LinkedList<>();
                connectedComponents = recursiveDFS(this.getNetwork().getListOfEntities(), index, visited);
                if (connectedComponents.size() != 0)
                    listOf2ConnectedComponents.add(connectedComponents);
            }
        }
        return listOf2ConnectedComponents;
    }

    /*
    I have to find the subgraphs that are MAXIMALLY 2-connected -> 1 connected (if I remove one vertex, it disconnects -> they have articulation points)
    2 connected : they don't have any articulation points; I can remove any 2 vertexes and they become disconnected
    Naive approach : find the connected components in the graph first -> for each, check if they have articulation points; if they do -> 1 connected
    if they don't have articulation points, remove 2 random vertices, see if they are connected anymore; if not-> maximally 2 connected

    Or for each connected component I can remove 2 random vertices and check if the resulted graph is still connected; if yes->not maximally 2connected
     */
    private List<List<Node>> find2ConnectedComponents() {
        //ar avea mai mult sens sa fie doar componentele 2 conexe
        //si cumva ar tb sa iau mai multe combinatii?

        List<List<Node>> listOf2ConnectedComponents = new LinkedList<>();
        listOf2ConnectedComponents = this.createConnectedComponents();

        //acum, in listOf2ConnectedComponents am componentele conexe ale grafului; cum verific ca sunt maxim 2 conexe?
        // 1. au pct de articulatie; nu au? -> 2.scot doua noduri si vad daca mai sunt conexe

        System.out.println("Componentele conexe sunt: ");
        for (List<Node> connectedComponents : listOf2ConnectedComponents) {
            for (Node node : connectedComponents)
                System.out.print(node.getName() + ", ");
            System.out.println();
            System.out.println();
        }

        Iterator<List<Node>> iterator = listOf2ConnectedComponents.iterator();
        while (iterator.hasNext()) {
            List<Node> connectedComponent = iterator.next();
            if (!this.isMax2Connected(connectedComponent))
                iterator.remove();
        }

        //the method below throws ConcurrentModificationException!!

//        for(List <Node> connectedComponent : listOf2ConnectedComponents)
//        {
//            if(!this.isMax2Connected(connectedComponent))
//                listOf2ConnectedComponents.remove(connectedComponent);
//        }
        // listOf2ConnectedComponents.removeIf(connectedcomponent -> !this.isMax2Connected(connectedcomponent));

        return listOf2ConnectedComponents;
    }

    /**
     * @return componentele maximally 2 connected;
     * Idee: iau componentele conexe, verific daca sunt 2 conexe; verific care e gradul minim (trebuie sa fie 2)
     */
    private List<List<Node>> findMaximally2ConnectedComponents() {
        List<List<Node>> connectedComponentsList = this.createConnectedComponents();
        Iterator<List<Node>> iterator = connectedComponentsList.iterator();
        while (iterator.hasNext()) {
            List<Node> connectedComponent = iterator.next();
            if (connectedComponent.size() < 3) {
                iterator.remove();
                continue;
            }
            if(!isMaximally2Connected(connectedComponent))
                iterator.remove();
        }
        return connectedComponentsList;
    }

    private List<Node> recursiveDFS(List<Node> listOfNodes, int index, boolean[] visited) {
        List<Node> connectedComponents = new LinkedList<>();
        //adaug nodul de pe indexul index din lista
        connectedComponents.add(listOfNodes.get(index));
        visited[index] = true;

        //pentru fiecare nod din lista de adiacenta a nodului cu indexul index
        for (Node node : this.getNetwork().getConnectionsGraph().get(listOfNodes.get(index))) {
            if (listOfNodes.contains(node))
                if (!visited[listOfNodes.indexOf(node)]) {
                    connectedComponents.addAll(recursiveDFS(listOfNodes, listOfNodes.indexOf(node), visited));
                }
        }
        return connectedComponents;
    }

    /**
     * @param graph
     * @return daca e >2connected, returneaza false; daca e <2 connected, true
     */
    private boolean isMax2Connected(List<Node> graph) {
        // pt fiecare componenta conexa, verific ->
        // 1. daca au  puncte de articulatie (au:atunci e clar macar 2 connected, ca orice nod as scoate nu se deconecteaza)
        //2. scot 2 noduri random dupa, daca se deconecteaza atunci clar nu e mai mult de 2 connected

        //graf >=3 conex : minim 4 noduri -> sub 3 noduri nu verific
        if (graph.size() <= 3)
            return true;

        if (!this.hasArticulationPoints(graph))
            return true;

        List<Node> copyOfGraph = new LinkedList<>(graph);

        return !this.is2Connected(copyOfGraph);
    }


    /**
     * @param graph
     * @return: true, daca e >2 conex; false, altfel
     */
    private boolean is2Connected(List<Node> graph) {
        List<Node> copyOfGraph = new LinkedList<>(graph);
        copyOfGraph.remove(0);
        copyOfGraph.remove(0);
        //fac o lista in care retin nodurile din DFS traversal : daca graful ar fi >2conex, as avea toate nodurile in DFS traversal

        List<Node> DFSTraversalList = new LinkedList<>();
        boolean[] visited = new boolean[copyOfGraph.size()];
        for (boolean value : visited)
            value = false;

        DFSTraversalList = recursiveDFS(copyOfGraph, 0, visited);
        //verific daca toate nodurile din graf sunt in DFSTraversal; daca macar unul nu e , atunci graful nu e 2conex
        for (Node node : copyOfGraph)
            if (!DFSTraversalList.contains(node)) {
                return false;
            }
        return true;
    }

    protected boolean isMaximally2Connected(List <Node> graph)
    {
        if(!(this.is2Connected(graph) && Utils.minimumGraphDegree(graph)==2))
            return false;
        return true;
    }

    private boolean hasArticulationPoints(List<Node> graph) {
        List<Node> listOfArticulationPoints = this.findArticulationPoints(graph);
        return listOfArticulationPoints.size() != 0;
    }

    private void print2ConnectedComponents(List<List<Node>> twoConnectedComponents) {
        if (twoConnectedComponents.size() == 0)
            System.out.println("Reteaua nu are componente care sa fie maxim 2-conexe!");
        else
            for (List<Node> list : twoConnectedComponents) {
                System.out.println("Componenta maxim 2-conexa: ");
                for (Node node : list)
                    System.out.print(node.getName() + ", ");
                System.out.println();
            }
    }
}


/*
Ce inteleg prin maximally 2 connected ?
sa fie 1-connected sau 2connected, dar nu mai mult de atat

Notiunea 2:
"A graph is said to be maximally connected if its connectivity equals its minimum degree"

In cazul nostru : un graf e maximally 2 connnected daca e 2connected si gradul minim al unui nod este 2
 */