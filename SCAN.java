import java.util.ArrayList;

public class SCAN extends Algorithm {
    private boolean isMovingForward = false;

    @Override
    public void startSimulation(ArrayList<Request> requests) {
        copyQueue(requests);

        while (finishedRequests < Main.simulationSize) {
            if (!simulationQueue.isEmpty() && activeQueue.isEmpty()) {
                currentTime = Math.max(simulationQueue.peek().getArrivalTime(), currentTime);
                addToActiveQueue();
            }

            Request compareRequest = new Request(0, headPosition);
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
                int headMovement = Math.abs((headPosition - activeRequest.getPosition()));
                headPosition = activeRequest.getPosition();
                totalHeadMovement += headMovement;
                currentTime += headMovement;

                activeRequest.setWaitingTime(currentTime - activeRequest.getArrivalTime() + headMovement);
                activeQueue.remove(activeRequest);
                finishedRequests++;
            }
        }


        System.out.println("SCAN");
        printResults();
        System.out.println("----------");
    }
}
