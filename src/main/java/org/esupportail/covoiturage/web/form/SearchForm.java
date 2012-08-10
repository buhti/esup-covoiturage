package org.esupportail.covoiturage.web.form;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

public class SearchForm {

    @NotEmpty
    private String from;

    @NotEmpty
    private String to;

    @Valid
    private DateTimeField date;

    private int fromTolerance;
    private int toTolerance;
    private int dateTolerance;

    public SearchForm() {
        // Create fields
        date = new DateTimeField();
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
