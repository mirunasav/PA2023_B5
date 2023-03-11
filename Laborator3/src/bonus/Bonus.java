package bonus;

import compulsory.Company;
import compulsory.Node;
import compulsory.Person;
import homework.Designer;
import homework.Network;
import homework.Programmer;

import java.util.List;

import static utilities.Utils.createList;

public class Bonus {
    public static void main(String[] args) {
        Company Emag = new Company("Emag", "Romania");
        Company Facebook = new Company("Facebook", "USA");
        Company Google = new Company("Google", "USA");
        Company Levi9 = new Company("Levi9", "Romania");
        Company Amazon = new Company("Amazon", "USA");

        Person andreiStan = new Programmer("Andrei Stan", 5, "10-06-1990","Java");
        Person mariusPop = new Programmer("Marius Pop", 7, "09-05-1998","C#");
        Person catalinCatalin= new Designer("Catalin Catalin", 8, "12-10-1995",3);
        Person andreiMatei= new Designer("Andrei Matei", 2, "12-02-2002",1);
        Person andreeaMircea= new Designer("Andreea Mircea", 10, "12-02-1990",2);
        Person costinPreotu= new Programmer("Costin Preotu", 1, "12-10-2002","Python");
        Person mariaAlexa= new Designer("Maria Alexa", 2, "12-05-2002",1);

        List<Node> listOfEntities = createList(Emag, Facebook, Google, mariusPop, andreiStan, catalinCatalin,andreiMatei,andreeaMircea);

        Network smallNetwork = new Network(listOfEntities);
        smallNetwork.orderNetworkNodes();
      //  smallNetwork.printNetworkConnections();
        smallNetwork.createConnectionsGraph();
        System.out.println();

        Solution  connectedComponentsSolution = new Solution(smallNetwork, TypeOfSolutions.FIND_ARTICULATION_POINTS);
        System.out.println();

        Solution solution = new Solution(smallNetwork, TypeOfSolutions.FIND_MAXIMALLY2CONNECTED_COMPONENTS);
    }
}
