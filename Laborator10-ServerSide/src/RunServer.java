import java.io.IOException;

public class RunServer {
    public static void main(String[] args)throws IOException {
       GameServer.getInstance().run();
    }
}
