package compulsory;

public class Company implements Comparable<Node>, Node{

    private String name;
    private int numberOfEmployees;

    public Company(String name, int numberOfEmployees) {
        this.name = name;
        this.numberOfEmployees = numberOfEmployees;
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
    public String getName() {
        return this.name;
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

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", numberOfEmployees=" + numberOfEmployees +
                '}';
    }
}
