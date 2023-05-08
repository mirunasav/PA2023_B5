import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 8100;

    public Server() throws IOException{
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                System.out.println("waiting for a client ...");
                Socket socket = serverSocket.accept();

                new ClientThread(socket).run();
            }
        } catch (IOException e) {
            System.err.println("Error in server " + e);
        }
    }

}
