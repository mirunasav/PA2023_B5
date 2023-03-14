package utilities;

import org.example.compulsory.Project;
import org.example.compulsory.Student;

import java.sql.Array;
import java.util.*;

public class Utilities {
    public static List<Project> createProjectList(Project[] projectArray) {
        return new LinkedList<>(Arrays.asList(projectArray));
    }

    public static void createStudentList(Student... students) {
        List<Student> listOfStudents = new LinkedList<>(Arrays.asList(students));
        listOfStudents.sort(Student::compareTo);
        printStudents(listOfStudents);
    }

    public static void printStudents(List<Student> listOfStudents) {
        System.out.println("Studentii ordonati alfabetic sunt: ");
        for (Student student : listOfStudents)
            System.out.print(student.getName() + " ");
        System.out.println();
    }

    public static void createProjectTreeSet(Project... projects) {
        System.out.println("Proiectele sunt: ");

        TreeSet<Project> projectTreeSet = new TreeSet<>(Project::compareTo);

        projectTreeSet.addAll(Arrays.asList(projects));
        projectTreeSet.forEach(element -> System.out.print(element.getName() + " "));

    }

}
