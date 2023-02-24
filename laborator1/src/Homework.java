import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Homework {

    static int n;
    static int p;
    static Character[] alphabet;
    static boolean[][] adjacencyMatrix;
    static String[] arrayOfWords = new String[n];
    static boolean shouldIPrint;

    static boolean validateArguments(String[] arguments) {
        try {
            Homework.n = Integer.parseInt(arguments[0]);//arunca eroare daca nu e int
            Homework.p = Integer.parseInt(arguments[1]);
        } catch (NumberFormatException e) {
            System.out.println("The arguments you have written are not numbers!");
        }

        //acum verific daca citesc doar litere, nu si cuvinte sau cifre
        Set<Character> alphabetSet = new HashSet<Character>();

        for (int i = 2; i < arguments.length; i++) {
            if (arguments[i].length() > 1)
                return false;
            char newCharacter = arguments[i].charAt(0);
            if (!Character.isLetter(newCharacter))
                return false;
            alphabetSet.add(newCharacter);
        }

        Homework.alphabet = alphabetSet.toArray(new Character[0]);
        return true;
    }

    static void printAlphabet()
    {
        System.out.print("The alphabet is: ");
        for(char i : Homework.alphabet)
            System.out.print(i+" ");
        System.out.println();
    }

    static void printWords(String [] arrayOfWords)
    {
        System.out.println("The words are: ");
        for(String word : arrayOfWords)
            System.out.println(word);
    }
    static String [] createWords ()
    {
        String[] arrayOfWords = new String[n];
        for (int i = 0; i<n; i++) //facem n cuvinte
        {
            StringBuilder word = new StringBuilder();
            for(int j = 0; j < p; j++) //cu cate p litere
            {
                int random = (int) (Math.random()*alphabet.length );//formula : math.random()*(max-min+1)+min pt numere intre min si max inclusiv
                word.append(alphabet[random]);
            }
            arrayOfWords[i] = word.toString();
        }
        return arrayOfWords;
    }

    static void createAdjacencyMatrix()
    {
        adjacencyMatrix = new boolean [n][n];

        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j<n; j++)
            {
                if(i==j)
                    adjacencyMatrix[i][j] = true;
                if (checkIfWordsAreAdjacent(arrayOfWords[i], arrayOfWords[j]))
                {
                    adjacencyMatrix[i][j] = true;
                    adjacencyMatrix[j][i] = true;
                }
                else
                {
                    adjacencyMatrix[i][j] = false;
                    adjacencyMatrix[j][i] = false;
                }
            }
        }
    }

    static boolean checkIfWordsAreAdjacent(String first, String second)
    {
        for(int i = 0; i < first.length(); i++)
        {
            for(int j = 0; j < second.length(); j++)
                if(first.charAt(i) == second.charAt(j))
                    return true;
        }
        return false;
    }

    static void printMatrix()
    {
        for(int i = 0; i < n; i++)
        {
            System.out.println(Arrays.toString(adjacencyMatrix[i]));
        }
    }

    static void createNeighbours ()
    {
        ArrayList[] allNeighbours = new ArrayList[n];

        for (int i = 0; i < n ; i++)
        {
            allNeighbours[i] = new ArrayList<String> ();
        }
        for (int i = 0; i < arrayOfWords.length; i++)
        {
            for (int j = 0; j < arrayOfWords.length; j++)
            {
                if(i!=j && adjacencyMatrix[i][j])
                    allNeighbours[i].add(arrayOfWords[j]);
            }
        }

        if(shouldIPrint) {
            for (int i = 0; i < arrayOfWords.length; i++) {
                System.out.println("vecinii cuvantului " + arrayOfWords[i] + " sunt:");
                for (Object word : allNeighbours[i])
                    System.out.println(word);
            }
        }
    }
    static void run(String [] args)
    {
        int numberOfArguments = args.length;
        if (numberOfArguments < 3) //cel putin cele doua numere si o litera
        {
            System.out.println("Not enough arguments!");
        }

        int n = Integer.parseInt(args[0]);//arunca eroare daca nu e int
        int p = Integer.parseInt(args[1]);
        if (!validateArguments(args))
            System.out.println("The arguments are not valid!");
        shouldIPrint = n < 100;

        if(shouldIPrint)
            printAlphabet();

        arrayOfWords = createWords();

        if(shouldIPrint)
            printWords(arrayOfWords);

        createAdjacencyMatrix();
        if(shouldIPrint)
            printMatrix();

        createNeighbours();

    }
    public static void main(String[] args) {
        long begin = System.nanoTime();

        run(args);

        long end = System.nanoTime();
        long timeInNanoseconds = end-begin;
        System.out.println();
        System.out.println("Elapsed Time in nanoseconds: "+timeInNanoseconds);

        double timeInSeconds = (double) timeInNanoseconds / 1_000_000_000.0;
        System.out.println("Elapsed time in seconds: "+ timeInSeconds);
        //System.out.println("Elapsed Time in seconds: "+ TimeUnit.SECONDS.convert(time, TimeUnit.NANOSECONDS));

    }
}