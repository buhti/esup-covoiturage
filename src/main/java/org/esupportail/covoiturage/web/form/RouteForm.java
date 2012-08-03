package org.esupportail.covoiturage.web.form;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

public class RouteForm {

    private final Map<String, String> predefinedLocations;
    private final Map<String, String> availableSeats;

    private final List<Integer> dateDay;
    private final Map<Integer, String> dateMonth;
    private final List<Integer> dateYear;
    private final List<String> dateTime;
    private final Map<Integer, String> dateWeekDay;

    private RouteOccasionalForm occasionalForm;
    private RouteRecurrentForm recurrentForm;

    @NotEmpty
    private String fromAddress;

    @NotEmpty
    private String toAddress;

    @NotNull
    private boolean driver;

    @Min(1)
    private int seats;

    @NotNull
    private boolean recurrent;

    public RouteForm(Map<String, String> predefinedLocations, Map<String, String> availableSeats) {

        // Create subforms
        occasionalForm = new RouteOccasionalForm();
        recurrentForm = new RouteRecurrentForm();

        // Inject values
        this.predefinedLocations = predefinedLocations;
        this.availableSeats = availableSeats;

        DateTime today = new DateTime();

        dateDay = range(1, 31);
        dateYear = range(today.getYear(), today.getYear() + 3);
        dateMonth = new LinkedHashMap<Integer, String>();
        dateTime = new ArrayList<String>();
        dateWeekDay = new LinkedHashMap<Integer, String>();

        for (int i = 1; i <= 12; i++) {
            dateMonth.put(i, today.withMonthOfYear(i).monthOfYear().getAsText(new Locale("fr")));
        }

        for (int i = 0; i <= 23; i++) {
            String hour = i < 10 ? "0" + i : Integer.toString(i);
            dateTime.add(hour + ":00");
            dateTime.add(hour + ":30");
        }

        for (int i = 1; i <= 7; i++) {
            dateWeekDay.put(i, today.withDayOfWeek(i).dayOfWeek().getAsShortText(new Locale("fr")));
        }

        driver = true;
    }

    public RouteOccasionalForm getOccasionalForm() {
        return occasionalForm;
    }

    public RouteRecurrentForm getRecurrentForm() {
        return recurrentForm;
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

    public Map<Integer, String> getDateWeekDay() {
        return dateWeekDay;
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

    public boolean isDriver() {
        return driver;
    }

    public void setDriver(boolean driver) {
        this.driver = driver;
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

}
