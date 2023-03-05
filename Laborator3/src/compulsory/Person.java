package compulsory;

import utilities.Utils;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static utilities.Utils.dateToString;
import static utilities.Utils.stringToDate;

public class Person implements Comparable<Node>, Node {
    private String name;
    private int yearsOfExperience;

    private Date birthDate;

    //cand creez noi conexiuni, angajez fiecare persoana la o singura firma
    //inainte sa creez conexiunea cu o firma, vad daca nu cumva persoana e deja angajata
    boolean isEmployed = false;

    //type of entity - type of relationship
    public Map<Node, Utils.TypesOfRelationships> connections = new HashMap<>();

    public Person(String name, int yearsOfExperience, String birthDate) {
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;

        try {
            this.birthDate = stringToDate(birthDate);
        } catch (ParseException exception) {
            System.out.println(exception.toString());
        }

    }

    /**
     * @param o the object to be compared. The values of the names are compared
     * @return negative if nume1<nume2, 0 if nume==nume2, positive if nume1>nume2
     */
    @Override
    public int compareTo(Node o) {
        //compares name: negative if nume1<nume2, 0 if nume==nume2, positive if nume1>nume2
        return this.getName().compareTo(o.getName());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getNodeImportance() {
        return this.connections.size();
    }

    @Override
    public void printConnections() {
        System.out.println( this.getName() +" are " + this.getNodeImportance() + " conexiuni");

        System.out.println("conexiunile lui " + this.getName() +" : ");
        for(Map.Entry<Node, Utils.TypesOfRelationships> entry : this.getConnections().entrySet())
        {
            System.out.println(entry.getKey().getName() + " " + entry.getValue());
        }
        System.out.println();
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isEmployed() {
        return isEmployed;
    }

    public void setEmployed(boolean employed) {
        isEmployed = employed;
    }

    public Map<Node, Utils.TypesOfRelationships> getConnections() {
        return connections;
    }

    public void setConnections(Map<Node, Utils.TypesOfRelationships> connections) {
        this.connections = connections;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                ", birthDate=" + dateToString(birthDate) +
                '}';
    }
}
