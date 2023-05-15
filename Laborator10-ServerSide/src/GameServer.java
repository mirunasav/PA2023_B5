import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class GameServer {
    public static final int PORT = 8100;
    private static int idCounter = 1;
    private static int gameIdCounter = 1;

    public static GameServer instance;

    private Map<Integer, Game> gameMap;//map de la un id la un joc; cand primesc request de

    //la un thread cu id-ul asta, stiu ce joc modifica
    private List<ClientThread> clientThreadList;
    private List<Game> listOfGames;

    public static GameServer getInstance() throws IOException {
        if (instance == null)
            instance = new GameServer();
        return instance;
    }

    public void createNewGame(int clientThreadID) {
        if (gameMap.containsKey(clientThreadID))
            throw new RuntimeException("This client is already in a game");
        var gameToAdd = new Game(gameIdCounter++, clientThreadID);
        gameMap.put(clientThreadID, gameToAdd);
        this.listOfGames.add(gameToAdd);

        Objects.requireNonNull(clientThreadList.stream()
                        .filter(thread -> thread.getId() == clientThreadID)
                        .findFirst()
                        .orElse(null))
                .setGameID(gameIdCounter - 1);
    }

    //adds client thread and gameid to gamemap; updates nr o players in target game
    public void joinGame(int clientThreadID, int gameID) {
        var targetGame = this.listOfGames.stream().filter(game -> game.gameID == gameID).toList();
        this.gameMap.put(clientThreadID, targetGame.get(0));
        targetGame.get(0).addPlayer(clientThreadID);
        startGame(gameID);
        targetGame.get(0).startGame();
    }

    public Game getGame(int gameID) {
        return this.gameMap.get(gameID);
    }

    public boolean gameExists(int gameID) {
        return this.listOfGames
                .stream()
                .filter(game -> game.gameID == gameID)
                .toList().size() > 0; //incearca sa isi dea seama daca exista un game cu acest id
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setReuseAddress(true);

            while (true) {
                // System.out.println("waiting for a client ...");
                Socket clientSocket = serverSocket.accept();

                //System.out.println("New client!");

                ClientThread clientThread = new ClientThread(clientSocket, idCounter++);
                clientThreadList.add(clientThread);
                new Thread(clientThread).start();
            }
        } catch (IOException e) {
            System.err.println("Error in server " + e);
        }
    }

    public void startGame(int gameID){
        var clientThreads = this.gameMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().gameID == gameID)
                .map(Map.Entry :: getKey).toList();

        for(int i = 0; i < clientThreads.size(); i++){
            //transmit fiecarui client thread ca am inceput jocul
            for (ClientThread clientThread : clientThreadList)
                if(clientThread.getId() == clientThreads.get(i)) {
                    try {
                        clientThread.announceThatTheGameHasStarted();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    private GameServer() {
        gameMap = new HashMap<>();
        clientThreadList = new LinkedList<>();
        listOfGames = new LinkedList<>();
    }

}
