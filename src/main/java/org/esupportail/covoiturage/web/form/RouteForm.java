package org.esupportail.covoiturage.web.form;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

public class RouteForm {

    private final Map<String, String> predefinedLocations;
    private final Map<String, String> possibleStatuses;
    private final Map<String, String> availableSeats;
    private final List<Integer> dateDay;
    private final Map<Integer, String> dateMonth;
    private final List<Integer> dateYear;
    private final List<String> dateTime;

    @NotNull
    private String fromAddress;

    @NotNull
    private String toAddress;

    @NotNull
    private int status;

    @NotNull
    private int seats;

    private boolean recurrent;

    private int wayOutDay;
    private int wayOutMonth;
    private int wayOutYear;
    private String wayOutTime;

    private int wayBackDay;
    private int wayBackMonth;
    private int wayBackYear;
    private String wayBackTime;

    public RouteForm(Map<String, String> predefinedLocations, Map<String, String> possibleStatuses,
            Map<String, String> availableSeats) {
        this.predefinedLocations = predefinedLocations;
        this.possibleStatuses = possibleStatuses;
        this.availableSeats = availableSeats;

        DateTime today = new DateTime();

        dateDay = range(1, 31);
        dateYear = range(today.getYear(), today.getYear() + 3);
        dateMonth = new LinkedHashMap<Integer, String>();
        dateTime = new ArrayList<String>();

        for (int i = 1; i <= 12; i++) {
            dateMonth.put(i, today.withMonthOfYear(i).monthOfYear().getAsText(new Locale("fr")));
        }

        for (int i = 0; i <= 23; i++) {
            String hour = i < 10 ? "0" + i : Integer.toString(i);
            dateTime.add(hour + ":00");
            dateTime.add(hour + ":30");
        }

        wayOutDay = wayBackDay = today.getDayOfMonth();
        wayOutMonth = wayBackMonth = today.getMonthOfYear();
        wayOutYear = wayBackYear = today.getYear();
        wayOutTime = wayBackTime = dateTime.get(0);
    }

    private static List<Integer> range(int start, int end) {
        List<Integer> range = new ArrayList<Integer>(end - start + 1);
        for (int i = start; i <= end; i++) {
            range.add(i);
        }
        return range;
    }

    public Map<String, String> getPredefinedLocations() {
        return predefinedLocations;
    }

    public String getPredefinedLocationsJSON() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");

        Iterator<String> it = predefinedLocations.keySet().iterator();
        while (it.hasNext()) {
            String location = it.next();
            sb.append('"');
            sb.append(location);
            sb.append('"');

            if (it.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    public Map<String, String> getPossibleStatuses() {
        return possibleStatuses;
    }

    public Map<String, String> getAvailableSeats() {
        return availableSeats;
    }

    public List<Integer> getDateDay() {
        return dateDay;
    }

    public Map<Integer, String> getDateMonth() {
        return dateMonth;
    }

    public List<Integer> getDateYear() {
        return dateYear;
    }

    public List<String> getDateTime() {
        return dateTime;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public boolean isRecurrent() {
        return recurrent;
    }

    public void setRecurrent(boolean recurrent) {
        this.recurrent = recurrent;
    }

    public DateTime getWayOutDateTime() {
        int hour = Integer.parseInt(wayOutTime.split(":", 2)[0]);
        int minute = Integer.parseInt(wayOutTime.split(":", 2)[1]);
        return new DateTime(wayOutDay, wayOutMonth, wayOutYear, hour, minute);
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

    public DateTime getWayBackDateTime() {
        int hour = Integer.parseInt(wayBackTime.split(":", 2)[0]);
        int minute = Integer.parseInt(wayBackTime.split(":", 2)[1]);
        return new DateTime(wayBackDay, wayBackMonth, wayBackYear, hour, minute);
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
