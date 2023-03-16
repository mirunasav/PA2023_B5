package org.example;

import java.util.stream.IntStream;

import static org.example.utilities.Utilities.*;


public class Compulsory {
    public static void main(String[] args) {
        //Example: 3 students (S0,S1,S2) and 3 projects (P0,P1,P2).
        //Projects considered admissible by: S0={P0,P1,P2}, S1={P0,P1}, S2={P0}.
        //The maximum cardinality matching should be: {S0-P2, S1-P1, S2-P0}.

        var projects = createProjects();
        var students = createStudents(projects);

        createStudentList(students);
        createProjectTreeSet(projects);
    }
}