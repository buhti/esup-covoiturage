package org.esupportail.covoiturage.domain;

public abstract class Route {

    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_DRIVER = 1;
    public static final int STATUS_PASSENGER = 2;

    private final long id;
    private final Customer owner;
    private final int status;
    private final int seats;
    private final Location from;
    private final Location to;

    protected Route(long id, Customer owner, int status, int seats, Location from, Location to) {
        this.id = id;
        this.owner = owner;
        this.status = status;
        this.seats = seats;
        this.from = from;
        this.to = to;
    }

    public long getId() {
        return id;
    }

    public Customer getOwner() {
        return owner;
    }

    public int getStatus() {
        return status;
    }

    public int getSeats() {
        return seats;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public abstract boolean isRecurrent();

}
