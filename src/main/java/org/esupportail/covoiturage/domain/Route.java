package org.esupportail.covoiturage.domain;

public abstract class Route {

    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_DRIVER = 1;
    public static final int STATUS_PASSENGER = 2;

    private final long id;
    private final Customer owner;
    private final boolean driver;
    private final boolean ladiesOnly;
    private final int seats;
    private final Location from;
    private final Location to;
    private final int distance;
    private final boolean roundTrip;

    protected Route(long id, Customer owner, boolean driver, boolean ladiesOnly, 
            int seats, Location from, Location to, int distance, boolean roundTrip) {
        this.id = id;
        this.owner = owner;
        this.driver = driver;
        this.ladiesOnly = ladiesOnly;
        this.seats = seats;
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.roundTrip = roundTrip;
    }

    public long getId() {
        return id;
    }

    public Customer getOwner() {
        return owner;
    }

    public boolean isDriver() {
        return driver;
    }

    public boolean isLadiesOnly() {
        return ladiesOnly;
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

    public int getDistance() {
        return distance;
    }

    public boolean isRoundTrip() {
        return roundTrip;
    }

    public abstract boolean isRecurrent();

}
