import java.util.ArrayList;

public class FCFS extends Algorithm {
    @Override
    public void startSimulation(ArrayList<Request> requests) {
        copyQueue(requests);

        for (Request request : simulationQueue) {
            activeRequest = request;
            currentTime = Math.max(activeRequest.getArrivalTime(), currentTime);

            calculateRequestInfo();
        }

        printResults();
    }
}
