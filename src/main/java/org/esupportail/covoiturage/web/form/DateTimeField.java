package org.esupportail.covoiturage.web.form;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class DateTimeField extends DateField {

    @Valid
    private TimeField timeField;

    public DateTimeField() {
        this(new DateTime());
    }

    public DateTimeField(DateTime date) {
        super(date);
        timeField = new TimeField(date);
    }

    public String getTime() {
        return timeField.getTime();
    }

    public void setTime(String time) {
        timeField.setTime(time);
    }

    @Override
    public DateTime toDateTime() {
        LocalTime time = timeField.toLocalTime();
        return new DateTime(getYear(), getMonth(), getDay(), time.hourOfDay().get(), time.minuteOfHour().get());
    }

}
