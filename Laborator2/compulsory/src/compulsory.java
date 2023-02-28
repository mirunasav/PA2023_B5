//Savin Miruna
public class compulsory {
    public static void main(String[] args) {
        //creating Locations & roads
        //first, creating the locations
        Location firstCity = new Location("Iasi", LocationType.CITY,new Integer[] {100,100});
        Location firstAirport = new Location("Aeroport Iasi", LocationType.CITY,new Integer[] {103,105});
        Location secondCity = new Location("Bucuresti", LocationType.CITY,new Integer[] {10,30});

        Road fromIasiToIasiAirport = new Road(RoadType.EXPRESS,15f, 80, new Location[] {firstCity, firstAirport});
        Road fromIasiToBucuresti = new Road(RoadType.HIGHWAY,500f, 130, new Location[] {firstCity, secondCity});
        Road fromIasiAirportToBucuresti = new Road(RoadType.HIGHWAY,460f, 130, new Location[] {firstAirport, secondCity});

        System.out.println(firstAirport.toString() + "\n"+firstCity.toString() + "\n" + fromIasiToIasiAirport.toString());


    }
}
