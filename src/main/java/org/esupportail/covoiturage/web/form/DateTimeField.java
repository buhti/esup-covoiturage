package org.esupportail.covoiturage.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.joda.time.DateTime;

public class DateTimeField extends DateField {

    @NotNull
    @Pattern(regexp = "([0-1][0-9]|2[0-3]):[0-5][0-9]")
    private String time;

    public DateTimeField() {
        this(new DateTime().minuteOfHour().addToCopy(30));
    }

    public DateTimeField(DateTime date) {
        super(date);

        int hours = date.hourOfDay().get();
        int minutes = date.minuteOfHour().get();

        time = hours + ":" + (minutes < 30 ? "00" : "30");
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public DateTime toDateTime() {
        int hours = Integer.parseInt(time.split(":", 2)[0]);
        int minutes = Integer.parseInt(time.split(":", 2)[1]);

        return new DateTime(getYear(), getMonth(), getDay(), hours, minutes);
    }

}
