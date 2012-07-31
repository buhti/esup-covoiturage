package org.esupportail.covoiturage.web.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

public class DateField {

    @NotNull
    @Min(1)
    @Max(31)
    private int day;

    @NotNull
    @Min(1)
    @Max(12)
    private int month;

    @NotNull
    @Min(2012)
    private int year;

    public int getDay() {
        return day;
    }

    public DateField() {
        this(new DateTime());
    }

    public DateField(DateTime date) {
        day = date.getDayOfMonth();
        month = date.getMonthOfYear();
        year = date.getYear();
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public DateTime toDateTime() {
        return new DateTime(day, month, year, 0, 0);
    }

}
