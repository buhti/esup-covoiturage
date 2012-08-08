package org.esupportail.covoiturage.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class TimeField {

    @NotNull
    @Pattern(regexp = "([0-1][0-9]|2[0-3]):[0-5][0-9]")
    private String time;

    public TimeField() {
        this(new DateTime());
    }

    public TimeField(DateTime date) {
        DateTime newDate = date.plusMinutes(30);
        int hours = newDate.hourOfDay().get();
        int minutes = newDate.minuteOfHour().get();

        time = hours + ":" + (minutes < 30 ? "00" : "30");
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LocalTime toLocalTime() {
        int hours = Integer.parseInt(time.split(":", 2)[0]);
        int minutes = Integer.parseInt(time.split(":", 2)[1]);
        return new LocalTime(hours, minutes);
    }

}
