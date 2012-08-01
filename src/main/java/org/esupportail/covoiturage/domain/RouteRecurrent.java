package org.esupportail.covoiturage.domain;

import org.joda.time.DateTime;

public class RouteRecurrent extends Route {

    private DateTime startDate;
    private DateTime endDate;
    private String wayOutTime;
    private String wayBackTime;

    public RouteRecurrent(long id, Customer owner, int status, int seats, Location from, Location to,
            DateTime startDate, DateTime endDate, String wayOutTime, String wayBackTime) {

        super(id, owner, status, seats, from, to);

        this.startDate = startDate;
        this.endDate = endDate;
        this.wayOutTime = wayOutTime;
        this.wayBackTime = wayBackTime;
    }

    @Override
    boolean isRecurrent() {
        return true;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public String getWayOutTime() {
        return wayOutTime;
    }

    public String getWayBackTime() {
        return wayBackTime;
    }

}
