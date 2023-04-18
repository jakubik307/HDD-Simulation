public record Request(int arrivalTime, int position, int id) implements Comparable<Request> {

    @Override
    public int compareTo(Request o) {
        int d = Integer.compare(position, o.position());
        if (d == 0) d = Integer.compare(arrivalTime, o.arrivalTime());
        if (d == 0) d = Integer.compare(id, o.id());
        return d;
    }
}
