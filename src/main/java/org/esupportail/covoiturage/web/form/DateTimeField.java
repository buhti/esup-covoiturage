package org.esupportail.covoiturage.web.form;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class DateTimeField extends DateField {

    @Valid
    private TimeField timeField;

    /**
     * Construteur.
     */
    public DateTimeField() {
        this(new DateTime());
    }

    /**
     * Construteur.
     *
     * @param date Valeur par défaut
     */
    public DateTimeField(DateTime date) {
        super(date);
        timeField = new TimeField(new LocalTime(date.getHourOfDay(), date.getMinuteOfHour()));
    }

    /**
     * Retourne le champ horaire.
     *
     * @return
     */
    public String getTime() {
        return timeField.getTime();
    }

    public void setTime(String time) {
        timeField.setTime(time);
    }

    /**
     * Retourne la date et l'horaire sous forme d'un objet manipulable.
     *
     * @return date et horaire
     */
    @Override
    public DateTime toDateTime() {
        LocalTime time = timeField.toLocalTime();
        return new DateTime(getYear(), getMonth(), getDay(), time.hourOfDay().get(), time.minuteOfHour().get());
    }

}
