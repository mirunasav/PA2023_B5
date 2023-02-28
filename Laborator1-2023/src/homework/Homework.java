//Savin Miruna, A5
package homework;

import static java.lang.System.exit;

public class Homework {
    static Integer n;
    static Integer[][] latinSquareMatrix;

    public static void createLatinSquareMatrix() {
        latinSquareMatrix = new Integer[n][n];

        //practic o sa fac permutari in felul urmator:
        //pe prima linie: 1 .. n
        //pe a doua linie : 5 1 2...n-1
        //pe a k-a linie : n-k+2 n-k+3 ... n   1 2...n-k+1
        //pe a n-a linie:n-n+2 n-n+3 ... 1 , adica 2 3 .. n 1

        int rotationPoint = n + 1;
        //mai intai punem pe linii numerele de la k la n, apoi de la 1 la k-1
        for (int i = 0; i < n; i++) //pe fiecare linie
        {
            int firstNumberOnRow = rotationPoint;
            int columnNumber = 0;

            while (firstNumberOnRow <= n) {
                latinSquareMatrix[i][columnNumber] = firstNumberOnRow;
                firstNumberOnRow++;
                columnNumber++;
            }

            for (int j = 1; j < rotationPoint; j++) {
                latinSquareMatrix[i][columnNumber] = j;
                columnNumber++;
            }
            rotationPoint--;
        }

    }

    public static void printMatrix() {
        System.out.println("Matricea arata astfel: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(latinSquareMatrix[i][j]+" ");
            System.out.println();
        }
    }

    public static void printLineConcatenation(boolean shouldPrint) {
       if(shouldPrint)
           System.out.println("concatenam liniile: ");
        StringBuilder concatenationString;
        for (int i = 0; i < n; i++) {
            concatenationString = new StringBuilder();
            for (int j = 0; j < n; j++)
                concatenationString.append(latinSquareMatrix[i][j].toString());
            if(shouldPrint)
                System.out.println(concatenationString);
        }
    }

    public static void printColumnConcatenation(boolean shouldPrint) {
        if(shouldPrint)
            System.out.println("concatenam coloanele:");
        StringBuilder concatenationString;
        for (int j = 0; j < n; j++) {
            concatenationString = new StringBuilder();
            for (int i = 0; i < n; i++)
                concatenationString.append(latinSquareMatrix[i][j].toString());
           if(shouldPrint)
               System.out.println(concatenationString);
        }
    }

    public static void validateArguments(String[] args) {
        n = Integer.parseInt(args[0]);//arunca eroare daca n nu este int : asa se face verificarea
        if (n < 1) {
            System.out.println("Numarul introdus este prea mic!");
            exit(1);
        }

    }

    public static void run(boolean shouldPrint) {
        createLatinSquareMatrix();
        if(shouldPrint)
        {
            printMatrix();
            printColumnConcatenation(true);
            printLineConcatenation(true);
        }
        else
        {
            printColumnConcatenation(false);
            printLineConcatenation(false);
        }

    }

    public static void runningTime() {
        long begin = System.nanoTime();

        run(false);

        long end = System.nanoTime();
        long timeInNanoseconds = end - begin;
        System.out.println("Elapsed time in nanoseconds : " + timeInNanoseconds);

        double timeInSeconds = (double) timeInNanoseconds / 1_000_000_000.0;
        System.out.println("Elapsed time in seconds : " + timeInSeconds);
        //System.out.println("Elapsed Time in seconds: "+ TimeUnit.SECONDS.convert(time, TimeUnit.NANOSECONDS));
    }

    public static void main(String[] args) {
        validateArguments(args);
        if (n < 100)
            run(true);//rulam efectiv aplicatia
        else
            runningTime();//calculam doar running time

    }
}
