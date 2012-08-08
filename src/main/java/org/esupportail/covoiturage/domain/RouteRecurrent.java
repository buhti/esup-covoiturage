package org.esupportail.covoiturage.domain;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class RouteRecurrent extends Route {

    private DateTime startDate;
    private DateTime endDate;
    private LocalTime wayOutTime;
    private LocalTime wayBackTime;

    public RouteRecurrent(long id, Customer owner, boolean driver, int seats, Location from, Location to,
            int distance, DateTime startDate, DateTime endDate, LocalTime wayOutTime, LocalTime wayBackTime) {

        super(id, owner, driver, seats, from, to, distance);

        this.startDate = startDate;
        this.endDate = endDate;
        this.wayOutTime = wayOutTime;
        this.wayBackTime = wayBackTime;
    }

    @Override
    public boolean isRecurrent() {
        return true;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public LocalTime getWayOutTime() {
        return wayOutTime;
    }

    public LocalTime getWayBackTime() {
        return wayBackTime;
    }

}
