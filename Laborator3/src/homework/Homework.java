package homework;

import compulsory.Company;
import compulsory.Node;
import compulsory.Person;

import java.util.List;

import static utilities.Utils.*;

public class Homework {
    public static void main(String[] args) {
        Company Emag = new Company("Emag", "Romania");
        Company Facebook = new Company("Facebook", "USA");
        Company Google = new Company("Google", "USA");

        Person andreiStan = new Programmer("Andrei Stan", 5, "10-06-1990","Java");
        Person mariusPop = new Programmer("Marius Pop", 7, "09-05-1998","C#");
        Person catalinCatalin= new Designer("Catalin Catalin", 8, "12-10-1995",3);
        Person andreiMatei= new Designer("Andrei Matei", 2, "12-02-2002",1);

        List<Node> listOfEntities = createList(Emag, Facebook, Google, mariusPop, andreiStan, catalinCatalin,andreiMatei);

        Network smallNetwork = new Network(listOfEntities);
        smallNetwork.orderNetworkNodes();
        smallNetwork.printNetworkConnections();
        smallNetwork.createConnectionsGraph();


    }
}
