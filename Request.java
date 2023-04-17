public class Request {

    private final int arrivalTime;
    private final int position;
    private int waitingTime;

    public Request(int arrivalTime, int position) {
        this.arrivalTime = arrivalTime;
        this.position = position;
        this.waitingTime = 0;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getPosition() {
        return position;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
}
