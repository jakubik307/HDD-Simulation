import java.util.ArrayList;

public class SCAN extends Algorithm {
    private boolean isMovingForward = false;

    @Override
    public void startSimulation(ArrayList<Request> requests) {
        initialState();
        copyQueue(requests);

        while (finishedRequests < Main.simulationSize) {
            addToActiveQueue();

            Request compareRequest = new Request(0, headPosition, 0);
            if (isMovingForward) activeRequest = activeQueue.ceiling(compareRequest);
            else activeRequest = activeQueue.floor(compareRequest);

            if (activeRequest == null) {
                int headMovement;
                if (isMovingForward) {
                    headMovement = Main.driveSize - headPosition;
                    headPosition = Main.driveSize;
                } else {
                    headMovement = headPosition;
                    headPosition = 0;
                }
                totalHeadMovement += headMovement;
                currentTime += headMovement;
                isMovingForward = !isMovingForward;
            } else {
                calculateRequestInfo();
            }
        }

        printResults();
    }
}
