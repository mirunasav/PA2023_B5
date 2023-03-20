package org.example.bonus;

import org.example.Student;
import org.example.homework.Problem;

import java.util.Arrays;

import static org.example.utilities.Utilities.*;
import static org.example.utilities.Utilities.findMatching;

public class Bonus {
    public static void main(String[] args) {
        var projects = createProjects();
        var students = createStudents(projects);
        Problem instanceOfProblem = new Problem(Arrays.asList(students),Arrays.asList(projects));

        System.out.println("numarul average de preferinte este: ");
        System.out.println(averagePreferencesOfStudents( instanceOfProblem.getStudentList()
                .toArray(Student[]::new)));

        System.out.println("studentii cu numar mai mic decat average de preferinte sunt: ");
        printStudents(lowPreferencesStudents(instanceOfProblem.getStudentList()
                .toArray(Student[]::new)));

//        Problem randomInstanceOfProblem = createRandomProblemInstance(100,50);//50 studenti, 100 de proiecte
//        System.out.println(randomInstanceOfProblem);
//
//        printStudentPreferences(randomInstanceOfProblem);
//        System.out.println("Algoritmul facut de mine: \n");
//        findMatching(randomInstanceOfProblem);
//
//        System.out.println("Algoritmul greedy din JGraphT: \n");
//        greedyAlgorithmForMatching(randomInstanceOfProblem.getStudentList(), randomInstanceOfProblem.getProjectList());
//
//        System.out.println("Algoritmul eficient din JGraphT: \n");
//        efficientMatchingAlgorithm(randomInstanceOfProblem.getStudentList(), randomInstanceOfProblem.getProjectList());
//
//        findMinimumVertexCover(randomInstanceOfProblem);
//        maximumStableSet(randomInstanceOfProblem);

        testPerformance(100,100);
    }
}
