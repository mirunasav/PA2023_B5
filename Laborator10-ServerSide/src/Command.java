import java.io.IOException;

public abstract class Command {
   protected ClientThread clientThread;
   public abstract void execute(ClientThread clientThread) throws IOException;

}
