//Savin Miruna, A5
//problema mi se pare ca seamana cu LIS (longest increasing subsequence)
//si o abordare eficienta cred ca este una cu programare dinamica
//asemanatoare cu ce am facut la Proiectarea algoritmilor anul trecut


public class Bonus {
    static int [] arrayOfLengths ;
    //ok deci functia asta practic construieste arrayul de secvente in felul urmator:
    // lNSEH(wordArray, index) = lNSEH(wordArray, j) + 1, doar daca wordarray[index] si wordarray[j] sunt vecini

    static int longestNeighbourSequenceEndingHere(String [] wordArray, int index){
        if(index == 0)
            return 1; //daca sunt pe pozitia 0, cea mai lunga secventa e 1 : cuvantul insusi
        int length = 1;

        for(int i = index - 1; i >= 0; i--)
        {
            if(Homework.checkIfWordsAreAdjacent(wordArray[i], wordArray[index]))
                length = Math.max(length, 1 + longestNeighbourSequenceEndingHere(wordArray, i));
        }

        return length;

    }

    //asta e varianta recursiva, care are complexitate mai mare
    static int longestNeighbourSequence(String[] wordArray)
    {
        int maxLength = 1; //macar un cuvant,pt ca toate sunt vecine cu ele insesi
        for(int i = 0; i < wordArray.length; i++)
        {
            maxLength = Math.max(maxLength, longestNeighbourSequenceEndingHere(wordArray, i));
        }
        return maxLength;
    }
    //pt varianta cu programare dinamica pur si simplu pun valorile intr-un array,nu le mai calculez de fiecare data
    static int dynamicApproach (String [] wordArray){
        arrayOfLengths = new int[Homework.n];
        arrayOfLengths[0] = 1;
        int maxLength = 1;
        for(int i = 1; i < wordArray.length; i++)
        {
            arrayOfLengths[i] = 1;
            for(int j = i; j >= 0; j--){

                if(Homework.checkIfWordsAreAdjacent(wordArray[i], wordArray[j]))
                {
                    if(arrayOfLengths[i] < arrayOfLengths[j] +1)
                        arrayOfLengths[i] = arrayOfLengths[j] +1;
                }
                if(arrayOfLengths[i] > maxLength)
                    maxLength = arrayOfLengths[i];
            }
        }
        return maxLength;
    }
    public static void main(String[] args) {
        Homework.run(args);
        System.out.println("Cea mai lunga secventa este: "+ longestNeighbourSequence(Homework.arrayOfWords));
        System.out.println("Cea mai lunga secventa (cu DP) este " + dynamicApproach(Homework.arrayOfWords));
    }
}
