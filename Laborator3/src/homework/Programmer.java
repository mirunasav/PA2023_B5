package homework;

import compulsory.Node;
import compulsory.Person;

public class Programmer extends Person implements Node {
    private String languageOfProgramming;
    public Programmer(String name, int yearsOfExperience, String birthDate, String languageOfProgramming) {
        super(name, yearsOfExperience, birthDate);
        this.languageOfProgramming = languageOfProgramming;
    }

    public String getLanguageOfProgramming() {
        return languageOfProgramming;
    }

    public void setLanguageOfProgramming(String languageOfProgramming) {
        this.languageOfProgramming = languageOfProgramming;
    }
}
