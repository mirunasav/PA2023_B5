package org.example.compulsory;

import org.example.bonus.Node;

import java.util.Objects;

public class Project implements Comparable<Project>, Node {
    String name;

    public Project(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Project o) {
        return this.getName().compareTo(o.getName());
    }
    @Override
    public String toString() {
        return name + " ";
    }

}
