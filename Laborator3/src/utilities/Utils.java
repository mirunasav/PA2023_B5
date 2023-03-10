package utilities;

import compulsory.Company;
import compulsory.Node;
import compulsory.Person;
import homework.Designer;
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
     *
     */
    public static void createConnections(List<Node> listOfEntities) {
        //mai intai creez un map nou de conexiuni pt fiecare persoana pe care am creat-o deja
        // for (Node person : listOfEntities)
        //   if (person instanceof Person)
        //     ((Person) person).connections = new HashMap<>();

        //apoi pentru fiecare generez un numar random de la 1 la listOfEntities.size() pt a stabili nr max de conexiuni

        //pe urma generez indecsi random si vad daca pot adauga listOFEntities.get(i)
        // la lista de conexiuni e persoanei pe care o conectez
        int maxNumberOfConnections = getMaxConnections(listOfEntities) ;
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
                            if (listOfEntities.get(index) instanceof Person)
                            {
                                //creez conexiunea in ambele liste
                                ((Person) person).addRelationship((Person) listOfEntities.get(index),TypesOfRelationships.COWORKERS);
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
    public static int getMaxConnections(List <Node> listOfEntities)
    {
        int maxNumber = 0;
        for(Node node : listOfEntities)
            if(node instanceof Person)
                maxNumber++;
        return maxNumber;
    }


    /**
     * @param listOfEntities orders nodes by their importance, aka number of connections
     * @return a list containing nodes in descending order of importance
     */
    public static List<Node> orderNodesByImportance(List <Node> listOfEntities)
    {
        //created a comparator for the nodes
        Comparator<Node> compareByImportance = (Node o1, Node o2)-> o1.getNodeImportance().compareTo(o2.getNodeImportance());

       listOfEntities.sort(compareByImportance);
       Collections.reverse(listOfEntities);
       return listOfEntities;
    }
}
