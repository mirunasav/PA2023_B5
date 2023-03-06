public class Bonus {

    /**
     * @param typeOfSolution alegem daca vrem sa calculam cea mai scurta / rapida solutie a problemei;
     *                       Functia genereaza o instanta random a problemei, pe urma o rezolva in functie de tipul de solutie cerut
     */
    public static void runRandomProblem(Utilities.SolutionType typeOfSolution) {
        //marcam inceputul momentului din care incepem sa cronometram durata rularii
        long begin = System.nanoTime();

        StringBuilder typeOfRoute = new StringBuilder();
        switch (typeOfSolution){
            case FASTEST:
                typeOfRoute.append("rapid");
                break;
            case SHORTEST:
                typeOfRoute.append("scurt");
        }

        System.out.println("La inceput, avem " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                + " memorie disponibila");
        Problem randomProblem = Utilities.generateProblemInstance(1500, 1000);


        System.out.println("Vom calcula drumul cel mai " + typeOfRoute + " de la "
                + randomProblem.firstLocation +
                " pana la " + randomProblem.secondLocation);

        Solution solution = new Solution(randomProblem);

        switch (typeOfSolution)
        {
            case FASTEST:
                System.out.println(solution.createSolution(typeOfSolution) + " ore");
                break;
            case SHORTEST:
                System.out.println(solution.createSolution(typeOfSolution) + " km");
        }

        //sfarsitul cronometrarii
        long end = System.nanoTime();
        long timeInNanoseconds = end - begin;
        System.out.println("Elapsed time in nanoseconds : " + timeInNanoseconds);

        double timeInSeconds = (double) timeInNanoseconds / 1_000_000_000.0;
        System.out.println("Elapsed time in seconds : " + timeInSeconds);
        System.out.println("La final, avem " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                + " memorie disponibila");
    }

    public static void main(String[] args) {
        City firstCity = new City("Iasi", new Integer[]{100, 100}, 300000);
        Airport firstAirport = new Airport("Aeroport Iasi", new Integer[]{103, 105}, 20);
        City secondCity = new City("Bucuresti", new Integer[]{10, 30}, 1_000_000);
        City thirdCity = new City("Sinaia", new Integer[]{50, 50}, 20_000);

        Highway fromIasiToIasiAirport = new Highway(15f, 80, new Location[]{firstCity, firstAirport});
        Express fromIasiToBucuresti = new Express(500f, 130, new Location[]{firstCity, secondCity});
        Highway fromIasiAirportToBucuresti = new Highway(460f, 60, new Location[]{firstAirport, secondCity});
        try {
            Problem problemInstance = new Problem(new Location[]{firstAirport, firstCity, secondCity},
                    new Road[]{fromIasiToIasiAirport, fromIasiAirportToBucuresti, fromIasiToBucuresti}, firstCity, secondCity);
            Solution solution = new Solution(problemInstance);
            System.out.println(solution.createSolution(Utilities.SolutionType.FASTEST) + " hours");
            System.out.println(solution.createSolution(Utilities.SolutionType.SHORTEST) + " km");
        } catch (InvalidArgumentsException exception) {
            System.out.println(exception.toString());
        }

        runRandomProblem(Utilities.SolutionType.FASTEST);
    }
}
