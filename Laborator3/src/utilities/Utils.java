package utilities;

import bonus.Solution;
import compulsory.Company;
import compulsory.Node;
import compulsory.Person;
import homework.Designer;
import homework.Network;
import homework.Programmer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    public enum TypesOfPrinting {
        BULK,
        ONE_BY_ONE
    }

    public enum TypesOfRelationships {

        WORKS_AS_PROGRAMMER,
        WORKS_AS_DESIGNER,
        ACQUAIANTANCE,
        FAMILY,
        COWORKERS
    }

    static int time = 0;
    public static String datePattern = "dd-mm-yyyy";
    public static SimpleDateFormat simpleDateFormat;

    static {
        simpleDateFormat = createDateFormat(datePattern);
    }

    /**
     * @param nodes the nodes that will be in the list
     * @return the list of nodes
     */
    public static List<Node> createList(Node... nodes) {
        /*
        Varianta cu mai mult scris:
        List <Node> listOfNodes = new LinkedList<>();
        for(node : nodes)
            listOfNodes.add(node);
        return listOfNodes;
         */
        return new LinkedList<>(Arrays.asList(nodes));
    }

    public static void printList(List<Node> listOfNodes, TypesOfPrinting typeOfPrinting) {
        switch (typeOfPrinting) {
            case BULK -> {
                System.out.println(listOfNodes.toString());
                break;
            }
            case ONE_BY_ONE -> {
                for (int i = 0; i < listOfNodes.size(); i++)
                    System.out.println(listOfNodes.get(i).toString());
            }
        }

    }

    private static SimpleDateFormat createDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    public static Date stringToDate(String dateAsString) throws ParseException {
        return simpleDateFormat.parse(dateAsString);
    }

    public static String dateToString(Date date) {
        return simpleDateFormat.format(date);
    }

    /**
     * @param listOfEntities the list of entities in the network; the function randomly creates connections
     */
    public static void createConnections(List<Node> listOfEntities) {
        //mai intai creez un map nou de conexiuni pt fiecare persoana pe care am creat-o deja
        // for (Node person : listOfEntities)
        //   if (person instanceof Person)
        //     ((Person) person).connections = new HashMap<>();

        //apoi pentru fiecare generez un numar random de la 1 la listOfEntities.size() pt a stabili nr max de conexiuni

        //pe urma generez indecsi random si vad daca pot adauga listOFEntities.get(i)
        // la lista de conexiuni e persoanei pe care o conectez
        int maxNumberOfConnections = getMaxConnections(listOfEntities);
        for (Node person : listOfEntities)
            if (person instanceof Person) {
                int maxConnections = generateNumber(1, maxNumberOfConnections);
                for (int i = 0; i < maxConnections; i++) {
                    int index = generateNumber(1, listOfEntities.size());

                    //verific daca entitatea generata e obiectul insusi
                    if (!listOfEntities.get(index).equals(person))

                        //verific daca entitatea generata e deja conectata de obiectul meu
                        if (!((Person) person).getConnections().containsKey(listOfEntities.get(index))) {
                            //verific de ce tip e entitatea si in functie de asta adaug relatia
                            if (listOfEntities.get(index) instanceof Person) {
                                //creez conexiunea in ambele liste
                                ((Person) person).addRelationship((Person) listOfEntities.get(index), TypesOfRelationships.COWORKERS);
                                ((Person) listOfEntities.get(index)).addRelationship((Person) person, TypesOfRelationships.COWORKERS);
                            }

                            if (listOfEntities.get(index) instanceof Company && !((Person) person).isEmployed()) {
                                //daca nodul cu care fac legatura e o companie: marchez omul ca si angajat
                                //maresc nr de employees al companiei si adaug omul in lista de angajati
                                ((Person) person).addEmployment((Company) listOfEntities.get(index));
                                ((Company) listOfEntities.get(index)).addEmployee((Person) person);
                            }

                        }

                }
            }

    }

    public static void printConnections(List<Node> listOfEntities) {
        for (Node entity : listOfEntities) {
            entity.printConnections();
            System.out.println();
        }

    }

    public static int generateNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * @param listOfEntities se da lista de entitati din network
     * @return nr maxim de conexiuni pe care o persoana le poate avea = nr de persoane din network-1 (conexiuni cu persoane)
     * +1(1 companie)
     */
    public static int getMaxConnections(List<Node> listOfEntities) {
        int maxNumber = 0;
        for (Node node : listOfEntities)
            if (node instanceof Person)
                maxNumber++;
        return maxNumber;
    }


    /**
     * @param listOfEntities orders nodes by their importance, aka number of connections
     * @return a list containing nodes in descending order of importance
     */
    public static List<Node> orderNodesByImportance(List<Node> listOfEntities) {
        //created a comparator for the nodes
        Comparator<Node> compareByImportance = (Node o1, Node o2) -> o1.getNodeImportance().compareTo(o2.getNodeImportance());

        listOfEntities.sort(compareByImportance);
        Collections.reverse(listOfEntities);
        return listOfEntities;
    }

    public static int minimumGraphDegree(List<Node> graph) {
        int minimumDegree = Integer.MAX_VALUE;
        for (Node node : graph) {
            if (node.getNodeImportance() < minimumDegree)
                minimumDegree = node.getNodeImportance();
        }
        return minimumDegree;
    }

    //nu merge asa, o sa fac un graf cu persoane care o sa fie conectat
    public static Network createExampleBiconnectedGraph() {
        Map<Node, List<Node>> graph = new HashMap<>();

        Person andreiStan = new Programmer("Andrei Stan", 5, "10-06-1990", "Java");
        Person mariusPop = new Programmer("Marius Pop", 7, "09-05-1998", "C#");
        Person catalinCatalin = new Designer("Catalin Catalin", 8, "12-10-1995", 3);
        Person andreiMatei = new Designer("Andrei Matei", 2, "12-02-2002", 1);
        Person mironBogdan = new Designer("Miron Bogdan", 10, "12-02-1990", 2);

        andreiStan.addRelationship(mariusPop, TypesOfRelationships.COWORKERS);
        andreiStan.addRelationship(catalinCatalin, TypesOfRelationships.COWORKERS);

        mariusPop.addRelationship(andreiStan, TypesOfRelationships.COWORKERS);
        mariusPop.addRelationship(catalinCatalin, TypesOfRelationships.COWORKERS);
        mariusPop.addRelationship(andreiMatei, TypesOfRelationships.COWORKERS);

        andreiMatei.addRelationship(mariusPop, TypesOfRelationships.COWORKERS);
        andreiMatei.addRelationship(mironBogdan, TypesOfRelationships.COWORKERS);

        catalinCatalin.addRelationship(andreiStan, TypesOfRelationships.COWORKERS);
        catalinCatalin.addRelationship(mariusPop, TypesOfRelationships.COWORKERS);
        catalinCatalin.addRelationship(mironBogdan, TypesOfRelationships.COWORKERS);

        mironBogdan.addRelationship(catalinCatalin, TypesOfRelationships.COWORKERS);
        mironBogdan.addRelationship(andreiMatei, TypesOfRelationships.COWORKERS);

        List<Node> people = createList(andreiStan, mariusPop, andreiMatei, catalinCatalin, mironBogdan);

        Network network = new Network(people);

        for (Node node : people) {
            List<Node> adjacencyList = new LinkedList<>();

            for (Map.Entry<Node, Utils.TypesOfRelationships> entry : ((Person) node).getConnections().entrySet()) {
                adjacencyList.add(entry.getKey());
            }

            graph.put(node, adjacencyList);
        }
        network.setConnectionsGraph(graph);

        return network;
    }

    //functions which were previously in the Solution class
    public static void print2ConnectedComponents(List<List<Node>> twoConnectedComponents) {
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

    /**
     * @return componentele maximally 2 connected;
     * Idee: iau componentele conexe, verific daca sunt 2 conexe; verific care e gradul minim (trebuie sa fie 2)
     */
    public static List<List<Node>> findMaximally2ConnectedComponents(Solution solution) {
        List<List<Node>> connectedComponentsList = new LinkedList<>();
        connectedComponentsList = createConnectedComponents(solution.getNetwork());
        Iterator<List<Node>> iterator = connectedComponentsList.iterator();
        while (iterator.hasNext()) {
            List<Node> connectedComponent = iterator.next();
            if (connectedComponent.size() < 3) {
                iterator.remove();
                continue;
            }
            if(!isMaximally2Connected(solution.getNetwork(),connectedComponent))
                iterator.remove();
        }
        return connectedComponentsList;
    }

    /*
   I have to find the subgraphs that are MAXIMALLY 2-connected -> 1 connected (if I remove one vertex, it disconnects -> they have articulation points)
   2 connected : they don't have any articulation points; I can remove any 2 vertexes and they become disconnected
   Naive approach : find the connected components in the graph first -> for each, check if they have articulation points; if they do -> 1 connected
   if they don't have articulation points, remove 2 random vertices, see if they are connected anymore; if not-> maximally 2 connected

   Or for each connected component I can remove 2 random vertices and check if the resulted graph is still connected; if yes->not maximally 2connected
    */
    public static List<List<Node>> find2ConnectedComponents(Network network) {
        //ar avea mai mult sens sa fie doar componentele 2 conexe
        //si cumva ar tb sa iau mai multe combinatii?

        List<List<Node>> listOf2ConnectedComponents = new LinkedList<>();
        listOf2ConnectedComponents = createConnectedComponents(network);

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
            if (!isMax2Connected(network,connectedComponent))
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


    public static List<List<Node>> createConnectedComponents(Network network) {
        List<List<Node>> listOf2ConnectedComponents = new LinkedList<>();

        boolean[] visited = new boolean[network.getListOfEntities().size()];

        for (int index = 0; index < network.getListOfEntities().size(); index++) {
            if (!visited[index]) {
                List<Node> connectedComponents = new LinkedList<>();
                connectedComponents = recursiveDFS(network,network.getListOfEntities(), index, visited);
                if (connectedComponents.size() != 0)
                    listOf2ConnectedComponents.add(connectedComponents);
            }
        }
        return listOf2ConnectedComponents;
    }

    public static List<Node> recursiveDFS(Network network,List<Node> listOfNodes, int index, boolean[] visited) {
        List<Node> connectedComponents = new LinkedList<>();
        //adaug nodul de pe indexul index din lista
        connectedComponents.add(listOfNodes.get(index));
        visited[index] = true;

        //pentru fiecare nod din lista de adiacenta a nodului cu indexul index
        for (Node node : network.getConnectionsGraph().get(listOfNodes.get(index))) {
            if (listOfNodes.contains(node))
                if (!visited[listOfNodes.indexOf(node)]) {
                    connectedComponents.addAll(recursiveDFS(network,listOfNodes, listOfNodes.indexOf(node), visited));
                }
        }
        return connectedComponents;
    }
    /**
     * @param graph
     * @return: true, daca e >2 conex; false, altfel
     */
    public static boolean is2Connected(Network network,List<Node> graph) {
        List<Node> copyOfGraph = new LinkedList<>(graph);
        copyOfGraph.remove(0);
        copyOfGraph.remove(0);
        //fac o lista in care retin nodurile din DFS traversal : daca graful ar fi >2conex, as avea toate nodurile in DFS traversal

        List<Node> DFSTraversalList = new LinkedList<>();
        boolean[] visited = new boolean[copyOfGraph.size()];
        for (boolean value : visited)
            value = false;

        DFSTraversalList = recursiveDFS(network,copyOfGraph, 0, visited);
        //verific daca toate nodurile din graf sunt in DFSTraversal; daca macar unul nu e , atunci graful nu e 2conex
        for (Node node : copyOfGraph)
            if (!DFSTraversalList.contains(node)) {
                return false;
            }
        return true;
    }


    public static boolean isMaximally2Connected(Network network,List <Node> graph)
    {
        if(!(is2Connected(network,graph) && minimumGraphDegree(graph)==2))
            return false;
        return true;
    }

    /**
     * @return O lista cu nodurile care sunt puncte de articulatie in retea (daca le scot, se deconecteaza reteaua);
     * Folosesc algoritmul lui Tarjan: intr-un arbore DFS, un nod este punct de articulatie, DACA:
     * 1.este radacina arborelui DFS si are minim 2 fii
     * 2.nu e radacina, dar are un copil v, astfel incat in
     * subarborele lui v nu exista noduri cu un backedge la un stramos al lui u
     */
    public static List<Node> findArticulationPoints(Network network,List<Node> listOfEntities) {
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
                checkIfItIsArticulationPoint(network,i, visited, discovered, lowest, parent, isArticulationPoint, listOfEntities);

        for (int i = 0; i < numberOfVertices; i++)
            if (isArticulationPoint[i]) {
                articulationPoints.add(network.getListOfEntities().get(i));
            }
        time = 0;
        return articulationPoints;
    }


    public static boolean hasArticulationPoints(Network network,List<Node> graph) {
        List<Node> listOfArticulationPoints = findArticulationPoints(network,graph);
        return listOfArticulationPoints.size() != 0;
    }

    public static void printArticulationPoints(List<Node> articulationPoints) {
        if (articulationPoints.size() == 0)
            System.out.println("Reteaua nu are puncte de articulatie!");
        else
            for (Node node : articulationPoints)
                System.out.println("Nodul " + node.getName() + " este punct de articulatie in retea!");
    }



    public static void checkIfItIsArticulationPoint(Network network, int u, boolean[] visited,
                                                     int[] discovered, int[] lowest, int parent, boolean[] isArticulationPoint, List<Node> listOfEntities) {
        //numaram copiii din arborele DFS
        int children = 0;

        visited[u] = true;

        discovered[u] = ++time;
        lowest[u] = time;

        //gasesc lista de adiacenta din graful meu
        //cheia este obiectul aflat la indexul u in lista de entitati
        for (Node node : network.getConnectionsGraph().get(listOfEntities.get(u))) {
            //daca nodul nu e visited, il marchez ca si copil al lui u in arborele DFS si
            // continui parcurgerea de la el, recursiv
            int index = network.getListOfEntities().indexOf(node);
            if (!visited[index]) {
                children++;
                checkIfItIsArticulationPoint(network,index, visited, discovered, lowest, u, isArticulationPoint, listOfEntities);

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


    /**
     * @param graph
     * @return daca e >2connected, returneaza false; daca e <2 connected, true
     */
    private static boolean isMax2Connected(Network network,List<Node> graph) {
        // pt fiecare componenta conexa, verific ->
        // 1. daca au  puncte de articulatie (au:atunci e clar macar 2 connected, ca orice nod as scoate nu se deconecteaza)
        //2. scot 2 noduri random dupa, daca se deconecteaza atunci clar nu e mai mult de 2 connected

        //graf >=3 conex : minim 4 noduri -> sub 3 noduri nu verific
        if (graph.size() <= 3)
            return true;

        if (hasArticulationPoints(network,graph))
            return true;

        List<Node> copyOfGraph = new LinkedList<>(graph);

        return !is2Connected(network,copyOfGraph);
    }

}
