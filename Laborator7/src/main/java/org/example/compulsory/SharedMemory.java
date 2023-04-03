package org.example.compulsory;

import java.util.*;
import java.util.stream.IntStream;

public class SharedMemory {
    private final LinkedList<Token> tokenList;
    public SharedMemory(int n){

        int newNumber = n*n*n;
        this.tokenList = new LinkedList<>(List.of(IntStream.range(0,newNumber)
                .mapToObj(i->new Token(i))
                .toArray(Token[]::new)));
        this.shuffleMemory();

    }

    public synchronized List<Token> extractTokens(int number){
        List <Token> extracted = new ArrayList<>();
        for(int i = 0; i < number; i++){
            if(tokenList.isEmpty())
                break;
            extracted.add(tokenList.pollFirst());
        }
        return extracted;
    }
    private void shuffleMemory(){
       Collections.shuffle(this.tokenList);
    }
}
