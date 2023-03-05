package compulsory;

public class Person implements Comparable<Node>, Node{
    private String name;
    private int yearsOfExperience;

    public Person(String name, int yearsOfExperience) {
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
    }

    /**
     * @param o the object to be compared. The values of the names are compared
     * @return  negative if nume1<nume2, 0 if nume==nume2, positive if nume1>nume2
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

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                '}';
    }
}
