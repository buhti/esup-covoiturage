package org.esupportail.covoiturage.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.joda.time.LocalTime;

/**
 * Ce formulaire représente un champ permettant le choix d'une horaire.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class TimeField {

    @NotNull
    @Pattern(regexp = "([0-1][0-9]|2[0-3]):[0-5][0-9]")
    private String time;

    /**
     * Constructeur.
     */
    public TimeField() {
        this(new LocalTime());
    }

    /**
     * Construteur.
     *
     * @param localTime Valeur par défaut
     */
    public TimeField(LocalTime localTime) {
        int hours = localTime.hourOfDay().get();
        int minutes = localTime.minuteOfHour().get();

        time = hours + ":" + (minutes < 30 ? "00" : "30");
    }

    /**
     * Retourne l'horaire sélectionnée.
     *
     * @return horaire
     */
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Retourne l'horaire sous forme d'un objet manipulable.
     *
     * @return horaire
     */
    public LocalTime toLocalTime() {
        int hours = Integer.parseInt(time.split(":", 2)[0]);
        int minutes = Integer.parseInt(time.split(":", 2)[1]);
        return new LocalTime(hours, minutes);
    }

}
