import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Main {
    public final static int simulationSize = 1000000;
    public final static int driveSize = 10000;
    public final static int avgTimePerRequest = 100;
    public final static int startingPosition = 53;
    public final static int starvationTime = 2 * driveSize;

    public final static int RTSize = (int) (0.3 * simulationSize);
    public final static int minDeadline = 3000;
    public final static int maxDeadline = 10000;

    private final static ArrayList<Request> originalRequests = new ArrayList<>();
    private final static ArrayList<RealTimeRequest> originalRTRequests = new ArrayList<>();

    public static void main(String[] args) {
//        test();

        simulation(originalRequests, originalRTRequests);
    }

    private static void generateRequestQueue() {
        for (int i = 0; i < simulationSize; i++) {
            originalRequests.add(generateRequest(i));
        }
        originalRequests.sort(Comparator.comparing(Request::getArrivalTime));

        for (int i = simulationSize; i < simulationSize + RTSize; i++) {
            Request request = generateRequest(i);
            Random random = new Random();
            originalRTRequests.add(new RealTimeRequest(request.getArrivalTime(), request.getPosition(), request.getId(), request.getArrivalTime() + random.nextInt(minDeadline, maxDeadline)));
        }
        originalRTRequests.sort(Comparator.comparing(RealTimeRequest::getArrivalTime));
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

    public static void simulation(ArrayList<Request> requests, ArrayList<RealTimeRequest> realTimeRequests) {
        Algorithm algorithm;
        RealTimeAlgorithm realTimeAlgorithm;
        generateRequestQueue();

        algorithm = new FCFS();
        algorithm.startSimulation(requests);

        algorithm = new SSTF();
        algorithm.startSimulation(requests);

        algorithm = new SCAN();
        algorithm.startSimulation(requests);

        algorithm = new CSCAN();
        algorithm.startSimulation(requests);

        realTimeAlgorithm = new EDF();
        realTimeAlgorithm.startSimulation(requests, realTimeRequests);

        realTimeAlgorithm = new FDSCAN();
        realTimeAlgorithm.startSimulation(requests, realTimeRequests);
    }

    @Deprecated
    public static void test() {
        ArrayList<Request> list = new ArrayList<>();

        list.add(new Request(0, 98, 0));
        list.add(new Request(0, 183, 1));
        list.add(new Request(0, 37, 2));
        list.add(new Request(0, 122, 3));
        list.add(new Request(0, 14, 4));
        list.add(new Request(0, 124, 5));
        list.add(new Request(0, 65, 6));
        list.add(new Request(0, 67, 7));

        simulation(list, originalRTRequests);
    }
}

/*
  Notes:
  FCFC performs incredibly poorly

  The rest performs quite similarly
  SSTF -> SCAN -> C-SCAN

  Total head movement ~= simulationSize * avgTimePerRequest
  Head jumps = simulationSize * avgTimePerRequest / driveSize
 */