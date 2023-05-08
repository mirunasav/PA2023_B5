import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.System.exit;

public class ClientThread extends Thread{
    private Socket socket = null;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public String readClientRequest(BufferedReader reader) throws IOException {
        String request = reader.readLine();
        System.out.println("Client has requested " + request);
        return request;
    }

    public void sendClientResponse(PrintWriter writer, String response) throws IOException {
        writer.println(response);
    }

    public boolean executeRequest(PrintWriter write, String request) throws IOException {
        String response = "Am primit comanda : " + request;
        if (request.equals("stop")) {
            response += "; Client thread se va inchide";
            sendClientResponse(write, response);
            return false;
        }
        sendClientResponse(write, "Am primit comanda : " + request);
        return true;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            // get request from the input stream
            while (true) {
                String request = readClientRequest(in);
                if(!executeRequest(out, request))
                    return;
            }

        } catch (IOException e) {
            System.err.println("Communication error..." + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("error at closing socket");
            }
        }
    }
}
