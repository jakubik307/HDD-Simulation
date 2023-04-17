import java.util.ArrayList;

public class FCFS extends Algorithm {
    @Override
    public void startSimulation(ArrayList<Request> requests) {
        copyQueue(requests);

        for (Request request : simulationQueue) {
            currentTime = Math.max(request.getArrivalTime(), currentTime);

            int headMovement = Math.abs((headPosition - request.getPosition()));
            headPosition = request.getPosition();
            totalHeadMovement += headMovement;
            currentTime += headMovement;

            request.setWaitingTime(currentTime - request.getArrivalTime() + headMovement);
        }


        System.out.println("FCFS");
        printResults();
        System.out.println("----------");
    }
}
