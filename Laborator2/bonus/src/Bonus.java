public class Bonus {

    public static void runRandomProblem() {
        Problem randomProblem = Utilities.generateProblemInstance(200, 100);
        System.out.println("Vom calcula drumul cel mai scurt de la "
                + randomProblem.firstLocation +
                " pana la " + randomProblem.secondLocation);
        Solution solution = new Solution(randomProblem);
        System.out.println(solution.createSolution(Utilities.SolutionType.SHORTEST) + " km");
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
        runRandomProblem();
    }
}
