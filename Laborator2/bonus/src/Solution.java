//Savin Miruna

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;
import static java.lang.System.in;

/**
 * practic trebuie sa gasesc shortest path de la o sursa la o destinatie
 * o sa fac un algoritm al lui Dijkstra de la firstLocation la secondLocation
 * actually cred ca o sa fac de la firstLocation la toate celelalte si apoi o sa vad cat ma ajunge
 * "costul" pana la destinatie;
 * Apoi o sa generez instante random ale problemei si voi testa algoritmul;
 * cum generez instante random ale problemei? generez locatii multe (1000), drumuri multe(5000), nume random de locatii
 * (generez cuvinte random adica)
 */
public class Solution {
    /**
     * tb sa primeasca o instanta a problemei
     */
    private Problem instanceOfProblem;

    public Solution(Problem instanceOfProblem) {
        try{
            this.instanceOfProblem = new Problem(instanceOfProblem.getLocationsArray(),
                    instanceOfProblem.getRoadsArray(),
                    instanceOfProblem.getFirstLocation(),
                    instanceOfProblem.getSecondLocation() );
        }
        catch (InvalidArgumentsException exception)
        {
            System.out.println(exception.toString());
        }

    }

    public Problem getInstanceOfProblem() {
        return instanceOfProblem;
    }

    public void setInstanceOfProblem(Problem instanceOfProblem) {
        this.instanceOfProblem = instanceOfProblem;
    }

    /**
     * @param distancesFromSourceToLocations "vectorul" de distante de la sursa la fiecare locatie
     * @param shortestPathTree               nodurile care au fost deja parcurse
     * @return Locatia care are distanta minima de la sursa fata de unul din nodurile ce nu sunt in spt
     */
    public Location minimumDistance(Map<Location, Float> distancesFromSourceToLocations,
                                    Map<Location, Boolean> shortestPathTree) {

        float min = Float.MAX_VALUE;
        Location minimumDistanceLocation = null;

        for (Map.Entry<Location, Float> entry : distancesFromSourceToLocations.entrySet()) {
            if (!shortestPathTree.get(entry.getKey()) && entry.getValue() < min) {
                min = entry.getValue();
                minimumDistanceLocation = entry.getKey();
            }
        }
        return minimumDistanceLocation;
    }

    /**
     * @param typeOfSolution imi indica daca vreau cea mai rapida sau cea mai scurta cale intre doua puncte
     * @return o lista a locatiile ce formeaza cea mai buna ruta intre sursa si destinatie
     */
    public Float createSolution(Utilities.SolutionType typeOfSolution) {
        try {
            this.instanceOfProblem.isProblemSolvable();
        } catch (InvalidArgumentsException exception) {
            System.out.println("Problema nu e rezolvabila! " + exception.toString());
            exit(1);
        }
        if (typeOfSolution == Utilities.SolutionType.FASTEST)
            return createSolutionBasedOnType(Utilities.SolutionType.FASTEST);
        else
            return createSolutionBasedOnType(Utilities.SolutionType.SHORTEST);

    }

    /**
     * facem Dijkstra dar luam in considerare limitele de viteza
     * adica o sa contorizam timpii (length / speedLimit)
     *
     * @return locatiile ce fac parte din cea mai rapida ruta
     */
    private Float createSolutionBasedOnType(Utilities.SolutionType typeOfSolution) {
        Map<Location, ArrayList<PairOfRoadAndLocation>> locationsGraph = this.instanceOfProblem.getLocationsGraph();
        Location source = this.instanceOfProblem.getFirstLocation();

        Location destination = this.instanceOfProblem.getSecondLocation();
        /**
         * In loc de vector de distante, fac un map, pentru a putea avea Locatia - distanta
         */
        Map<Location, Float> distancesFromSourceToLocations = new HashMap<>();
        /**
         * in loc de un vector ce sa reprezinte nodurile din shortestPathTree,
         * folosesc un map pentru a avea Locatia - true , daca apartine spt,
         * sau Locatia - false, daca nu apartine spt
         */
        Map<Location, Boolean> shortestPathTree = new HashMap<>();

        //initializam toate distantele cu "infinit", si toate nodurile ca nevizitate
        for (Location location : this.instanceOfProblem.locationsArray)
            distancesFromSourceToLocations.put(location, Float.MAX_VALUE);
        for (Location location : this.instanceOfProblem.locationsArray)
            shortestPathTree.put(location, false);
        distancesFromSourceToLocations.replace(source, 0f); //distanta de la sursa la ea insasi e 0

        //gasim shortest path fata de toate nodurile
        for (Map.Entry<Location, Float> entry : distancesFromSourceToLocations.entrySet()) {
            Location minimumDistanceLocation = minimumDistance(distancesFromSourceToLocations, shortestPathTree);
            if(minimumDistanceLocation != null)
            {
                //marcam nodul ales ca si vizitat
                shortestPathTree.replace(minimumDistanceLocation, true);
                //modific distanta minima pentru nodurile adiacente celui ales
                ArrayList<PairOfRoadAndLocation> adjacencyListOfChosenNode = new ArrayList<>();
                adjacencyListOfChosenNode = this.instanceOfProblem.locationsGraph.get(minimumDistanceLocation);
                for (PairOfRoadAndLocation adjacentLocation : adjacencyListOfChosenNode) {
                    //daca: 1.nodul nu se afla in spt deja
                    //2. nodul este accesibil, adica nu are distanta "infinit"
                    //3. pot actualiza distanta minima de la nodul sursa la nodul curent
                    //atunci modific distanta minima
                    if (typeOfSolution == Utilities.SolutionType.SHORTEST) {
                        //daca e shortest, iau in considerare distanta
                        if (!shortestPathTree.get(adjacentLocation.getLocation())
                                && distancesFromSourceToLocations.get(adjacentLocation.getLocation()) != Integer.MAX_VALUE
                                && distancesFromSourceToLocations.get(minimumDistanceLocation)
                                + adjacentLocation.getPairOfLengthAndSpeedLimit().getLength()
                                < distancesFromSourceToLocations.get(adjacentLocation.getLocation())) {
                            distancesFromSourceToLocations.replace(adjacentLocation.getLocation(),
                                    distancesFromSourceToLocations.get(minimumDistanceLocation)
                                            + adjacentLocation.getPairOfLengthAndSpeedLimit().getLength());

                        }
                    } else //iau in considerare timpul
                    {
                        if (!shortestPathTree.get(adjacentLocation.getLocation())
                                && distancesFromSourceToLocations.get(adjacentLocation.getLocation()) != Integer.MAX_VALUE
                                && distancesFromSourceToLocations.get(minimumDistanceLocation)
                                + adjacentLocation.getPairOfLengthAndSpeedLimit().getLength() / adjacentLocation.getPairOfLengthAndSpeedLimit().getSpeedLimit()
                                < distancesFromSourceToLocations.get(adjacentLocation.getLocation())) {
                            distancesFromSourceToLocations.replace(adjacentLocation.getLocation(),
                                    distancesFromSourceToLocations.get(minimumDistanceLocation)
                                            + adjacentLocation.getPairOfLengthAndSpeedLimit().getLength()
                                            / adjacentLocation.getPairOfLengthAndSpeedLimit().getSpeedLimit());
                        }
                    }
                }
            }

        }
        return distancesFromSourceToLocations.get(instanceOfProblem.secondLocation);

    }

}
