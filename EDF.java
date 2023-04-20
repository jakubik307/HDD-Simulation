import java.util.ArrayList;

public class EDF extends RealTimeAlgorithm {
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
                    // TODO: 20/04/2023 BUUUUUG
                    headPosition = 0;
                    totalHeadMovement += headMovement;
                    currentTime += headMovement;
                    headJumps++;
                } else {
                    calculateRequestInfo();
                }
            } else {
                //EDF
                activeRequest = rtActiveQueue.first();
                calculateRTRequestInfo();
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
}
