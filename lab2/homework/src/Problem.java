import java.util.*;

import static java.lang.System.exit;

public class Problem {
    //problema trebuie sa primeasca : locatii, roads intre ele
    Location [] locationsArray;
    Road [] roadsArray;
    Location firstLocation; //sursa
    Location secondLocation;//destinatie
    //key : o locatie ; value : o lista de adiacenta
    Map<Location, ArrayList<Location> > locationsGraph;

    private boolean checkArgumentsValidity (Location [] locationsArray, Road [] roadsArray, Location firstLocation, Location secondLocation)
    {
        //verific sa nu fi scris doua locatii/doua roads la fel
        if(firstLocation.equals(secondLocation))
            return false;
        for(int i = 0; i < locationsArray.length-1; i++)
            for(int j = i+1; j < locationsArray.length; j++)
            {
                if(locationsArray[i].equals(locationsArray[j]))
                    return false;
            }
        for(int i = 0; i < roadsArray.length-1; i++)
            for(int j = i+1; j < roadsArray.length; j++)
            {
                if(roadsArray[i].equals(roadsArray[j]))
                    return false;
            }
        if(!Arrays.asList(locationsArray).contains(firstLocation) || !Arrays.asList(locationsArray).contains(secondLocation))
            return false;
        return true;
    }

    public boolean isProblemSolvable()
    {
//        fac un BFS
//        boolean firstLocationExistsOnARoad = false;
//        boolean secondLocationExistsOnARoad = false;
//        //vad daca am drum de la locatia 1 la locatia 2
//        //cum? - verific mai intai daca am un drum care incepe cu locatia 1 si unul care se termina cu locatia 2
//        for (Road road : this.roadsArray) {
//            if (road.getStartEndLocations()[0].equals(this.firstLocation)) {
//                firstLocationExistsOnARoad = true;
//                break;
//            }
//        }
//        if(!firstLocationExistsOnARoad)
//            return false;
//
//        for(Road road : this.roadsArray)
//            if(road.getStartEndLocations()[1].equals(this.secondLocation)) {
//                secondLocationExistsOnARoad = true;
//                break;
//            }
//        if(!secondLocationExistsOnARoad)
//            return false;
        LinkedList <Location> traversalQueue = new LinkedList<>();

        Map <Location, Boolean> visited = new HashMap<>();
        for(Location location : locationsArray)
        {
            visited.put(location, false);
        }

        Location currentNode = firstLocation;
        visited.replace(currentNode, true);
        traversalQueue.add(currentNode);

        while(traversalQueue.size()!=0)
        {
            currentNode = traversalQueue.poll();
            for(Location location : this.locationsGraph.get(currentNode))
                if(!visited.get(location))
                {
                    traversalQueue.add(location);
                    visited.put(location, true);
                }
        }

        for(Map.Entry<Location, Boolean> entry : visited.entrySet())
            System.out.println("key : "+ entry.getKey() + ", value : " + entry.getValue());
        return visited.get(secondLocation);
    }
    public Problem(Location[] locationsArray, Road[] roadsArray, Location firstLocation, Location secondLocation) {
        this.locationsArray = locationsArray;
        this.roadsArray = roadsArray;
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;

        if(!this.checkArgumentsValidity(locationsArray,roadsArray,firstLocation,secondLocation))
        {
            System.out.println("Argumentele problemei nu sunt valide!");
            exit(2);
        }
        this.createLocationGraph();
    }

    private void createLocationGraph ()
    {
        this.locationsGraph = new HashMap<>();

        for(Location location : this.locationsArray)
        {
            if(!this.locationsGraph.containsKey(location))
            {
                //creez lista de adiacenta
                ArrayList <Location> adjacencyList = new ArrayList<>();
                for(Road road : this.roadsArray) {
                    if (road.getStartEndLocations()[0].equals(location))
                        //daca sursa = location, nu are sens sa verificam daca destinatia este deja in lista de adiacenta
                        //daca ar fi , inseamna ca am avea acelasi road de doua ori which is not ok
                        adjacencyList.add(road.getStartEndLocations()[1]);

                }
                this.locationsGraph.put(location, adjacencyList);

            }
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
    //practic tb sa gasesc shortest path de la o sursa la o destinatie
    //o sa fac un algoritm al lui Dijkstra I guess de la firstLocation la secondLocation
    //bine cred ca o sa fac de la firstLocation la toate celelalte si apoi o sa vad cat ma ajunge
    //"costul" pana la destinatie

    //cum generez instante random ale problemei? generez locatii multe (1000), drumuri multe(5000), nume random de locatii
    //(generez cuvinte random adica)

}
