package org.esupportail.covoiturage.web.form;

import javax.validation.Valid;

import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.domain.RouteRecurrent;

import org.hibernate.validator.constraints.NotEmpty;

public class RouteRecurrentForm {

    @Valid
    private DateField startDate;

    @Valid
    private DateField endDate;

    @Valid
    private TimeField wayOutTime;

    @Valid
    private TimeField wayBackTime;

    @NotEmpty
    private int[] weekDays;

    public RouteRecurrentForm() {
        startDate = new DateField();
        endDate = new DateField();
        wayOutTime = new TimeField();
        wayBackTime = new TimeField();
    }

    public DateField getStartDate() {
        return startDate;
    }

    public DateField getEndDate() {
        return endDate;
    }

    public TimeField getWayOutTime() {
        return wayOutTime;
    }

    public TimeField getWayBackTime() {
        return wayBackTime;
    }

    public int[] getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(int[] weekDays) {
        this.weekDays = weekDays;
    }

    public void populate(Route route) {
        if (route instanceof RouteRecurrent) {
            RouteRecurrent r = (RouteRecurrent) route;
            startDate = new DateField(r.getStartDate());
            endDate = new DateField(r.getEndDate());
            wayOutTime = new TimeField(r.getWayOutTime());
            weekDays = r.getWeekDays();

            if (r.isRoundTrip()) {
                wayBackTime = new TimeField(r.getWayBackTime());
            }
        }
    }

}
