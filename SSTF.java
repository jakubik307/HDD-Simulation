import java.util.ArrayList;

public class SSTF extends Algorithm {
    @Override
    public void startSimulation(ArrayList<Request> requests) {
        initialState();
        copyQueue(requests);

        while (finishedRequests < Main.simulationSize) {
            addToActiveQueue();


            Request compareRequest = new Request(0, headPosition, 0);
            Request floorRequest = activeQueue.floor(compareRequest);
            Request ceilRequest = activeQueue.ceiling(compareRequest);

            if (floorRequest == null) {
                activeRequest = ceilRequest;
            } else if (ceilRequest == null) {
                activeRequest = floorRequest;
            } else if (Math.abs(floorRequest.position() - headPosition) < Math.abs(ceilRequest.position() - headPosition)) {
                activeRequest = floorRequest;
            } else {
                activeRequest = ceilRequest;
            }

            calculateRequestInfo();
        }

        printResults();
        System.out.println();
    }
}
