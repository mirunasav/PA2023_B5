import java.io.IOException;

public class NewGameCommand extends Command{
    @Override
    public void execute(ClientThread clientThread) throws IOException {
        GameServer.getInstance().createNewGame(clientThread.getId());
        //trimit apoi catre client cum arata tabla
        clientThread.sendClientResponse("NEW_GAME_STARTED");
        clientThread.isInGame = true;
    }
}
