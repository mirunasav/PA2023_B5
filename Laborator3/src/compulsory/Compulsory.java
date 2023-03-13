package compulsory;

import utilities.Utils;

import java.util.List;

import static utilities.Utils.createList;
import static utilities.Utils.printList;

//persons, companies , unique names
//person  + person relation : how do they know each other? family, acquaiantance, friends, coworkers
//person + company : position
//specialized positions : programmer, designer
public class Compulsory {
    public static void main(String[] args) {
        Company Emag = new Company("Emag", "Romania");
        Company Facebook = new Company("Facebook", "USA");
        Company Google = new Company("Google", "USA");

        Person andreiStan = new Person("Andrei Stan", 5, "10-06-1990");
        Person mariusPop = new Person("Marius Pop", 7, "09-05-1998");
        Person catalinCatalin = new Person("Catalin Catalin", 8, "12-10-1995");
        Person andreiMatei = new Person("Andrei Matei", 2, "12-02-2002");

        List<Node> listOfEntities = createList(Emag, Facebook, Google, mariusPop, andreiStan, catalinCatalin, andreiMatei);
        printList(listOfEntities, Utils.TypesOfPrinting.BULK);
        printList(listOfEntities, Utils.TypesOfPrinting.ONE_BY_ONE);
        new Node.bar();

    }
}
