package org.esupportail.covoiturage.domain;

import org.joda.time.DateTime;

public class RouteOccasional extends Route {

    private final DateTime wayOutDate;
    private final DateTime wayBackDate;

    public RouteOccasional(long id, Customer owner, boolean driver, boolean ladiesOnly, 
            int seats, Location from, Location to, int distance, boolean roundTrip, 
            DateTime wayOutDate, DateTime wayBackDate) {

        super(id, owner, driver, ladiesOnly, seats, from, to, distance, roundTrip);

        this.wayOutDate = wayOutDate;
        this.wayBackDate = wayBackDate;
    }

    @Override
    public boolean isRecurrent() {
        return false;
    }

    public DateTime getWayOutDate() {
        return wayOutDate;
    }

    public DateTime getWayBackDate() {
        return wayBackDate;
    }

}
