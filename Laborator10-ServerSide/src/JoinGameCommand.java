import java.io.IOException;

//NOT going to work if game id is invalid
public class JoinGameCommand extends Command {
    @Override
    public void execute(ClientThread clientThread) throws IOException {

        clientThread.sendClientResponse("WRITE_GAME_ID");
        int gameID = Integer.parseInt(clientThread.readClientRequest());
        if (GameServer.getInstance().gameExists(gameID) && GameServer.getInstance().getGame(gameID).numberOfPlayers<2) {
            clientThread.setGameID(gameID);
            clientThread.isInGame = true;

            GameServer.getInstance().joinGame(clientThread.getId(), gameID);
            clientThread.sendClientResponse("GAME_ID_OK");
            return;
        }
        clientThread.sendClientResponse("INVALID_GAME_ID");
    }
}
