import java.util.*;

public abstract class Algorithm {
    protected int currentTime;
    protected int totalHeadMovement;
    protected int headPosition;
    protected int finishedRequests;
    protected int previousRefreshTime;
    protected Request activeRequest;
    protected Queue<Request> simulationQueue;
    protected TreeSet<Request> activeQueue;

    public Algorithm() {
        this.currentTime = 0;
        this.headPosition = Main.startingPosition;
        this.finishedRequests = 0;
        this.previousRefreshTime = 0;
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
        while (simulationQueue.peek() != null && simulationQueue.peek().getArrivalTime() <= currentTime) {
            activeQueue.add(simulationQueue.remove());
        }
    }

    protected void calculateWaitingTime() {
        // TODO: 17/04/2023  
    }

    protected void printResults() {
        // TODO: 17/04/2023  
    }

    public abstract void startSimulation(ArrayList<Request> requests);
}
