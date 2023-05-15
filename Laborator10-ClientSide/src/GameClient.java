import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;

public class GameClient {
    public final String serverAddress = "127.0.0.1";
    public final int PORT = 8100;
    private Scanner scanner = new Scanner(System.in);
    private PrintWriter writeToServer;
    private BufferedReader readFromServer;
    private boolean isInGame = false;
    private boolean gameStarted = false;

    public void processCommands(PrintWriter writeToServer) {
        //read command from keyboard
        String command = readCommand();

        //send command to server
        writeToServer(command, writeToServer);
    }

    public String receiveResponse(BufferedReader reader) throws IOException {
        String response = reader.readLine();
        System.out.println(response);
        return response;
    }

    public int readIntFromServer(BufferedReader reader) throws IOException {
        int intFromServer = Integer.parseInt(reader.readLine());
        System.out.println("the matrix has " + intFromServer + " lines");
        return intFromServer;
    }


    public void writeToServer(String command, PrintWriter writer) {
        writer.println(command);
    }

    public String readCommand() {
        String command = scanner.nextLine();
        if (command.equals("exit")) {
            System.out.println("Client will exit");
            exit(0);
        }
        return command;
    }

    public boolean processServerResponse(String response) throws IOException {
        switch (ServerResponses.valueOf(response)) {
            case NEW_GAME_STARTED -> {
                this.isInGame = true;
                return true;
            }
            case WRITE_GAME_ID -> {

                //aici comanda o sa fie un id pe care il trimit
                processCommands(writeToServer);
                this.processServerResponse(this.receiveResponse(readFromServer));
                return true;
            }
            case INVALID_GAME_ID -> {
                System.out.println("game id was invalid");
                return true;
            }

            case GAME_ID_OK -> {
                this.isInGame = true;
                System.out.println("game id was valid");
                return true;

            }
            case GAME_STARTED ->  {
                this.gameStarted = true;
                this.isInGame = true;
                return true;
            }

            case YOUR_TURN -> {
                System.out.println("Your turn to move! write a move, specified by a row (0-4) and a column(0-4)");
                //daca e tura ta, scrii o comanda
                processCommands(writeToServer);
                processCommands(writeToServer);
                return true;
            }
            case NOT_YOUR_TURN -> {
                System.out.println("Your opponent's turn!");
                return true;
            }
            case GAME_ENDED-> {
                System.out.println("game has ended");
                this.isInGame = false;
                return false;
            }
            case GAME_ONGOING -> {
                System.out.println("Game ongoing");
                return true;
            }
            case NEXT_MOVE -> {
                System.out.println("next round!");
                return true;
            }
            default -> {
                return true;
            }
        }
    }

    public void readGameTable() throws IOException {
        int numberOfLinesToRead = this.readIntFromServer(readFromServer);
        for (int i = 0; i < numberOfLinesToRead; i++) {
            this.receiveResponse(readFromServer);
        }
    }

    public void runInGame() throws IOException {
        //ce se intampla cand esti in game?
        // primesti game state
        //citesti cn urmeaza sa mute
        //daca e randul tau, scrii comanda de la tastatura si o trimiti la server

        //read if the game has ended or not: true if it is still going; else, false
        while(this.processServerResponse(this.receiveResponse(readFromServer))){
            readGameTable();
            this.processServerResponse(this.receiveResponse(readFromServer));//o sa scrie cine joaca
            this.processServerResponse(this.receiveResponse(readFromServer));//astept raspuns ca s-a terminat mutarea
        }

    }
    public void run() throws IOException {//ASTA CU JOIN E OK, DAR ALA CU NEW GAME MAI ASTEAPTA idk de ce
        try (Socket socket = new Socket(serverAddress, PORT)) {
            writeToServer = new PrintWriter(socket.getOutputStream(), true);
            readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                if(this.isInGame && this.gameStarted)
                {
                    runInGame();
                }
                if(this.isInGame){
                    this.processServerResponse(this.receiveResponse(readFromServer));//astept raspuns
                    continue;
                }

                //cand inca nu esti in vreun joc, scrii comanda, trimiti la server, procesezi raspunsul

                //read command from keyboard and send to server
                processCommands(writeToServer);

                //get response from server and show on console;
                this.processServerResponse(this.receiveResponse(readFromServer));
            }//tb sa bag cv eroare in caz ca serverul se inchide random

        } catch (IOException e) {
            System.err.println("No server listening... " + e);
        } finally {
            writeToServer.close();
            readFromServer.close();
        }
    }

}
