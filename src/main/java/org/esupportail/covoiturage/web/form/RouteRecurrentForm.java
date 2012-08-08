package org.esupportail.covoiturage.web.form;

import javax.validation.Valid;

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
    private int[] weekDay;

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

    public int[] getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int[] weekDay) {
        this.weekDay = weekDay;
    }

}
