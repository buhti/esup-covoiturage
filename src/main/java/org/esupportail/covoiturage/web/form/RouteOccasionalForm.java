package org.esupportail.covoiturage.web.form;

import org.joda.time.DateTime;

public class RouteOccasionalForm {

    private int wayOutDay;
    private int wayOutMonth;
    private int wayOutYear;
    private String wayOutTime;

    private int wayBackDay;
    private int wayBackMonth;
    private int wayBackYear;
    private String wayBackTime;

    public RouteOccasionalForm(RouteForm parent) {
        DateTime today = new DateTime();

        wayOutDay = wayBackDay = today.getDayOfMonth();
        wayOutMonth = wayBackMonth = today.getMonthOfYear();
        wayOutYear = wayBackYear = today.getYear();
        wayOutTime = wayBackTime = parent.getDateTime().get(0);
    }

    public DateTime getWayOutDateTime() {
        int hour = Integer.parseInt(wayOutTime.split(":", 2)[0]);
        int minute = Integer.parseInt(wayOutTime.split(":", 2)[1]);
        return new DateTime(wayOutDay, wayOutMonth, wayOutYear, hour, minute);
    }

    public DateTime getWayBackDateTime() {
        int hour = Integer.parseInt(wayBackTime.split(":", 2)[0]);
        int minute = Integer.parseInt(wayBackTime.split(":", 2)[1]);
        return new DateTime(wayBackDay, wayBackMonth, wayBackYear, hour, minute);
    }

    public int getWayOutDay() {
        return wayOutDay;
    }

    public void setWayOutDay(int wayOutDay) {
        this.wayOutDay = wayOutDay;
    }

    public int getWayOutMonth() {
        return wayOutMonth;
    }

    public void setWayOutMonth(int wayOutMonth) {
        this.wayOutMonth = wayOutMonth;
    }

    public int getWayOutYear() {
        return wayOutYear;
    }

    public void setWayOutYear(int wayOutYear) {
        this.wayOutYear = wayOutYear;
    }

    public String getWayOutTime() {
        return wayOutTime;
    }

    public void setWayOutTime(String wayOutTime) {
        this.wayOutTime = wayOutTime;
    }

    public int getWayBackDay() {
        return wayBackDay;
    }

    public void setWayBackDay(int wayBackDay) {
        this.wayBackDay = wayBackDay;
    }

    public int getWayBackMonth() {
        return wayBackMonth;
    }

    public void setWayBackMonth(int wayBackMonth) {
        this.wayBackMonth = wayBackMonth;
    }

    public int getWayBackYear() {
        return wayBackYear;
    }

    public void setWayBackYear(int wayBackYear) {
        this.wayBackYear = wayBackYear;
    }

    public String getWayBackTime() {
        return wayBackTime;
    }

    public void setWayBackTime(String wayBackTime) {
        this.wayBackTime = wayBackTime;
    }

}
