import java.util.*;

import static java.lang.System.exit;

/**
 * Problema trebuie sa primeasca drept argumente
 * locatii, drumurile dintre ele, o locatie sursa si una destinatie
 */
public class Problem {

    Location[] locationsArray;
    Road[] roadsArray;
    Location firstLocation; //sursa
    Location secondLocation;//destinatie
    /**
     * key : o locatie ; value : o lista de adiacenta
     * lista de adiacenta arata astfel : map intre o locatie, si drumul ce duce la acea locatie;
     * din drum ma intereseaza doar lungimea & speedLimit, deci o sa aleg sa stochez un Pair cu aceste valori
     */
    Map<Location, ArrayList<PairOfRoadAndLocation>> locationsGraph;

    public Location[] getLocationsArray() {
        return locationsArray;
    }

    public void setLocationsArray(Location[] locationsArray) {
        this.locationsArray = locationsArray;
    }

    public Road[] getRoadsArray() {
        return roadsArray;
    }

    public void setRoadsArray(Road[] roadsArray) {
        this.roadsArray = roadsArray;
    }

    public Location getFirstLocation() {
        return firstLocation;
    }

    public void setFirstLocation(Location firstLocation) {
        this.firstLocation = firstLocation;
    }

    public Location getSecondLocation() {
        return secondLocation;
    }

    public void setSecondLocation(Location secondLocation) {
        this.secondLocation = secondLocation;
    }

    Map<Location, ArrayList<PairOfRoadAndLocation>> getLocationsGraph() {
        return locationsGraph;
    }

    public void setLocationsGraph(Map<Location, ArrayList<PairOfRoadAndLocation>> locationsGraph) {
        this.locationsGraph = locationsGraph;
    }

    /**
     * @throws InvalidArgumentsException daca datele problemei nu sunt valide
     */
    private void checkArgumentsValidity(Location[] locationsArray, Road[] roadsArray,
                                        Location firstLocation, Location secondLocation)
            throws InvalidArgumentsException {

        if (firstLocation.equals(secondLocation))
            throw new InvalidArgumentsException("Locatia sursa si cea destinatie sunt identice!");

        for (int i = 0; i < locationsArray.length - 1; i++)
            for (int j = i + 1; j < locationsArray.length; j++) {
                if (locationsArray[i].equals(locationsArray[j]))
                    throw new InvalidArgumentsException("Ai pus aceeasi locatie de doua ori!");
            }

        for (int i = 0; i < roadsArray.length - 1; i++)
            for (int j = i + 1; j < roadsArray.length; j++) {
                if (roadsArray[i].equals(roadsArray[j]))
                    throw new InvalidArgumentsException("Ai pus acelasi drum de doua ori!");
            }

        if (!Arrays.asList(locationsArray).contains(firstLocation)
                || !Arrays.asList(locationsArray).contains(secondLocation))
            throw new InvalidArgumentsException("Una dintre locatiile sursa / destinatie nu se afla in argumentele date!");
    }

    /**
     * Idee algoritm: fac un BFS incepand din nodul sursa
     * si verific ca la final nodul destinatie sa fie marcat ca vizitat
     */
    public void isProblemSolvable() throws InvalidArgumentsException {
        LinkedList<Location> traversalQueue = new LinkedList<>();

        Map<Location, Boolean> visited = new HashMap<>();
        for (Location location : locationsArray) {
            visited.put(location, false);
        }

        Location currentNode = firstLocation;
        visited.replace(currentNode, true);
        traversalQueue.add(currentNode);
        //pentru fiecare nod , ii pun vecinii nevizitati in traversalQueue
        //apoi marchez vecinii ca si vizitati
        while (traversalQueue.size() != 0) {
            currentNode = traversalQueue.poll();
            for (PairOfRoadAndLocation pairOfRoadAndLocation :
                    this.locationsGraph.get(currentNode))
                if (!visited.get(pairOfRoadAndLocation.getLocation())) {
                    traversalQueue.add(pairOfRoadAndLocation.getLocation());
                    visited.put(pairOfRoadAndLocation.getLocation(), true);
                }
        }

        //for (Map.Entry<Location, Boolean> entry : visited.entrySet())
          //  System.out.println("key : " + entry.getKey() + ", value : " + entry.getValue());
        if (!visited.get(secondLocation))
            throw new InvalidArgumentsException("Problema nu e rezolvabila pentru ca nu pot ajunge din sursa in destinatie!");
    }

    public Problem(Location[] locationsArray, Road[] roadsArray, Location firstLocation, Location secondLocation) throws InvalidArgumentsException {
        this.locationsArray = locationsArray;
        this.roadsArray = roadsArray;
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;

        try {
            this.checkArgumentsValidity(locationsArray, roadsArray, firstLocation, secondLocation);
            this.createLocationGraph();
        } catch (InvalidArgumentsException exception) {
            throw new InvalidArgumentsException("Argumentele problemei nu sunt valide!" + exception.toString());
        }
    }

    /**
     * Creeaza un graf in care fiecare nod reprezinta o locatie
     * si o muchie intre doua noduri reprezinta drum intre cele doua locatii
     * cred ca o sa fie nevoie sa am key : locatie -> ArrayList<Map <Road, Location>>
     * adica fiecare element din lista de adiacenta a fiecarei locatie va fi o combinatie dintre drumul care duce la locatie
     * si locatia in sine
     */
    private void createLocationGraph() {
        this.locationsGraph = new HashMap<>();
        for (Location location : this.locationsArray) {
            ArrayList<PairOfRoadAndLocation> adjacencyList = new ArrayList<>();
            locationsGraph.put(location, adjacencyList);
        }

        for (Location location : this.locationsArray) {
            ArrayList<PairOfRoadAndLocation> adjacencyList = locationsGraph.get(location);
            for (Road road : this.roadsArray) {
                if (road.getStartEndLocations()[0].equals(location))
                //daca sursa = location, nu are sens sa verificam daca destinatia este deja in lista de adiacenta
                //daca ar fi , inseamna ca am avea acelasi road de doua ori which is not ok
                {
                    PairOfRoadAndLocation pairOfRoadAndLocation = new PairOfRoadAndLocation(road.getPairOfLengthAndSpeedLimit(), road.getStartEndLocations()[1]);
                    adjacencyList.add(pairOfRoadAndLocation);
                }
            }
            this.locationsGraph.put(location, adjacencyList);
        }
    }


    @Override
    public String toString() {
        return "Problem{" +
                "locationsArray=" + Arrays.toString(locationsArray) +
                ", roadsArray=" + Arrays.toString(roadsArray) +
                ", firstLocation=" + firstLocation +
                ", secondLocation=" + secondLocation +
                ", locationsGraph=" + locationsGraph +
                '}';
    }


}
