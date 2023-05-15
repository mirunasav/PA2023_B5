public class Run {
    public static void main(String[] args) {
        GameClient gameClient = new GameClient();
        try{
            gameClient.run();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
