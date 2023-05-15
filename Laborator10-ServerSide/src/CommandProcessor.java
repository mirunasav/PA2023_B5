import java.io.IOException;

public class CommandProcessor {
    private Command command;
    private ClientThread clientThread;

    public CommandProcessor(ClientThread clientThread) {
        this.clientThread = clientThread;//ca sa stiu asupra cui execut
    }


    public Command getCommand() {
        return command;
    }

    public void setCommand(String command) {
        switch (Commands.valueOf(command)) {
            case STOP -> this.command = new StopCommand();
            case NEW_GAME -> this.command = new NewGameCommand();
            case JOIN_GAME -> this.command = new JoinGameCommand();
        }
    }

    public void executeCommand() throws IOException {
        command.execute(clientThread);
    }
}
