package org.esupportail.covoiturage.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.joda.time.DateTime;

public class DateTimeField extends DateField {

    @NotNull
    @Pattern(regexp = "([0-1][0-9]|2[0-3]):[0-5][0-9]")
    private String time;

    public DateTimeField() {
        this(new DateTime());
    }

    public DateTimeField(DateTime date) {
        super(new DateTime());
        time = "00:00";
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public DateTime toDateTime() {
        int hour = Integer.parseInt(time.split(":", 2)[0]);
        int minute = Integer.parseInt(time.split(":", 2)[1]);

        return new DateTime(getDay(), getMonth(), getYear(), hour, minute);
    }

}
