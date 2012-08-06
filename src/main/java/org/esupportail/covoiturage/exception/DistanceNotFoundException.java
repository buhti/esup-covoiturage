package org.esupportail.covoiturage.exception;

import org.esupportail.covoiturage.domain.Location;

@SuppressWarnings("serial")
public class DistanceNotFoundException extends Exception {

    private final Location origin;
    private final Location destination;

    public DistanceNotFoundException(Location origin, Location destination) {
        super("Unable to retrieve the distance between " + origin.getAddress() + " and " + destination.getAddress());
        this.origin = origin;
        this.destination = destination;
    }

    public Location getOrigin() {
        return origin;
    }

    public Location getDestination() {
        return destination;
    }

}
