import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.TreeSet;

public abstract class Algorithm {
    protected TreeSet<Request> activeQueue;
    protected Queue<Request> simulationQueue;
    protected long currentTime;
    protected long totalHeadMovement;
    protected int headPosition;
    protected int finishedRequests;
    protected Request activeRequest;
    protected long totalWaitingTime;
    protected long maxWaitingTime;
    protected int starvedRequests;

    public Algorithm() {
        initialState();
    }

    protected void copyQueue(ArrayList<Request> originalRequests) {
        for (Request request : originalRequests) {
            simulationQueue.add(new Request(request.getArrivalTime(), request.getPosition(), request.getId()));
        }
    }

    protected void addToActiveQueue() {
        if (!simulationQueue.isEmpty()) {
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

        long waitingTime = currentTime - activeRequest.getArrivalTime() + headMovement;
        totalWaitingTime += waitingTime;
        if (waitingTime > maxWaitingTime) maxWaitingTime = waitingTime;
        if (waitingTime > Main.starvationTime) starvedRequests++;

        activeQueue.remove(activeRequest);
        finishedRequests++;
    }

    protected void printResults() {
        System.out.println(getClass().getName());
        System.out.println("-------------");

        System.out.println("Total head movement: " + totalHeadMovement);
        System.out.println("Average waiting time: " + totalWaitingTime / Main.simulationSize);
        System.out.println("Max waiting time: " + maxWaitingTime);
        System.out.println("Starved requests: " + starvedRequests);

        System.out.println();
    }

    protected void initialState() {
        this.currentTime = 0;
        this.headPosition = Main.startingPosition;
        this.finishedRequests = 0;
        this.totalWaitingTime = 0;
        this.maxWaitingTime = 0;
        this.starvedRequests = 0;
        this.activeRequest = null;
        this.simulationQueue = new ArrayDeque<>();
        this.activeQueue = new TreeSet<>();
    }

    public abstract void startSimulation(ArrayList<Request> requests);
}
