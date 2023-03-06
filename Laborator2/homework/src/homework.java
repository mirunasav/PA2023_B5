//Savin Miruna A5
public class homework {
    public static void main(String[] args) {
        //creating Locations & roads
        //first, creating the locations
        City firstCity = new City("Iasi", new Integer[]{100, 100}, 300000);
        Airport firstAirport = new Airport("Aeroport Iasi", new Integer[]{103, 105}, 20);
        City secondCity = new City("Bucuresti", new Integer[]{10, 30}, 1_000_000);
        City thirdCity = new City("Sinaia", new Integer[]{50, 50}, 20_000);

        Highway fromIasiToIasiAirport = new Highway(15f, 80, new Location[]{firstCity, firstAirport});
        Express fromIasiToBucuresti = new Express(500f, 130, new Location[]{firstCity, secondCity});
        Highway fromIasiAirportToBucuresti = new Highway(460f, 130, new Location[]{firstAirport, secondCity});

        try
        {
            Problem problemInstance = new Problem(new Location[]{firstAirport, firstCity, secondCity},
                    new Road[]{fromIasiToIasiAirport, fromIasiAirportToBucuresti}, firstCity, secondCity);
            try {
                problemInstance.isProblemSolvable();
                System.out.println("Problem is solvable!");
            } catch (InvalidArgumentsException exception) {
                System.out.println(exception.toString());
            }
        }
        catch(InvalidArgumentsException exception)
        {
            System.out.println(exception.toString());
        }

    }
}
