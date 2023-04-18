import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Main {
    public final static int simulationSize = 8;
    public final static int driveSize = 200;
    public final static int avgTimePerRequest = 30;
    public final static int startingPosition = 53;

    private final static ArrayList<Request> originalRequests = new ArrayList<>();

    public static void main(String[] args) {
        test();
    }

    private static void generateRequestQueue() {
        for (int i = 0; i < simulationSize; i++) {
            originalRequests.add(generateRequest());
        }
        originalRequests.sort(Comparator.comparing(Request::getArrivalTime).reversed());
    }

    private static Request generateRequest() {
        int position;
        int arrivalTime;
        Random random = new Random();

        //Generating timeToComplete
        double p = Math.random();

        if (p < 0.2) {
            position = random.nextInt(1, driveSize / 5);
        } else if (p < 0.4) {
            position = random.nextInt(driveSize / 5, 2 * driveSize / 5);
        } else if (p < 0.6) {
            position = random.nextInt(2 * driveSize / 5, 3 * driveSize / 5);
        } else if (p < 0.8) {
            position = random.nextInt(3 * driveSize / 5, 4 * driveSize / 5);
        } else {
            position = random.nextInt(4 * driveSize / 5, driveSize);
        }

        //Generating arrivalTime
        arrivalTime = random.nextInt(1, simulationSize * avgTimePerRequest);

        return new Request(arrivalTime, position);
    }

    public static void test() {
        Algorithm algorithm;
        ArrayList<Request> test = new ArrayList<>();

        test.add(new Request(0, 98));
        test.add(new Request(0, 183));
        test.add(new Request(0, 37));
        test.add(new Request(0, 122));
        test.add(new Request(0, 14));
        test.add(new Request(0, 124));
        test.add(new Request(0, 65));
        test.add(new Request(0, 67));

        algorithm = new FCFS();
        algorithm.startSimulation(test);

        algorithm = new SSTF();
        algorithm.startSimulation(test);

        algorithm = new SCAN();
        algorithm.startSimulation(test);

        algorithm = new CSCAN();
        algorithm.startSimulation(test);
    }
}
