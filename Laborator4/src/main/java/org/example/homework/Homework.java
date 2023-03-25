package org.example.homework;

import javafx.util.Pair;
import org.example.Student;

import java.util.Arrays;

import static org.example.utilities.Utilities.*;

public class Homework {
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

        Problem randomInstanceOfProblem = createRandomProblemInstance(100,50);
        System.out.println(randomInstanceOfProblem);

        printStudentPreferences(randomInstanceOfProblem);
        findMatching(randomInstanceOfProblem);
    }
}
