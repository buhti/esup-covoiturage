package org.esupportail.covoiturage.web.form;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.esupportail.covoiturage.util.JSUtil;

import org.hibernate.validator.constraints.NotEmpty;

public class SearchForm {

    private final Map<String, String> predefinedLocations;
    private final Collection<Integer> dateDay;
    private final Map<Integer, String> dateMonth;
    private final Collection<Integer> dateYear;
    private final Collection<String> dateTime;
    private final Map<Integer, String> dateTolerances;
    private final Map<Integer, String> distanceTolerances;

    @NotEmpty
    private String from;

    @NotEmpty
    private String to;

    @Valid
    private DateTimeField date;

    private int fromTolerance;
    private int toTolerance;
    private int dateTolerance;

    public SearchForm(Map<String, String> predefinedLocations, Collection<Integer> dateDay,
            Map<Integer, String> dateMonth, Collection<Integer> dateYear, Collection<String> dateTime,
            Map<Integer, String> dateTolerances, Map<Integer, String> distanceTolerances) {

        // Inject values
        this.predefinedLocations = predefinedLocations;
        this.dateDay = dateDay;
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.dateTime = dateTime;
        this.dateTolerances = dateTolerances;
        this.distanceTolerances = distanceTolerances;

        // Create fields
        date = new DateTimeField();

        // Initialize fields
        fromTolerance = toTolerance = distanceTolerances.keySet().iterator().next();
        dateTolerance = dateTolerances.keySet().iterator().next();
    }

    public String getPredefinedLocationsJSON() {
        return JSUtil.convertToArray(predefinedLocations.keySet());
    }

    public Map<Integer, String> getDateTolerances() {
        return dateTolerances;
    }

    public Map<Integer, String> getDistanceTolerances() {
        return distanceTolerances;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getFromTolerance() {
        return fromTolerance;
    }

    public void setFromTolerance(int fromTolerance) {
        this.fromTolerance = fromTolerance;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getToTolerance() {
        return toTolerance;
    }

    public void setToTolerance(int toTolerance) {
        this.toTolerance = toTolerance;
    }

    public DateTimeField getDate() {
        return date;
    }

    public void setDateTime(DateTimeField date) {
        this.date = date;
    }

    public int getDateTolerance() {
        return dateTolerance;
    }

    public void setDateTolerance(int dateTolerance) {
        this.dateTolerance = dateTolerance;
    }

}
