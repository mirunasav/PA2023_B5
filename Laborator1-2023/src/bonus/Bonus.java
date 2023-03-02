//Savin Miruna, A5
package bonus;

import java.util.*;

import static java.lang.System.exit;

public class Bonus {
    public static Integer n;
    public static int[][] adjacencyMatrix;

    public static void createAdjacencyMatrix() {
        adjacencyMatrix = new int[n + 1][n + 1];

        for (int[] row : adjacencyMatrix)
            Arrays.fill(row, 0);

        adjacencyMatrix[1][2] = 1;
        adjacencyMatrix[1][n] = 1;

        for (int i = 2; i < n; i++) {
            adjacencyMatrix[i][i + 1] = 1;
            adjacencyMatrix[i][i - 1] = 1;
        }

        adjacencyMatrix[n][1] = 1;
        adjacencyMatrix[n][n - 1] = 1;

        System.out.println("Matricea arata astfel: ");
        printMatrix(adjacencyMatrix);
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix.length; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }

    //aplicam un algoritm clasic de inmultire a doua matrice nxn :
    //inmultesc pe rand toate elementele  liniei k din prima matrice cu cele ale coloanei k din a doua matrice si le adun
    //rezultatele le pun in variabila temporara result
    public static int[][] multiplyMatrices(int[][] firstMatrix, int[][] secondMatrix) {
        int[][] result = new int[firstMatrix.length][firstMatrix.length];
        for (int i = 0; i < firstMatrix.length; i++) {//liniile primei matrice
            for (int j = 0; j < firstMatrix[i].length; j++) {//coloanele celei de a doua matrici
                int sum = 0;
                for (int k = 0; k < firstMatrix.length; k++) {//numarul elementului de pe coloana j din a doua matrice (linia elementului)
                    sum += firstMatrix[i][k] * secondMatrix[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    public static void powerMatrix(int[][] matrix) {
        int[][] result = matrix;
        int power = n;
        for (int k = 1; k < power; k++) {
            result = multiplyMatrices(result, matrix);
            System.out.println("Matricea la puterea " + (k + 1) + " arata astfel: ");
            printMatrix(result);
        }
        System.out.println();
    }

    public static boolean validateGeneratedNumber(int number, int callingNumber, Map<Integer, List<Integer>> adjacencyLists, List<Integer> adjacencyList, int vertexDegree) {
        //verific daca -> nodul ala e deja invecinat cu callingNumber;
        //          -> nodul ala are numar maxim de vecini deja
        //          -> nodul generat e acelasi cu callingNumber
        if (adjacencyList.contains(number))
            return false;
        if (number == callingNumber)
            return false;
        if (adjacencyLists.containsKey(number) && adjacencyLists.get(number).size() == vertexDegree)
            return false;
        return true; //contine : return false; altfel true
    }

    public static int generateNumber(int maxValue) {
        int minValue = 1;

        return (int) (Math.random() * (maxValue - minValue)) + minValue;
    }

    public static int[][] createAdjacencyMatrixOfRefgularGraphDeterministApproach(int numberOfVertices, int vertexDegree) {
        //PROBLEMA: nici varianta asta nu genereaza mereu, tot tb sa am un element de randomness (?) pt ca matricele
        //grafurilor regulate nu a o forma generala

        //in loc sa generez random numere, ca in varianta nedeterminista de mai jos, pentru fiecare nod i parcurg pe rand nodurile de la 1 la n
        //si vad daca le pot adauga in lista de adiacenta a lui i
        int[][] matrixOfRegularGraph = new int[numberOfVertices + 1][numberOfVertices + 1];

        for (int[] row : matrixOfRegularGraph)
            Arrays.fill(row, 0);

        Map<Integer, List<Integer>> adjacencyLists = new HashMap<>();
        //creez listele de adiacenta pentru fiecare nod, ca sa nu ma mai chinui dupa sa vad daca nodul se afla in map sau nu
        for (int i = 1; i <= numberOfVertices; i++) {
            List<Integer> neighboursList = new ArrayList<>();
            adjacencyLists.put(i, neighboursList);
        }

        for (int i = 1; i <= numberOfVertices; i++) {
            if (adjacencyLists.get(i).size() < vertexDegree)//daca mai pot adauga noduri in lista de adiacenta a lui i
            {
                int remainingSlots = vertexDegree - adjacencyLists.get(i).size(); //cate noduri mai pot adauga
                for (int j = numberOfVertices; j >= 1; j--) {
                    if (remainingSlots > 0)
                        //iau nodurile in ordine si vad daca sunt in lista de adiacenta deja sau nu
                        if (j != i && !adjacencyLists.get(i).contains(j) && adjacencyLists.get(j).size() < vertexDegree) {
                            adjacencyLists.get(i).add(j); //adaug nodul in ambele liste
                            adjacencyLists.get(j).add(i);
                            matrixOfRegularGraph[i][j] = 1;//modific matricea
                            matrixOfRegularGraph[j][i] = 1;
                            remainingSlots--;//scad numarul de noduri pe care le mai pot adauga
                        }
                }
            }
        }
        return matrixOfRegularGraph;
    }

    public static int[][] createAdjacencyMatrixOfRegularGraphNonDeterministApproach(int numberOfVertices, int vertexDegree) {
        /// PROBLEMA: algoritmul e nedeterminist, si dureaza extrem de mult sa ruleze; ca idee cred ca e corect
        /// dar nu e eficient deloc


        //as putea sa creez random niste matrici si sa vad daca au eigenvalue = 1, pana cand gasesc una buna
        //nu prea vreau sa calculez eigenvalue asa incat o sa fac altfel
        //generez matrici random si verific urmatoarea proprietate: pe fiecare rand, am vertexDegree valori de 1
        //for i de la 1 la n:
        //1. vad cate campuri goale am(cate numere mai tb sa generez, cate "grade"libere am
        //2. stiu cate numere tb sa generez, dar verific sa nu se repete numerele: pot
        //2.a pot sa generez random nr si sa vad daca e deja 1 pe pozitia aia in matrice; daca da, repet
        //2.b pot sa fac o lista cu numere  ce reprezinta varfurile cu care se invecineaza nodul meu si am grija sa nu am numere din lista aia
        //3. generez numere: completez casuta respectiva

        int[][] matrixOfRegularGraph = new int[numberOfVertices + 1][numberOfVertices + 1];

        for (int[] row : matrixOfRegularGraph)
            Arrays.fill(row, 0);

        Map<Integer, List<Integer>> adjacencyLists = new HashMap<>();

        for (int i = 1; i <= numberOfVertices; i++) {
            int remainingSlots;

            if (adjacencyLists.containsKey(i)) {
                //daca exista in map nodul i, vad cati vecini are deja
                int numberOfNeighbours = adjacencyLists.get(i).size();
                remainingSlots = vertexDegree - numberOfNeighbours;
            } else {
                //creez lista de adiacenta si o inserez
                List<Integer> neighboursList = new ArrayList<>();
                adjacencyLists.put(i, neighboursList);
                remainingSlots = vertexDegree;
            }
            while (remainingSlots > 0) {
                int number = generateNumber(numberOfVertices);
                if (validateGeneratedNumber(number, i, adjacencyLists, adjacencyLists.get(i), vertexDegree)) {
                    //daca numarul e validat il adaug in lista de adiacenta a nodului i
                    //pe urma modific matricea si pun 1 pe pozitiile necesare
                    adjacencyLists.get(i).add(number);
                    matrixOfRegularGraph[i][number] = 1;
                    matrixOfRegularGraph[number][i] = 1;
                    //trebuie sa modific si lista de adiacenta a nodului number;
                    //daca exista in map, doar adaug in lista
                    //daca nu exista, creez o lista doua, adaug in ea i, si adaug in map elementul number cu lista lui

                    if (adjacencyLists.containsKey(number))
                        adjacencyLists.get(number).add(i);
                    else {
                        List<Integer> neighbourList = new ArrayList<>();
                        neighbourList.add(i);
                        adjacencyLists.put(number, neighbourList);
                    }
                    remainingSlots--;
                }

            }
        }
        return matrixOfRegularGraph;
    }

    public static ArrayList<Integer> generateRandomPermutation(int n) {
        ArrayList<Integer> vectorOfRemainingNumbers = new ArrayList<>();
        ArrayList<Integer> permutation = new ArrayList<>();

        for (int i = 1; i <= n; i++)
            vectorOfRemainingNumbers.add(i);

        while (vectorOfRemainingNumbers.size() > 0) {
            int index = (int) (Math.random() * vectorOfRemainingNumbers.size());//generez un index random
            int number = vectorOfRemainingNumbers.get(index);
            permutation.add(number);
            vectorOfRemainingNumbers.set(index, vectorOfRemainingNumbers.get(vectorOfRemainingNumbers.size() - 1));
            //pun pe indexul ala ultimul numar, pe urma sterg ultima pozitie din vector
            vectorOfRemainingNumbers.remove(vectorOfRemainingNumbers.size() - 1);
        }
        return permutation;
    }

    public static boolean validateMatrix(int[][] matrix, int vertexDegree, int numberOfVertices) {
        //verific daca toate nodurile au gradul vertexDegree
        for (int i = 1; i <= numberOfVertices; i++) {
            int degree = 0;
            for (int j = 1; j <= numberOfVertices; j++)
                degree += matrix[i][j];
            if (degree != vertexDegree)
                return false;

        }
        return true;
    }

    public static int[][] createAdjacencyMatrixOfRegularGraph(int numberOfVertices, int vertexDegree) {
        //o sa fac sa mi genereze o permutare random , nu mai iau acel j din al doilea for ca fiind mereu de la n la 1 / de la 1 la n
        if(!(numberOfVertices >= vertexDegree + 1 && (numberOfVertices*vertexDegree)%2 == 0 )) //adica nu poate exista un graf regulat cu conditiile date
        {
            System.out.println("nu exista un graf regulat cu  "+ numberOfVertices + " noduri si gradul " + vertexDegree);
            exit(2);
        }

        System.out.println("O matrice pentru graful regulat cu " + numberOfVertices +" noduri si gradul " + vertexDegree +" este: ");
        int[][] matrixOfRegularGraph = new int[numberOfVertices + 1][numberOfVertices + 1];
        do {
            for (int[] row : matrixOfRegularGraph)
                Arrays.fill(row, 0);

            Map<Integer, List<Integer>> adjacencyLists = new HashMap<>();
            //creez listele de adiacenta pentru fiecare nod, ca sa nu ma mai chinui dupa sa vad daca nodul se afla in map sau nu
            for (int i = 1; i <= numberOfVertices; i++) {
                List<Integer> neighboursList = new ArrayList<>();
                adjacencyLists.put(i, neighboursList);
            }

            for (int i = 1; i <= numberOfVertices; i++) {
                if (adjacencyLists.get(i).size() < vertexDegree)//daca mai pot adauga noduri in lista de adiacenta a lui i
                {
                    int remainingSlots = vertexDegree - adjacencyLists.get(i).size(); //cate noduri mai pot adauga
                    ArrayList<Integer> permutation = generateRandomPermutation(numberOfVertices);
                    for (Integer j : permutation) {
                        if (remainingSlots > 0)
                            //iau nodurile in ordine si vad daca sunt in lista de adiacenta deja sau nu
                            if (j != i && !adjacencyLists.get(i).contains(j) && adjacencyLists.get(j).size() < vertexDegree) {
                                adjacencyLists.get(i).add(j); //adaug nodul in ambele liste
                                adjacencyLists.get(j).add(i);
                                matrixOfRegularGraph[i][j] = 1;//modific matricea
                                matrixOfRegularGraph[j][i] = 1;
                                remainingSlots--;//scad numarul de noduri pe care le mai pot adauga
                            }
                    }
                }
            }
        }
        while (!validateMatrix(matrixOfRegularGraph, vertexDegree, numberOfVertices));
        return matrixOfRegularGraph;
    }

    public static void main(String[] args) {
        n = Integer.parseInt(args[0]);
        createAdjacencyMatrix();
        powerMatrix(adjacencyMatrix);
        printMatrix(createAdjacencyMatrixOfRegularGraph(5, 3));
    }
}
