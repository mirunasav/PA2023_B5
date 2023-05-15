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

    public void processServerResponse(String response) throws IOException {
        switch (ServerResponses.valueOf(response)) {
            case NEW_GAME_STARTED -> this.isInGame = true;
            case WRITE_GAME_ID -> {

                //aici comanda o sa fie un id pe care il trimit
                processCommands(writeToServer);
                this.processServerResponse(this.receiveResponse(readFromServer));
            }
            case INVALID_GAME_ID -> System.out.println("game id was invalid");
            case GAME_ID_OK -> {
                this.isInGame = true;
                System.out.println("game id was valid");
            }
            case GAME_STARTED ->  this.gameStarted = true;

        }
    }

    public void readGameTable() throws IOException {
        int numberOfLinesToRead = this.readIntFromServer(readFromServer);
        for (int i = 0; i < numberOfLinesToRead; i++) {
            this.receiveResponse(readFromServer);
        }
    }
    public void run() throws IOException {
        try (Socket socket = new Socket(serverAddress, PORT)) {
            writeToServer = new PrintWriter(socket.getOutputStream(), true);
            readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                if(this.isInGame && !this.gameStarted){
                    this.processServerResponse(this.receiveResponse(readFromServer));//astept raspuns
                    continue;
                }
               if(this.isInGame && this.gameStarted){
                   //citesc de la server cum arata tabla in acest moment;
                   readGameTable();
                   this.receiveResponse(readFromServer);//afisez cn trebuie sa mute
                   continue;
                 }
                //read command from keyboard and send to server
                processCommands(writeToServer);

                //get response from server and show on console
                ;
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
