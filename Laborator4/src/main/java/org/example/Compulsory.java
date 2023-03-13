package org.example;

import java.util.stream.IntStream;

import static utilities.Utilities.createProjectTreeSet;
import static utilities.Utilities.createStudentList;

public class Compulsory {
    public static void main(String[] args) {
        //Example: 3 students (S0,S1,S2) and 3 projects (P0,P1,P2).
        //Projects considered admissible by: S0={P0,P1,P2}, S1={P0,P1}, S2={P0}.
        //The maximum cardinality matching should be: {S0-P2, S1-P1, S2-P0}.
        var projects = IntStream.range(0, 3)
                .mapToObj(i -> new org.example.compulsory.Project("P" + i) )
                .toArray(org.example.compulsory.Project[]::new);

        var students = IntStream.range(0, 3)
                .mapToObj(i -> new org.example.compulsory.Student("S" + i,
                        IntStream.rangeClosed(0,2-i).mapToObj(j->(org.example.compulsory.Project)projects[j]).toList()) )
                .toArray(org.example.compulsory.Student[]::new);

        createStudentList(students);
        createProjectTreeSet(projects);
    }
}