package org.esupportail.covoiturage.web.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

/**
 * Ce formulaire représente un champ permettant le choix d'un date.
 *
 * @author Florent Cailhol (Anyware Services)
 */
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

    /**
     * Constructeur.
     */
    public DateField() {
        this(new DateTime());
    }

    /**
     * Constructeur.
     *
     * @param date Valeur par défaut
     */
    public DateField(DateTime date) {
        day = date.getDayOfMonth();
        month = date.getMonthOfYear();
        year = date.getYear();
    }

    /**
     * Retourne le jour sélectionné.
     *
     * @return jour
     */
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Retourne le mois sélectionné.
     *
     * @return mois
     */
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Retourne l'année sélectionnée.
     *
     * @return année
     */
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Retourne la date sous forme d'un objet manipulable.
     *
     * @return date
     */
    public DateTime toDateTime() {
        return new DateTime(year, month, day, 0, 0);
    }

}
