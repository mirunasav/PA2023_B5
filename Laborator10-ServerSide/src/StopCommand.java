import java.io.IOException;

public class StopCommand extends Command{
    @Override
    public void execute(ClientThread clientThread) throws IOException {
        String serverResponse = "Am primit comanda stop;";
        serverResponse += " Client thread se va inchide";
        clientThread.sendClientResponse(serverResponse);
        clientThread.getSocket().close();
        clientThread.isRunning = false;
    }
}
