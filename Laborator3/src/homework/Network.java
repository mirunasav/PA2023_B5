package homework;

import compulsory.Company;
import compulsory.Node;
import compulsory.Person;
import utilities.Utils;

import java.util.*;

import static utilities.Utils.*;

public class Network {
    private List<Node> listOfEntities;

    private Map<Node, List<Node>> connectionsGraph;

    public Network(List<Node> listOfEntities) {
        this.listOfEntities = listOfEntities;
        createConnections(listOfEntities);
    }

    public List<Node> getListOfEntities() {
        return listOfEntities;
    }

    public void setListOfEntities(List<Node> listOfEntities) {
        this.listOfEntities = listOfEntities;
    }

    private int getNodeImportance(Node node) throws IllegalArgumentException {
        if (!this.listOfEntities.contains(node))
            throw new IllegalArgumentException();
        return node.getNodeImportance();
    }

    public void orderNetworkNodes()
    {
        this.setListOfEntities(orderNodesByImportance(this.listOfEntities));
    }

    public void printNetworkConnections(){
        printConnections(this.listOfEntities);
    }

    /**
     * @return un graf al conexiunilor dintre entitatile din network,sub forma unui map, unde:
     * key = entitate(companie/persoana), value = lista de adiacenta(pentru persoane:oameni/companii;
     * pentru companii:employees);
     *
     */
    public void createConnectionsGraph()
    {
        Map<Node, List<Node>> connectionsGraph = new HashMap<>();
        for(Node node : this.listOfEntities)
        {
            List <Node> adjacencyList = new LinkedList<>();
            if(node instanceof Company)
            {
//                for(Person person : ((Company) node).getEmployees())
//                {
//                    adjacencyList.add(person);
//                }
                adjacencyList.addAll(((Company) node).getEmployees()); //mai elegant frumos, thank u IDE

            }
            if(node instanceof  Person)
            {
                for(Map.Entry<Node, Utils.TypesOfRelationships> entry : ((Person) node).getConnections().entrySet())
                {
                    adjacencyList.add(entry.getKey());
                }
            }
            connectionsGraph.put(node, adjacencyList);
        }
        this.connectionsGraph = connectionsGraph;
        this.printConnectionsGraph();
    }

    public void printConnectionsGraph()
    {
        for(Map.Entry<Node, List<Node>> entry : this.connectionsGraph.entrySet())
        {
            System.out.print("Nodul " + entry.getKey().getName() + " are conexiunile : " );
            for(Node node : entry.getValue())
                System.out.print(node.getName() + ", ");
            System.out.println();
        }
    }

    public Map<Node, List<Node>> getConnectionsGraph() {
        return connectionsGraph;
    }

    public void setConnectionsGraph(Map<Node, List<Node>> connectionsGraph) {
        this.connectionsGraph = connectionsGraph;
    }
}


