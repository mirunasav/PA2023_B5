package homework;

import compulsory.Node;
import compulsory.Person;

public class Designer extends Person implements Node {
    private int maxAmountOfProjects;

    public Designer(String name, int yearsOfExperience, String birthDate , int maxAmountOfProjects) {
        super(name, yearsOfExperience, birthDate);
        this.maxAmountOfProjects = maxAmountOfProjects;
    }
}
