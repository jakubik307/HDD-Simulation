import java.util.ArrayList;

@SuppressWarnings("ALL")
public class CSCAN extends Algorithm {
    int headJumps;

    @Override
    public void startSimulation(ArrayList<Request> requests) {
        copyQueue(requests);

        while (finishedRequests < Main.simulationSize) {
            addToActiveQueue();

            Request compareRequest = new Request(0, headPosition);
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
    }
}
