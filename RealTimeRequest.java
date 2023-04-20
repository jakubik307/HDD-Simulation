public class RealTimeRequest extends Request {
    private final int deadline;

    public RealTimeRequest(int arrivalTime, int position, int id, int deadline) {
        super(arrivalTime, position, id);
        this.deadline = deadline;
    }

    public int getDeadline() {
        return deadline;
    }
}
