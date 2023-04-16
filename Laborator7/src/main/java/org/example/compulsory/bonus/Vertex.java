package org.example.compulsory.bonus;

public class Vertex {
    private int id;

    private boolean visited = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vertex(int id){
        this.id = id;
    }

    /**
     * @return true, if the node was not previously visited
     */
    public boolean visit(){
        synchronized (this)
        {
            if(!this.visited){
                this.visited = true;
                return true;
            }
            else
                return false;
        }
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return id +
                " ";
    }
}
