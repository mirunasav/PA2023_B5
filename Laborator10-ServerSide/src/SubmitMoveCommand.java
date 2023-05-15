import java.io.IOException;

public class SubmitMoveCommand extends Command{
    @Override
    public void execute(ClientThread clientThread) throws IOException {
        int row = Integer.parseInt(clientThread.readClientRequest());
        int column = Integer.parseInt(clientThread.readClientRequest());
        //ar tb validate miscarile cumva, fac dupa
        GameServer.getInstance().getGame(clientThread.getGameID()).addPiece(row, column, clientThread.getId());
        //clientThread.sendClientResponse("MOVE_OK");
    }
}
