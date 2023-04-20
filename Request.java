public class Request implements Comparable<Request> {
    private final int arrivalTime;
    private final int position;
    private final int id;

    public Request(int arrivalTime, int position, int id) {
        this.arrivalTime = arrivalTime;
        this.position = position;
        this.id = id;
    }

    @Override
    public int compareTo(Request o) {
        int d = Integer.compare(position, o.getPosition());
        if (d == 0) d = Integer.compare(arrivalTime, o.getArrivalTime());
        if (d == 0) d = Integer.compare(id, o.getId());
        return d;
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
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Request) obj;
        return this.arrivalTime == that.arrivalTime && this.position == that.position && this.id == that.id;
    }
}
