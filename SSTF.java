import java.util.ArrayList;

public class SSTF extends Algorithm {
    @Override
    @SuppressWarnings("ConstantConditions")
    public void startSimulation(ArrayList<Request> requests) {
        copyQueue(requests);

        while (finishedRequests < Main.simulationSize) {
            if (!simulationQueue.isEmpty() && activeQueue.isEmpty()) {
                currentTime = Math.max(simulationQueue.peek().getArrivalTime(), currentTime);
                addToActiveQueue();
            }

            Request compareRequest = new Request(0, headPosition);
            Request floorRequest = activeQueue.floor(compareRequest);
            Request ceilRequest = activeQueue.ceiling(compareRequest);

            if (floorRequest == null) {
                activeRequest = ceilRequest;
            } else if (ceilRequest == null) {
                activeRequest = floorRequest;
            } else if (Math.abs(floorRequest.getPosition() - headPosition) < Math.abs(ceilRequest.getPosition() - headPosition)) {
                activeRequest = floorRequest;
            } else {
                activeRequest = ceilRequest;
            }

            int headMovement = Math.abs((headPosition - activeRequest.getPosition()));
            headPosition = activeRequest.getPosition();
            totalHeadMovement += headMovement;
            currentTime += headMovement;

            activeRequest.setWaitingTime(currentTime - activeRequest.getArrivalTime() + headMovement);
            activeQueue.remove(activeRequest);
            finishedRequests++;
        }

        System.out.println("SSTF");
        printResults();
        System.out.println("----------");
    }
}
