package org.esupportail.covoiturage.web.form;

import java.util.Map;

public class SearchForm {

    private final Map<String, String> distanceTolerances;
    private final Map<String, String> predefinedLocations;

    private String from;
    private String fromTolerance;
    private String to;
    private String toTolerance;
    private String date;
    private String dateTolerance;

    public SearchForm(Map<String, String> distanceTolerances, Map<String, String> predefinedLocations) {
        this.distanceTolerances = distanceTolerances;
        this.predefinedLocations = predefinedLocations;
    }

    public Map<String, String> getDistanceTolerances() {
        return distanceTolerances;
    }

    public Map<String, String> getPredefinedLocations() {
        return predefinedLocations;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromTolerance() {
        return fromTolerance;
    }

    public void setFromTolerance(String fromTolerance) {
        this.fromTolerance = fromTolerance;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getToTolerance() {
        return toTolerance;
    }

    public void setToTolerance(String toTolerance) {
        this.toTolerance = toTolerance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateTolerance() {
        return dateTolerance;
    }

    public void setDateTolerance(String dateTolerance) {
        this.dateTolerance = dateTolerance;
    }

}
