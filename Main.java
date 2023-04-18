import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Main {
    public final static int simulationSize = 10000;
    public final static int driveSize = 10000;
    public final static int avgTimePerRequest = 30;
    public final static int startingPosition = 53;
    public final static int starvationTime = 3 * driveSize;

    private final static ArrayList<Request> originalRequests = new ArrayList<>();

    public static void main(String[] args) {
        simulation(originalRequests);
    }

    private static void generateRequestQueue() {
        for (int i = 0; i < simulationSize; i++) {
            originalRequests.add(generateRequest(i));
        }
        originalRequests.sort(Comparator.comparing(Request::arrivalTime));
    }

    private static Request generateRequest(int id) {
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

        return new Request(arrivalTime, position, id);
    }

    public static void simulation(ArrayList<Request> list) {
        Algorithm algorithm;
        generateRequestQueue();

        algorithm = new FCFS();
        algorithm.startSimulation(list);

        algorithm = new SSTF();
        algorithm.startSimulation(list);

        algorithm = new SCAN();
        algorithm.startSimulation(list);

        algorithm = new CSCAN();
        algorithm.startSimulation(list);
    }

    public static void test() {
        ArrayList<Request> list = new ArrayList<>();

        list.add(new Request(0, 98, 0));
        list.add(new Request(0, 183, 1));
        list.add(new Request(0, 37, 2));
        list.add(new Request(0, 122, 3));
        list.add(new Request(0, 14, 4));
        list.add(new Request(0, 124, 5));
        list.add(new Request(0, 67, 6));
        list.add(new Request(0, 67, 7));

        simulation(list);
    }
}
