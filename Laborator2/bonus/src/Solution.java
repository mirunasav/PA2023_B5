//Savin Miruna

import java.util.ArrayList;
import java.util.Map;

import static java.lang.System.exit;

public class Solution {
    /**
     * tb sa primeasca o instanta a problemei
     */
    private Problem instanceOfProblem;

    public Solution(Problem instanceOfProblem) {
        this.instanceOfProblem = instanceOfProblem;
    }

    public Problem getInstanceOfProblem() {
        return instanceOfProblem;
    }

    public void setInstanceOfProblem(Problem instanceOfProblem) {
        this.instanceOfProblem = instanceOfProblem;
    }

    public int minimumDistance(int[] vectorOfDistances, boolean[] shortestPathTree) {
        //gasesc nodul cu cea mai mica distanta fata de sursa
        int min = Integer.MAX_VALUE, minimum_index = -1;

        for (int index = 0; index < vectorOfDistances.length; index++) {

            if (shortestPathTree[index] == false && vectorOfDistances[index] < min) {
                min = vectorOfDistances[index];
                minimum_index = index;
            }
        }
        return minimum_index;
    }

    public ArrayList<Location> createSolution(Functions.SolutionType typeOfSolution) {
        if (this.instanceOfProblem.isProblemSolvable()) {
            if (typeOfSolution == Functions.SolutionType.FASTEST)
                return createFastestSolution();
            else
                return createShortestSolution();
        }
        else
        {
            System.out.println("problema nu e rezolvabila!");
            exit(1);
        }
        return null;
    }

    public ArrayList<Location> createFastestSolution() {
        //facem dijkstra dar luam in considerare limitele de viteza i guess
    }

    public ArrayList<Location> createShortestSolution() {
        //facem dijkstra si luam in considerare distantele
        Map<Location, ArrayList<Location>> locationsGraph = this.instanceOfProblem.getLocationsGraph();
        Location source = this.instanceOfProblem.getFirstLocation();

        Location destination = this.instanceOfProblem.getSecondLocation();
        int[] vectorOfDistances = new int[this.instanceOfProblem.getLocationsArray().length];
        boolean[] shortestPathTree = new boolean[this.instanceOfProblem.getLocationsArray().length];

        //initializam toate distantele cu "infinit", si toate nodurile ca nevizitate
        for (int distance : vectorOfDistances)
            distance = Integer.MAX_VALUE;
        for (boolean isNodeVisited : shortestPathTree)
            isNodeVisited = false;
    }
}
