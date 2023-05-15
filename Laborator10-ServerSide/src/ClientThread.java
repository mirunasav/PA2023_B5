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
        String request = reader.readLine();
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

    public void announceThatTheGameHasStarted() throws IOException {
        sendClientResponse("GAME_STARTED");
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
            sendClientResponse("OPPONENT'S_TURN");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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
                    sendGameState();
                    //write which player's turn it is
                    if (writeWhoHasToMove())//this client's turn to move
                    {
                        executeRequest(readClientRequest());
                    }

                    continue;
                    //execute client request: if it is the client's turn to move, execute it
                }
                if(isInGame){
                    continue;
                }
                executeRequest(readClientRequest());
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
