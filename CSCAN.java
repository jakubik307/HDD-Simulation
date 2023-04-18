import java.util.ArrayList;

public class CSCAN extends Algorithm {
    int headJumps;
    @Override
    public void startSimulation(ArrayList<Request> requests) {
        copyQueue(requests);

        while (finishedRequests < Main.simulationSize) {
            if (!simulationQueue.isEmpty() && activeQueue.isEmpty()) {
                currentTime = Math.max(simulationQueue.peek().getArrivalTime(), currentTime);
                addToActiveQueue();
            }

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
                int headMovement = Math.abs((headPosition - activeRequest.getPosition()));
                headPosition = activeRequest.getPosition();
                totalHeadMovement += headMovement;
                currentTime += headMovement;

                activeRequest.setWaitingTime(currentTime - activeRequest.getArrivalTime() + headMovement);
                activeQueue.remove(activeRequest);
                finishedRequests++;
            }
        }


        System.out.println("C-SCAN");
        printResults();
        System.out.println("----------");
    }
}
