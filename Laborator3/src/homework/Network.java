package homework;

import compulsory.Node;

import java.util.Comparator;
import java.util.List;

import static utilities.Utils.*;

public class Network {
    private List<Node> listOfEntities;

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

}
