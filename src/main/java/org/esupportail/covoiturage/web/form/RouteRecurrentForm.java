package org.esupportail.covoiturage.web.form;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class RouteRecurrentForm {

    @Valid
    private DateField startDate;

    @Valid
    private DateField endDate;

    @NotNull
    @Pattern(regexp = "([0-1][0-9]|2[0-3]):[0-5][0-9]")
    private String wayOutTime;

    @NotNull
    @Pattern(regexp = "([0-1][0-9]|2[0-3]):[0-5][0-9]")
    private String wayBackTime;

    @NotEmpty
    private int[] weekDay;

    public RouteRecurrentForm() {
        startDate = new DateField();
        endDate = new DateField();

        wayOutTime = wayBackTime = "00:00";
    }

    public DateField getStartDate() {
        return startDate;
    }

    public DateField getEndDate() {
        return endDate;
    }

    public String getWayOutTime() {
        return wayOutTime;
    }

    public void setWayOutTime(String wayOutTime) {
        this.wayOutTime = wayOutTime;
    }

    public String getWayBackTime() {
        return wayBackTime;
    }

    public void setWayBackTime(String wayBackTime) {
        this.wayBackTime = wayBackTime;
    }

    public int[] getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int[] weekDay) {
        this.weekDay = weekDay;
    }

}
