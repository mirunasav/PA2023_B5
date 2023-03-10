package compulsory;

import java.util.LinkedList;
import java.util.List;

public class Company implements Comparable<Node>, Node {
    private String name;
    private String country;
    private int numberOfEmployees = 0;

    private List<Person> employees = new LinkedList<>();

    public Company(String name, String country) {
        this.name = name;
        this.country = country;
    }

    /**
     * @param o the object to be compared. The values of the names are compared
     * @return -1,  negative if nume1<nume2, 0 if nume==nume2, positive if nume1>nume2.
     */
    @Override
    public int compareTo(Node o) {
        //compares name: negative if nume1<nume2, 0 if nume==nume2, positive if nume1>nume2
        return this.getName().compareTo(o.getName());
    }

    @Override

    public Integer getNodeImportance() {
        return this.getNumberOfEmployees();
    }

    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public void printConnections() {
        System.out.println(this.getName() + " are " + this.getNodeImportance() + " conexiuni");
        System.out.println("Compania " + this.getName() + " are angajatii: ");
        for (Person person : employees)
            System.out.print(person.getName() + "   ");
        System.out.println();
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Person> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Person> employees) {
        this.employees = employees;
    }

    public void addEmployee(Person person) {
        this.numberOfEmployees++;
        this.employees.add(person);
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", numberOfEmployees=" + numberOfEmployees +
                ", employees=" + employees +
                '}';
    }
}
