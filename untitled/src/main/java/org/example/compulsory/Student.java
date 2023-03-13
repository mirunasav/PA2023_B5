package org.example.compulsory;

import java.util.Comparator;
import java.util.List;

public class Student implements Comparable<Student> {
    String name;
    List<Project> admissableProjects;

    public Student(String name, List <Project> listOfAdmissableProjects) {
        this.name = name;
        this.admissableProjects = listOfAdmissableProjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getAdmissableProjects() {
        return admissableProjects;
    }

    public void setAdmissableProjects(List<Project> admissableProjects) {
        this.admissableProjects = admissableProjects;
    }

    @Override
    public int compareTo(Student o) {
        return this.getName().compareTo(o.getName());
    }
}
