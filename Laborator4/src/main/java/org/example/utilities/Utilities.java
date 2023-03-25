package org.example.utilities;

import com.github.javafaker.Faker;
import javafx.util.Pair;
import org.example.bonus.Node;
import org.example.bonus.TypeOfAlgorithm;
import org.example.compulsory.Project;
import org.example.Student;
import org.example.homework.Problem;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.interfaces.VertexCoverAlgorithm;
import org.jgrapht.alg.matching.GreedyMaximumCardinalityMatching;
import org.jgrapht.alg.matching.HopcroftKarpMaximumCardinalityBipartiteMatching;
import org.jgrapht.alg.vertexcover.RecursiveExactVCImpl;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.*;
import java.util.stream.IntStream;

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

    public static Student[] createStudents(Project[] projects) {
        return IntStream.range(0, 3)
                .mapToObj(i -> new org.example.Student("S" + i,
                        IntStream.rangeClosed(0, 2 - i).mapToObj(j -> (org.example.compulsory.Project) projects[j]).toList()))
                .toArray(org.example.Student[]::new);
    }

    public static Project[] createProjects() {
        var projects = IntStream.range(0, 3)
                .mapToObj(i -> new org.example.compulsory.Project("P" + i))
                .toArray(org.example.compulsory.Project[]::new);
        return projects;
    }

    public static Student[] lowPreferencesStudents(Student[] students) {
        OptionalDouble average = averagePreferencesOfStudents(students);
        int averagePreferences;
        if (average.isPresent())
            averagePreferences = ((Double) average.getAsDouble()).intValue();
        else {
            averagePreferences = 0;
        }

        var studentsWithLowPreferences = Arrays.stream(students)
                .filter(student -> student.getAdmissableProjects().size() < averagePreferences)
                .toArray(org.example.Student[]::new);
        return studentsWithLowPreferences;
    }

    public static OptionalDouble averagePreferencesOfStudents(Student[] students) {
        return Arrays.stream(students).
                mapToInt(student -> student.getAdmissableProjects().size()).average();
    }

    public static void printStudents(Student[] students) {
        Arrays.asList(students).forEach(student -> System.out.println(student.getName()));
    }

    public static Project[] createRandomProjects(int numberOfProjects) {
        Faker faker = new Faker();
        List<Project> listOfProjects = new LinkedList<>();

        int i = 0;
        while (i < numberOfProjects) {
            String name = faker.pokemon().name();
            if (listOfProjects.stream().noneMatch(project -> project.getName().equals(name))) {
                listOfProjects.add(new Project(name));
                i++;
            }
        }
        return listOfProjects.toArray(Project[]::new);
        //returneaza si duplicate asta
//        return IntStream.range(0, 5)
//                .mapToObj(project -> new Project(faker.pokemon().name())).toArray(Project[]::new);
    }

    public static Student[] createRandomStudents(Project[] projects, int numberOfStudents) {
        Faker faker = new Faker();
        return IntStream.range(0, numberOfStudents)
                .mapToObj(student ->
                        new Student(faker.name().fullName(), randomProjectPreferences(projects)))
                .toArray(Student[]::new);
    }

    public static List<Project> randomProjectPreferences(Project[] projects) {
        Faker faker = new Faker();
        int randomNumberOfPreferences = faker.number().numberBetween(1, projects.length);

        List<Project> projectList = new LinkedList<>();
        int i = 0;

        while (i < randomNumberOfPreferences) {
            int randomIndex = faker.number().numberBetween(1, projects.length);
            if (!projectList.contains(projects[randomIndex])) {
                projectList.add(projects[randomIndex]);
                i++;
            }
        }

        return projectList;
    }

    public static Problem createRandomProblemInstance(int numberOfProjects, int numberOfStudents) {
        var projects = createRandomProjects(numberOfProjects);
        var students = createRandomStudents(projects, numberOfStudents);

        return new Problem(Arrays.asList(students), Arrays.asList(projects));
    }

    public static void findMatching(Problem problem) {
        //bun deci am un graf; iau muchii (Adica perechi de doua chestii : student -> proiect );
        //le pun in M (adica o lista de perechi student - proiect)
        //cand am o muchie care intersecteaza o alta (o extremitate exista deja in M), nu o adaug in M
        //graful meu va fi o lista de perechi student- proiect, care reprezinta toate muchiile din graf
        List<Pair<Student, Project>> listOfEdges = createGraphEdges(problem);
        List<Pair<Student, Project>> matching = new LinkedList<>();
        List<Project> copyOfProjectList = new LinkedList<>(problem.getProjectList());
        List<Student> copyOfStudentList = new LinkedList<>(problem.getStudentList());

        for (Pair<Student, Project> edge : listOfEdges) {
            //verific daca studentul / proiectul au fost alocate deja
            //daca studentul / proiectul lipsesc, au fost alocate deja; deci ambele trebuie sa fie continute in liste
            if (copyOfProjectList.contains(edge.getValue()) && copyOfStudentList.contains(edge.getKey())) {
                matching.add(edge);
                copyOfProjectList.remove(edge.getValue());
                copyOfStudentList.remove(edge.getKey());
            }
        }

        printMatching(matching);
    }

    public static List<Pair<Student, Project>> createGraphEdges(Problem problem) {
        List<Pair<Student, Project>> listOfEdges = new LinkedList<>();

        for (Student student : problem.getStudentList()) {
            for (Project project : student.getAdmissableProjects()) {
                Pair<Student, Project> pair = new Pair<>(student, project);
                listOfEdges.add(pair);
            }
        }
        return listOfEdges;
    }

    public static void printMatching(List<Pair<Student, Project>> matching) {
        for (Pair<Student, Project> edge : matching)
            System.out.println("Studentul " + edge.getKey().getName() + " are proiectul " + edge.getValue().getName());
        System.out.println();
    }

    public static void printStudentPreferences(Problem problem) {
        for (Student student : problem.getStudentList()) {
            System.out.print("Studentul " + student.getName() + " are preferintele ");
            for (Project project : student.getAdmissableProjects())
                System.out.print(project.getName() + ", ");
            System.out.println();
        }
        System.out.println();
    }

    public static Graph<Node, DefaultEdge> createGraph(List<Student> listOfStudents, List<Project> listOfProjects) {
        Graph<Node, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        for (Student student : listOfStudents)
            graph.addVertex(student);
        for (Project project : listOfProjects)
            graph.addVertex(project);

        for (Student student : listOfStudents)
            for (Project project : student.getAdmissableProjects()) {
                graph.addEdge(student, project);
            }
        return graph;
    }

    public static void greedyAlgorithmForMatching(List<Student> listOfStudents, List<Project> listOfProjects) {
        Graph<Node, DefaultEdge> graph = createGraph(listOfStudents, listOfProjects);
        MatchingAlgorithm<Node, DefaultEdge> matchingAlgorithm =
                new GreedyMaximumCardinalityMatching<>(graph, true);
        printMatching(matchingAlgorithm.getMatching());
    }

    /**
     * @param listOfStudents studentii din lista;
     * @param listOfProjects proiectele din lista;
     */
    public static void efficientMatchingAlgorithm(List<Student> listOfStudents, List<Project> listOfProjects) {
        Graph<Node, DefaultEdge> graph = createGraph(listOfStudents, listOfProjects);
        Set<Node> studentSet = new HashSet<>(listOfStudents);
        Set<Node> projectSet = new HashSet<>(listOfProjects);
        MatchingAlgorithm<Node, DefaultEdge> matchingAlgorithm =
                new HopcroftKarpMaximumCardinalityBipartiteMatching<>(graph, studentSet, projectSet);
        printMatching(matchingAlgorithm.getMatching());
    }

    public static void printMatching(MatchingAlgorithm.Matching<Node, DefaultEdge> matching) {
        matching.forEach(edge -> System.out.println(edge.toString()));
        System.out.println();
    }

    public static void findMinimumVertexCover(Problem problem) {
        printVertexCover(createMinimumVertexCover(problem));
    }

    /**
     * @param problem Finds a minimum vertex cover in a undirected graph.
     *                The implementation relies on a recursive algorithm.
     *                At each recursive step, the algorithm picks an unvisited vertex v
     *                and distinguishes two cases: either v has to be added to the vertex cover or all of its neighbors.
     * @return
     */
    public static VertexCoverAlgorithm.VertexCover<Node> createMinimumVertexCover(Problem problem) {
        Graph<Node, DefaultEdge> graph = createGraph(problem.getStudentList(), problem.getProjectList());
        VertexCoverAlgorithm<Node> vertexCoverAlgorithm = new RecursiveExactVCImpl<>(graph);
        return vertexCoverAlgorithm.getVertexCover();
    }

    public static void printVertexCover(VertexCoverAlgorithm.VertexCover<Node> vertexCover) {
        System.out.println("Minimum vertex cover: ");
        for (Node node : vertexCover)
            System.out.println(node.getName() + ", ");
        System.out.println();
    }

    /**
     * @param problem instanta a problemei;
     *                gaseste o multime de noduri astfel incat intre ele sa nu existe muchii;
     *                " Given a vertex cover of a graph, all vertices not in the cover define a independent vertex set";
     */
    public static void maximumStableSet(Problem problem) {
        //folosesc algoritmul de la vertexCover si toate nodurile care nu sunt in vertexCover, sunt in MIS
        Graph<Node, DefaultEdge> graph = createGraph(problem.getStudentList(), problem.getProjectList());
        VertexCoverAlgorithm.VertexCover<Node> vertexCover = createMinimumVertexCover(problem);
        List<Node> maximumStableSet = new LinkedList<>();
        for (Node node : graph.vertexSet())
            if (!vertexCover.contains(node))
                maximumStableSet.add(node);

        System.out.println("Maximum stable set: ");
        for (Node node : maximumStableSet)
            System.out.println(node.getName() + ", ");
        System.out.println();

    }

    private static double getRunningTime(TypeOfAlgorithm typeOfAlgorithm, Problem problem) {
        long begin = System.nanoTime();
        switch (typeOfAlgorithm) {
            case MY_GREEDY -> findMatching(problem);
            case JGRAPHT_GREEDY -> greedyAlgorithmForMatching(problem.getStudentList(), problem.getProjectList());
            case JGRAPHT_EFFICIENT -> efficientMatchingAlgorithm(problem.getStudentList(), problem.getProjectList());
        }
        long end = System.nanoTime();
        long timeInNanoseconds = end - begin;
        return (double) timeInNanoseconds / 1_000_000_000.0;
    }



    public static void testPerformance(int numberOfStudents, int numberOfProjects) {
        Problem problem = createRandomProblemInstance(numberOfProjects, numberOfStudents);
        double myGreedyRunningTime = getRunningTime(TypeOfAlgorithm.MY_GREEDY, problem);
        double greedyRunningTime = getRunningTime(TypeOfAlgorithm.JGRAPHT_GREEDY, problem);
        double efficientRunningTime = getRunningTime(TypeOfAlgorithm.JGRAPHT_EFFICIENT, problem);
        System.out.println("My greedy :  " + greedyRunningTime + ", JGraphT greedy : " + greedyRunningTime +
                ", efficient JGraphT: " + efficientRunningTime);
        List a = new LinkedList();

    }

    // long begin = System.nanoTime();
    //
    //        run(false);
    //
    //        long end = System.nanoTime();
    //        long timeInNanoseconds = end - begin;
    //        System.out.println("Elapsed time in nanoseconds : " + timeInNanoseconds);
    //
    //        double timeInSeconds = (double) timeInNanoseconds / 1_000_000_000.0;
    //        System.out.println("Elapsed time in seconds : " + timeInSeconds);
}
