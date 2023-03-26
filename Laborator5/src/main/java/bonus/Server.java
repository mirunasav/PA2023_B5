package bonus;
import org.example.compulsory.CatalogManager;
import org.example.compulsory.Compulsory;

import java.net.*;
import java.io.*;

public class Server {

    //aici ma apucasem sa fac conexiune intre codul asta de java si un cod in python ca sa fac o interfata grafica
    //but i gave up
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        Socket clientSocket = serverSocket.accept();
        System.out.println("Connected to Python client");

        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if(inputLine.equals("minimumVertexDegree")){
                    Compulsory.main(new String[]{});
                }
                    System.out.println("Received message from Python client: " + inputLine);
                String response = "ACK: " + inputLine;
                out.println(response);
                System.out.println("Sent response to Python client: " + response);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port 12345 or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
