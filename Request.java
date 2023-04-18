public class Request implements Comparable<Request> {

    private final int arrivalTime;
    private final int position;
    private final int id;

    public Request(int arrivalTime, int position, int id) {
        this.arrivalTime = arrivalTime;
        this.position = position;
        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Request o) {
        int d = Integer.compare(position, o.getPosition());
        if (d == 0) d = Integer.compare(arrivalTime, o.getArrivalTime());
        if (d == 0) d = Integer.compare(id, o.getId());
        return d;
    }
}
