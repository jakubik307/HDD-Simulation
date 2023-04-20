import java.util.ArrayList;
import java.util.Iterator;

public class FDSCAN extends RealTimeAlgorithm {
    int headJumps;

    @Override
    public void startSimulation(ArrayList<Request> requests, ArrayList<RealTimeRequest> realTimeRequests) {
        initialState();
        copyQueue(requests);
        copyRTQueue(realTimeRequests);

        while (finishedRequests < Main.simulationSize) {
            addToActiveQueue();
            addToActiveRTQueue();

            if (rtActiveQueue.isEmpty()) {
                //Normal C-SCAN
                Request compareRequest = new Request(0, headPosition, 0);
                activeRequest = activeQueue.ceiling(compareRequest);

                if (activeRequest == null) {
                    int headMovement;
                    headMovement = Main.driveSize - headPosition;
                    headPosition = 0;
                    totalHeadMovement += headMovement;
                    currentTime += headMovement;
                    headJumps++;
                } else {
                    calculateRequestInfo();
                }
            } else {
                //FD-SCAN
                Iterator<RealTimeRequest> iterator = rtActiveQueue.iterator();
                while (iterator.hasNext()) {
                    RealTimeRequest request = iterator.next();
                    if (request.getDeadline() - request.getArrivalTime() < Math.abs(request.getPosition() - headPosition)) {
                        failedRT++;
                        activeQueue.remove(request);
                        iterator.remove();
                    }
                }

                if (!rtActiveQueue.isEmpty()) {
                    Request target = rtActiveQueue.first();

                    do {
                        Request compareRequest = new Request(0, headPosition, 0);
                        if (target.getPosition() >= headPosition) activeRequest = activeQueue.ceiling(compareRequest);
                        else activeRequest = activeQueue.floor(compareRequest);

                        if(activeRequest != null) {
                            if (activeRequest instanceof RealTimeRequest) calculateRTRequestInfo();
                            else calculateRequestInfo();
                        }
                    } while (activeRequest != null && !target.equals(activeRequest));
                }
            }
        }

        printResults();
        System.out.println();
    }

    @Override
    public void startSimulation(ArrayList<Request> requests) {
        CSCAN cscan = new CSCAN();
        cscan.startSimulation(requests);
    }

    @Override
    protected void addToActiveRTQueue() {
        if (!rtSimulationQueue.isEmpty()) {
            while (!rtSimulationQueue.isEmpty() && rtSimulationQueue.peek().getArrivalTime() <= currentTime) {
                RealTimeRequest request = rtSimulationQueue.remove();
                rtActiveQueue.add(request);
                activeQueue.add(request);
            }
        }
    }

    @Override
    protected void calculateRTRequestInfo() {
        int headMovement = Math.abs((headPosition - activeRequest.getPosition()));
        headPosition = activeRequest.getPosition();
        totalHeadMovement += headMovement;
        currentTime += headMovement;

        if (activeRequest instanceof RealTimeRequest && currentTime > ((RealTimeRequest) activeRequest).getDeadline())
            failedRT++;

        activeQueue.remove(activeRequest);
        rtActiveQueue.remove((RealTimeRequest) activeRequest);
    }
}
