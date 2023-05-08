import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;

public class Client {
    public static final String serverAddress = "127.0.0.1";
    public static final int PORT = 8100;
    private static Scanner scanner = new Scanner(System.in);

    public static void processCommands(PrintWriter writeToServer,BufferedReader readFromServer) {
        //read command from keyboard
        String command = readCommand();

        //send command to server
        writeToServer(command, writeToServer);
    }
    public static void receiveResponse(BufferedReader reader) throws IOException {
        String response = reader.readLine();
        System.out.println(response);
    }
    public static void writeToServer(String command, PrintWriter writer ){
        writer.println(command);
    }
    public static String readCommand() {
        String command = scanner.nextLine();
        if(command.equals("exit")){
            System.out.println("Client will exit");
            exit(0);
        }
        return command;
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket(serverAddress, PORT);
             PrintWriter writeToServer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
            while(true){
                //read command from keyboard and send to server
                processCommands(writeToServer, readFromServer);

                //get response from server and show on console
                Client.receiveResponse(readFromServer);
            }//tb sa bag cv eroare in caz ca serverul se inchide random

        } catch (IOException e) {
            System.err.println("No server listening... " + e);
        }

    }
}
