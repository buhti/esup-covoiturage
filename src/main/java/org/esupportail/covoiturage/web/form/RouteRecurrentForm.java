package org.esupportail.covoiturage.web.form;

import org.joda.time.DateTime;

public class RouteRecurrentForm {

    private int startDay;
    private int startMonth;
    private int startYear;

    private int endDay;
    private int endMonth;
    private int endYear;

    private String wayOutTime;
    private String wayBackTime;

    private int[] weekDay;

    public RouteRecurrentForm() {
        DateTime today = new DateTime();

        startDay = endDay = today.getDayOfMonth();
        startMonth = endMonth = today.getMonthOfYear();
        startYear = endYear = today.getYear();

        wayOutTime = wayBackTime = "00:00";
    }

    public DateTime getStartDateTime() {
        return new DateTime(startDay, startMonth, startYear, 0, 0);
    }

    public DateTime getEndDateTime() {
        return new DateTime(endDay, endMonth, endYear, 0, 0);
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
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
