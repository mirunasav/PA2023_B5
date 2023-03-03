import java.util.ArrayList;
import java.util.Arrays;

public class Utilities {
    public enum SolutionType {
        FASTEST,
        SHORTEST
    }

    /**
     * @param numberOfRoads     numarul de drumuri din problema
     * @param numberOfLocations numarul de locatii din problema.
     *                          Cum generez instante random ale problemei?
     *                          1. generez Locatiile ->numele, tipul, coordonatele
     *                          2. generez drumurile ->tipul,locatia 1 si locatia 2 intre care se gasesc, si la length verific mereu sa fie > minValue
     */
    public static Problem generateProblemInstance(int numberOfRoads, int numberOfLocations) {
        Location[] locationsArray = generateLocations(numberOfLocations);
        Road[] roadsArray = generateRoads(numberOfRoads, locationsArray);
        int[] firstLastLocations = generateTwoIndexes(locationsArray.length);
        try {
            Problem randomProblem = new Problem(locationsArray, roadsArray, locationsArray[firstLastLocations[0]], locationsArray[firstLastLocations[1]]);
            try {
                randomProblem.isProblemSolvable();
                return randomProblem;
            } catch (InvalidArgumentsException exception) {
                return generateProblemInstance(numberOfRoads, numberOfLocations);//mai incerc iar sa generez pana gasesc o problema rezolvabila
            }
        } catch (InvalidArgumentsException exception) {
            //daca problema nu e valida, mai incerc sa generez alta pana cand gasesc una valida
           return generateProblemInstance(numberOfRoads, numberOfLocations);
        }

    }

    /**
     * generez un numar intre -180 si 180 pt longitudine
     * si unul intre -90 si 90 pt latitudine
     *
     * @return coordonatele
     */
    public static Integer[] generateCoordinates() {
        //o sa le fac numere consecutive ca sa fie dragut si simplu
        //generez un numar intre -180 si 180 pt longitudine
        // si unul intre -90 si 90 pt latitudine

        int maxLongitudinalValue = 180;
        int minLongitudinalValue = -180;
        int maxLatitudinalValue = 90;
        int minLatitudinalValue = -90;

        int longitude = (int) (Math.random() * (maxLongitudinalValue - minLongitudinalValue + 1) + minLongitudinalValue);
        int latitude = (int) (Math.random() * (maxLatitudinalValue - minLatitudinalValue + 1) + minLatitudinalValue);

        return new Integer[]{longitude, latitude};
    }

    /**
     * @return random length of road
     */
    public static Float generateLength() {
        Float length = (float) (Math.random() * 200);
        return length;
    }

    /**
     * @return random speedLimit
     */
    public static Integer generateSpeedLimit() {
        Integer speedLimit = (int) (Math.random() * (130 - 50 + 1) + 50);
        return speedLimit;
    }

    public static int[] generateTwoIndexes(int maxNumber) {
        int firstIndex = (int) (Math.random() *( maxNumber));
        int lastIndex = (int) (Math.random() * (maxNumber));
        return new int[]{firstIndex, lastIndex};
    }

    /**
     *
     * @return true, daca lungimea drumului e valida.
     * Motivul pentru care nu am folosit cealalta functie de tipul asta scrisa deja
     * e pentru ca arunca exceptie si voiam sa fac mai simplu
     */
    public static boolean checkLengthValidity (float length, Location firstLocation, Location secondLocation)
    {
        double minimumLength;

        int firstX = firstLocation.getCoordinates()[0];
        int secondX = secondLocation.getCoordinates()[0];
        int firstY =firstLocation.getCoordinates()[1];
        int secondY =secondLocation.getCoordinates()[1];

        minimumLength = Math.sqrt(Math.pow(firstX - secondX, 2) + Math.pow(firstY - secondY, 2));

        if (length < minimumLength)
            return false;
        return true;
    }
    public static Road[] generateRoads(int numberOfRoads, Location[] locationsArray) {
        Road[] roadsArray = new Road[numberOfRoads];

        for (int i = 0; i < numberOfRoads; i++) {
            boolean validity = false;
            while(!validity)
            {
                Integer speedLimit = generateSpeedLimit();
                Float length = generateLength();
                int[] indexesOfLocations = generateTwoIndexes(locationsArray.length);
                validity = checkLengthValidity(length,locationsArray[indexesOfLocations[0]], locationsArray[indexesOfLocations[1]]);
                if(validity)
                    roadsArray[i] = new Highway(length, speedLimit, new Location[]{locationsArray[indexesOfLocations[0]], locationsArray[indexesOfLocations[1]]});
                //o sa fac toate drumurile autostrazi ca sa nu mai stau sa generez acum tipuri de drum, oricum nu schimba cu nimic
            }
        }
        return roadsArray;
    }

    public static Location[] generateLocations(int numberOfLocations) {
        Location[] locationsArray = new Location[numberOfLocations];
        for (int i = 0; i < numberOfLocations; i++) {
            String name = generateName(3);
            int typeOfLocationIndex = (int) (Math.random() * 3); //generez un numar de la 1 la 3 pt ca am 3 tipuri de locatii
            Integer[] coordinates = generateCoordinates();
            switch (typeOfLocationIndex) {
                case 1: //Airport
                   locationsArray[i] = new Airport(name, coordinates, 2);//puteam sa generez random numberOfTerminals
                    //dar nu schimba cu nimic
                    break;
                case 2://City
                    locationsArray[i] = new City(name, coordinates, 1000000);
                    break;
                case 3://GasStation
                    locationsArray[i] = new GasStation(name, coordinates);
                    break;
                default:
                    locationsArray[i] =  new Airport(name, coordinates, 2);
            }
        }

        return locationsArray;
    }

    /**
     *
     * @param numberOfLetters numar maxim de litere al fiecarui nume
     * @return un nume random
     */
    public static String generateName(int numberOfLetters) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < numberOfLetters; i++) {
            int random = (int) (Math.random() * alphabet.length());
            word.append(alphabet.charAt(random));
        }
        return word.toString();
    }
}
