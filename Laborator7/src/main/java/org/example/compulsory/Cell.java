package org.example.compulsory;

import java.util.List;

public class Cell {
    List<Token> tokenList;
   private boolean visited;

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Cell(List<Token> tokenList) {
        this.tokenList = tokenList;
        visited = false;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }
}
