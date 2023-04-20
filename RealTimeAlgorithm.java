import java.util.*;

public abstract class RealTimeAlgorithm extends Algorithm {
    protected Queue<RealTimeRequest> rtSimulationQueue;
    protected TreeSet<RealTimeRequest> rtActiveQueue;
    protected int failedRT;

    public RealTimeAlgorithm() {
        initialState();
    }


    protected void copyRTQueue(ArrayList<RealTimeRequest> realTimeRequests) {
        for (RealTimeRequest request : realTimeRequests) {
            rtSimulationQueue.add(new RealTimeRequest(request.getArrivalTime(), request.getPosition(), request.getId(), request.getDeadline()));
        }
    }

    @Override
    protected void addToActiveQueue() {
        if (!simulationQueue.isEmpty()) {
            if (activeQueue.isEmpty() && rtActiveQueue.isEmpty())
                currentTime = Math.max(simulationQueue.peek().getArrivalTime(), currentTime);
            while (!simulationQueue.isEmpty() && simulationQueue.peek().getArrivalTime() <= currentTime) {
                activeQueue.add(simulationQueue.remove());
            }
        }
    }

    protected void addToActiveRTQueue() {
        if (!rtSimulationQueue.isEmpty()) {
            while (!rtSimulationQueue.isEmpty() && rtSimulationQueue.peek().getArrivalTime() <= currentTime) {
                rtActiveQueue.add(rtSimulationQueue.remove());
            }
        }
    }

    protected void calculateRTRequestInfo() {
        int headMovement = Math.abs((headPosition - activeRequest.getPosition()));
        headPosition = activeRequest.getPosition();
        totalHeadMovement += headMovement;
        currentTime += headMovement;

        if (activeRequest instanceof RealTimeRequest && currentTime > ((RealTimeRequest) activeRequest).getDeadline())
            failedRT++;

        rtActiveQueue.remove((RealTimeRequest) activeRequest);

        while (!rtActiveQueue.isEmpty() && rtActiveQueue.first().getDeadline() < currentTime) {
            failedRT++;
            rtActiveQueue.remove(rtActiveQueue.first());
        }
    }

    @Override
    protected void printResults() {
        super.printResults();
        System.out.println("Failed RT Requests: " + failedRT);
    }

    @Override
    protected void initialState() {
        super.initialState();
        rtSimulationQueue = new ArrayDeque<>();
        rtActiveQueue = new TreeSet<>(Comparator.comparing(RealTimeRequest::getDeadline).thenComparing(RealTimeRequest::getPosition).thenComparing(RealTimeRequest::getArrivalTime).thenComparing(RealTimeRequest::getId));
    }

    public abstract void startSimulation(ArrayList<Request> requests, ArrayList<RealTimeRequest> realTimeRequests);
}
