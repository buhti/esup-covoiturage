package org.esupportail.covoiturage.domain;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class RouteRecurrent extends Route {

    private final DateTime startDate;
    private final DateTime endDate;
    private final LocalTime wayOutTime;
    private final LocalTime wayBackTime;
    private final int[] weekDays;

    public RouteRecurrent(long id, Customer owner, boolean driver, boolean ladiesOnly, 
            int seats, Location from, Location to, int distance, boolean roundTrip, 
            DateTime startDate, DateTime endDate, LocalTime wayOutTime, LocalTime wayBackTime,
            int[] weekDays) {

        super(id, owner, driver, ladiesOnly, seats, from, to, distance, roundTrip);

        this.startDate = startDate;
        this.endDate = endDate;
        this.wayOutTime = wayOutTime;
        this.wayBackTime = wayBackTime;
        this.weekDays = weekDays;
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

    public int[] getWeekDays() {
        return weekDays;
    }

}
