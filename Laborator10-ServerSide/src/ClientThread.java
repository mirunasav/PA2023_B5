import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket socket = null;
    protected BufferedReader reader;
    protected PrintWriter writer;
    private CommandProcessor commandProcessor;

    boolean isRunning = true;
    private int gameID;
    private int id;

    private int numberOfMovesInGame = 0;
    public boolean isInGame = false;
    public boolean hasGameStarted = false;

    public int getGameID() {
        return gameID;
    }

    public Game game;

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClientThread(Socket socket, int id) {
        this.socket = socket;
        this.id = id;
    }

    private void setCommandProcesser() {
        this.commandProcessor = new CommandProcessor(this);
    }

    public String readClientRequest() throws IOException {
        var request = reader.readLine();
        System.out.println("Client " + this.getId() + " has requested " + request);
        return request;
    }

    public void processClientRequest(String request) {

    }

    public void sendClientResponse(String response) throws IOException {
        writer.println(response);
    }

    public boolean executeRequest(String request) throws IOException {
        commandProcessor.setCommand(request);
        commandProcessor.executeCommand();
        //sendClientResponse( "Am primit comanda : " + request);
        return true;
    }

    public void executeMove()throws IOException{
        commandProcessor.setCommand("SUBMIT_MOVE");
        commandProcessor.executeCommand();
    }
    public void announceThatTheGameHasStarted() throws IOException {
        sendClientResponse("GAME_STARTED");
        this.hasGameStarted = true;
    }

    //sends the number of rows to be sent and then the actual rows to the client
    public void sendGameState() {
        try {
            var rowOfGameMatrix = GameServer.getInstance().getGame(gameID).getGameState();
            sendClientResponse(Integer.toString(rowOfGameMatrix.size()));
            for (String ofGameMatrix : rowOfGameMatrix) sendClientResponse(ofGameMatrix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean writeWhoHasToMove() {
        try {
            var playerWhoHasToMove = GameServer.getInstance().getGame(gameID).playerWhoMoves;
            if (playerWhoHasToMove == this.id) {
                sendClientResponse("YOUR_TURN");
                return true;
            }
            sendClientResponse("NOT_YOUR_TURN");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void runInGame() throws IOException {
        //trimit mesaj: se mai joaca sau nu?
        if (GameServer.getInstance().getGame(gameID).hasEnded) {
            sendClientResponse("GAME_ENDED");//trimit si castigatorul
            return;
        } else
            sendClientResponse("GAME_ONGOING");
        sendGameState();
        if (writeWhoHasToMove()) {
            //this client's turn to move
            executeMove();
            sendClientResponse("NEXT_MOVE");
        } else {
            //wait for update from server; get move sau cv de genul? sau cum ar tb ca client  thread -> server si tot asa cumva
            while (true) {
                if (GameServer.getInstance().getGame(gameID).numberOfMoves != numberOfMovesInGame) {
                    numberOfMovesInGame++;
                   if( GameServer.getInstance().getGame(gameID).hasEnded)
                       sendClientResponse("GAME_ENDED");
                   else
                       sendClientResponse("NEXT_MOVE");
                    break;
                }
            }
        }

    }

    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.setCommandProcesser();
            boolean haveISentOutput = false;
            // get request from the input stream
            while (isRunning) {
                if (isInGame && GameServer.getInstance().getGame(gameID).hasStarted) {
                    runInGame();
                }
                //daca nu sunt in game, execut request de la client
                //daca sunt in game dar jocul nu a inceput, nu fac nimic
                else {
                    if (!isInGame) {
                        executeRequest(readClientRequest());
                        continue;
                    }
                    while (true) {
                        //astept sa inceapa jocul
                        if (isInGame && GameServer.getInstance().getGame(gameID).hasStarted)
                            break;
                    }

                }

            }

        } catch (IOException e) {
            System.err.println("Communication error..." + e);
        } finally {
            try {
                socket.close();
                reader.close();
                writer.close();
            } catch (IOException e) {
                System.err.println("error at closing socket");
            }
        }
    }
}
