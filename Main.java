import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Main {
    public final static int simulationSize = 5;
    public final static int driveSize = 10000;
    public final static int avgTimePerRequest = 30;

    private final static ArrayList<Request> originalRequests = new ArrayList<>();

    public static void main(String[] args) {
        generateRequestQueue();

        for (Request request : originalRequests) {
            System.out.println(request.getArrivalTime() + " " + request.getPosition());
        }
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
