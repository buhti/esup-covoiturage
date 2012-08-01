package org.esupportail.covoiturage.domain;

import org.joda.time.DateTime;

public class RouteOccasional extends Route {

    private final DateTime wayOutDate;
    private final DateTime wayBackDate;

    public RouteOccasional(long id, Customer owner, int status, int seats, Location from, Location to,
            DateTime wayOutDate, DateTime wayBackDate) {

        super(id, owner, status, seats, from, to);

        this.wayOutDate = wayOutDate;
        this.wayBackDate = wayBackDate;
    }

    @Override
    boolean isRecurrent() {
        return false;
    }

    public DateTime getWayOutDate() {
        return wayOutDate;
    }

    public DateTime getWayBackDate() {
        return wayBackDate;
    }

}
