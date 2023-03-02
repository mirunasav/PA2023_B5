//Savin Miruna
import static java.lang.System.exit;

public class Problem {
    //problema trebuie sa primeasca : locatii, roads intre ele
    Location [] locationsArray;
    Road [] roadsArray;
    Location firstLocation; //sursa
    Location secondLocation;//destinatie

    private boolean checkArgumentsValidity (Location [] locationsArray, Road [] roadsArray, Location firstLocation, Location secondLocation)
    {
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
        return true;
    }

    public Problem(Location[] locationsArray, Road[] roadsArray, Location firstLocation, Location secondLocation) {
        this.locationsArray = locationsArray;
        this.roadsArray = roadsArray;
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;

        //aici in loc sa fac verificate si sout, ar fi tb sa arunc o exceptie
        if(!this.checkArgumentsValidity(locationsArray,roadsArray,firstLocation,secondLocation))
        {
            System.out.println("Argumentele problemei nu sunt valide!");
            exit(2);
        }
    }

    //practic tb sa gasesc shortest path de la o sursa la o destinatie
    //o sa fac un algoritm al lui Dijkstra I guess de la firstLocation la secondLocation
    //bine cred ca o sa fac de la firstLocation la toate celelalte si apoi o sa vad cat ma ajunge
    //"costul" pana la destinatie

    //cum generez instante random ale problemei? generez locatii multe (1000), drumuri multe(5000), nume random de locatii
    //(generez cuvinte random adica)

}
