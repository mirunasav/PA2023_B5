package utilities;

import compulsory.Node;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Utils {
    public enum TypesOfPrinting {
        BULK,
        ONE_BY_ONE
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

}
