package org.esupportail.covoiturage.domain;

public abstract class Route {

    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_DRIVER = 1;
    public static final int STATUS_PASSENGER = 2;

    private final long id;
    private final Customer owner;
    private final boolean driver;
    private final int seats;
    private final Location from;
    private final Location to;
    private final String distance;

    protected Route(long id, Customer owner, boolean driver, int seats, Location from, Location to, String distance) {
        this.id = id;
        this.owner = owner;
        this.driver = driver;
        this.seats = seats;
        this.from = from;
        this.to = to;
        this.distance = distance;
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

    public int getSeats() {
        return seats;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public String getDistance() {
        return distance;
    }

    public abstract boolean isRecurrent();

}
