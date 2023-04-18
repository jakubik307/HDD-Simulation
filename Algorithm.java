import java.util.*;

public abstract class Algorithm {
    protected final TreeSet<Request> activeQueue;
    protected int currentTime;
    protected int totalHeadMovement;
    protected int headPosition;
    protected int finishedRequests;
    protected Request activeRequest;
    protected Queue<Request> simulationQueue;

    public Algorithm() {
        this.currentTime = 0;
        this.headPosition = Main.startingPosition;
        this.finishedRequests = 0;
        this.activeRequest = null;
        this.simulationQueue = new ArrayDeque<>();
        this.activeQueue = new TreeSet<>(Comparator.comparing(Request::getPosition));
    }

    protected void copyQueue(ArrayList<Request> originalRequests) {
        simulationQueue = new ArrayDeque<>();
        for (Request request : originalRequests) {
            simulationQueue.add(new Request(request.getArrivalTime(), request.getPosition()));
        }
    }

    protected void addToActiveQueue() {
        if (!simulationQueue.isEmpty() && activeQueue.isEmpty()) {
            currentTime = Math.max(simulationQueue.peek().getArrivalTime(), currentTime);
            while (simulationQueue.peek() != null && simulationQueue.peek().getArrivalTime() <= currentTime) {
                activeQueue.add(simulationQueue.remove());
            }
        }
    }

    protected void calculateRequestInfo() {
        int headMovement = Math.abs((headPosition - activeRequest.getPosition()));
        headPosition = activeRequest.getPosition();
        totalHeadMovement += headMovement;
        currentTime += headMovement;

        activeRequest.setWaitingTime(currentTime - activeRequest.getArrivalTime() + headMovement);
        activeQueue.remove(activeRequest);
        finishedRequests++;
    }

    protected void printResults() {
        System.out.println(getClass().getName());
        System.out.println("-------------");

        System.out.println("Total head movement: " + totalHeadMovement);
        // TODO: 18/04/2023  

        System.out.println();
    }

    public abstract void startSimulation(ArrayList<Request> requests);
}
