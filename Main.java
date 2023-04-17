import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Main {
    public final static int simulationSize = 5;
    public final static int driveSize = 10000;
    public final static int avgTimePerRequest = 30;
    public final static int startingPosition = 53;

    private final static ArrayList<Request> originalRequests = new ArrayList<>();

    public static void main(String[] args) {
        ArrayList<Request> test = new ArrayList<>();

        test.add(new Request(0, 98));
        test.add(new Request(1, 183));
        test.add(new Request(2, 37));
        test.add(new Request(3, 122));
        test.add(new Request(4, 14));
        test.add(new Request(5, 124));
        test.add(new Request(6, 65));
        test.add(new Request(7, 67));

        FCFS fcfs = new FCFS();
        fcfs.startSimulation(test);

        System.out.println(fcfs.totalHeadMovement);
        System.out.println(fcfs.currentTime);
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
}
