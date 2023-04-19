import java.util.ArrayList;

public class CSCAN extends Algorithm {
    int headJumps;

    @Override
    public void startSimulation(ArrayList<Request> requests) {
        initialState();
        copyQueue(requests);

        while (finishedRequests < Main.simulationSize) {
            addToActiveQueue();

            Request compareRequest = new Request(0, headPosition, 0);
            activeRequest = activeQueue.ceiling(compareRequest);

            if (activeRequest == null) {
                int headMovement;
                headMovement = Main.driveSize - headPosition;
                headPosition = 0;
                totalHeadMovement += headMovement;
                currentTime += headMovement;
                headJumps++;
            } else {
                calculateRequestInfo();
            }
        }

        printResults();
        System.out.println();
    }

    @Override
    protected void printResults() {
        super.printResults();
        System.out.println("Head jumps: " + headJumps);
    }
}
