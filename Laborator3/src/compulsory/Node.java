package compulsory;

public interface Node {
    //if I define a variable here it will be public, static and final
    //I can ommit writing the public modifiers since all abstract, default & static methods
    //of an interface are implicitly public
    String getName ();

    static void bar() {

    }

    Integer getNodeImportance();
    void printConnections();

}
