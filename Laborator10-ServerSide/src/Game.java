import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.Collections.swap;

public class Game {
    private static final int numberOfColumns = 5;

    private String fileName = "C:\\Users\\Miruna Savin\\IdeaProjects\\PA2023_B5\\Laborator10-ServerSide\\files\\GameState";

    private int[][] gameBoard;

    public int gameID;

    public int numberOfPlayers;

    int firstPlayerID;
    int secondPlayerID;
    int playerWhoMoves;

    public boolean hasStarted = false;
    /*private enum GameState{
        WHITE_WON{
            @Override
            public String toString(){
                return "White won";
            }
        },
        BLACK_WON{
            @Override
            public String toString(){
                return "Black won";
            }
        },
        OPEN{
            @Override
            public String toString(){
                return "Ongoing game";
            }
        }
    }
*/
    public Game(int gameID, int firstPlayerID) {
        this.gameID = gameID;
        this.firstPlayerID = firstPlayerID;

        System.out.println("New game generated!");
        this.gameBoard = new int[numberOfColumns][numberOfColumns];
        IntStream.range(0, numberOfColumns).forEach(i -> {
            IntStream.range(0, numberOfColumns).forEach(j -> {
                gameBoard[i][j] = 0;
            });
        });
        fileName += gameID;
        updateGame();
    }

    //tb sa fac cumva sa nu mai citesc doar pana la newline pt ca nu o sa pot vedea tabla
    public void printGameState() {
        StringBuilder stringBuilder = new StringBuilder();
        IntStream.range(0, numberOfColumns).forEach(i -> {
            IntStream.range(0, numberOfColumns).forEach(j -> System.out.println(gameBoard[i][j]));
            stringBuilder.append("\n");
        });
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(int[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    //0 for empty, 1 for white, 2 for black
    public void addPiece(int row, int column, int clientID) {
        int color = clientID == firstPlayerID ? 1 : 2;
        this.gameBoard[row][column] = color;
        //dau switch la jucatorul al carei ture este
        playerWhoMoves = playerWhoMoves == firstPlayerID ? secondPlayerID : firstPlayerID;
        updateGame();
    }

    public void addPlayer(int secondPlayerID) {
        this.secondPlayerID = secondPlayerID;
        this.numberOfPlayers++;
        this.startGame();
    }

    //overwrites the content of the file
    public void updateGame() {
        synchronized (fileName) {
            try {
                FileWriter fileWriter = new FileWriter(fileName);
                IntStream.range(0, numberOfColumns).forEach(i -> {
                    IntStream.range(0, numberOfColumns).forEach(j -> {
                        try {
                            fileWriter.write(Integer.toString(gameBoard[i][j]) + " ");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ;
                    });
                    try {
                        fileWriter.write("\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                fileWriter.close();
                System.out.println("Wrote to the file");
            } catch (IOException e) {
                System.out.println("an error occured while writing to the file: " + e.getMessage());
            }
        }

    }

    public List<String> getGameState() throws IOException {
        List<String> listOfRows = new LinkedList<>();

        synchronized (fileName) {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                listOfRows.add(line);
            }
            bufferedReader.close();
            return listOfRows;

        }
    }

    public void startGame() {
        Random rand = new Random();
        int firstPlayer = rand.nextInt(2) + 1;
        if(firstPlayer == 2){
            var temp = firstPlayerID;
            firstPlayerID = secondPlayerID;
            secondPlayerID = temp;
            playerWhoMoves = secondPlayerID;
            hasStarted = true;
            return;
        }
        playerWhoMoves = firstPlayerID;
        hasStarted  = true;
    }
}
