package org.esupportail.covoiturage.web.form;

import java.util.Collection;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.esupportail.covoiturage.domain.Customer;
import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.domain.RouteOccasional;
import org.esupportail.covoiturage.domain.RouteRecurrent;
import org.esupportail.covoiturage.util.JSUtil;

import org.hibernate.validator.constraints.NotEmpty;

public class RouteForm {

    private final Map<String, String> predefinedLocations;
    private final Map<Integer, String> availableSeats;
    private final Collection<Integer> dateDay;
    private final Map<Integer, String> dateMonth;
    private final Collection<Integer> dateYear;
    private final Collection<String> dateTime;
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

    @NotNull
    private boolean roundTrip;

    public RouteForm(Map<String, String> predefinedLocations, Map<Integer, String> availableSeats,
            Collection<Integer> dateDay, Map<Integer, String> dateMonth, Collection<Integer> dateYear,
            Collection<String> dateTime, Map<Integer, String> dateWeekDay) {

        // Inject values
        this.predefinedLocations = predefinedLocations;
        this.availableSeats = availableSeats;
        this.dateDay = dateDay;
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.dateTime = dateTime;
        this.dateWeekDay = dateWeekDay;

        // Create subforms
        occasionalForm = new RouteOccasionalForm();
        recurrentForm = new RouteRecurrentForm();

        // Initialize fields
        driver = true;
    }

    public RouteOccasionalForm getOccasionalForm() {
        return occasionalForm;
    }

    public RouteRecurrentForm getRecurrentForm() {
        return recurrentForm;
    }

    public String getPredefinedLocationsJSON() {
        return JSUtil.convertToArray(predefinedLocations.keySet());
    }

    public Map<Integer, String> getAvailableSeats() {
        return availableSeats;
    }

    public Collection<Integer> getDateDay() {
        return dateDay;
    }

    public Map<Integer, String> getDateMonth() {
        return dateMonth;
    }

    public Collection<Integer> getDateYear() {
        return dateYear;
    }

    public Collection<String> getDateTime() {
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

    public boolean isRoundTrip() {
        return roundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        this.roundTrip = roundTrip;
    }

    public Route toRoute(Customer owner, Location from, Location to, int distance) {
        if (recurrent) {
            return new RouteRecurrent(0, owner, driver, seats, from, to, distance, roundTrip,
                    recurrentForm .getStartDate().toDateTime(), recurrentForm.getEndDate().toDateTime(),
                    recurrentForm.getWayOutTime().toLocalTime(), roundTrip ? recurrentForm.getWayBackTime().toLocalTime() : null,
                    recurrentForm.getWeekDay());
        } else {
            return new RouteOccasional(0, owner, driver, seats, from, to, distance, roundTrip, 
                    occasionalForm.getWayOut().toDateTime(), roundTrip ? occasionalForm.getWayBack().toDateTime() : null);
        }
    }
}
