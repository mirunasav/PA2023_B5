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
        Company Emag = new Company("Emag", 2000);
        Company Facebook = new Company("Facebook", 10000);
        Company Google = new Company("Google", 12000);

        Person andreiStan = new Person("Andrei Stan", 5);
        Person mariusPop = new Person("Marius Pop", 7);
        Person catalinCatalin= new Person("Catalin Catalin", 8);

        List <Node> listOfEntities = createList(Emag, Facebook, Google, mariusPop, andreiStan, catalinCatalin);
        printList(listOfEntities, Utils.TypesOfPrinting.BULK);
        printList(listOfEntities, Utils.TypesOfPrinting.ONE_BY_ONE);

    }
}
