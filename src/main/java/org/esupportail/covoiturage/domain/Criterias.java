package org.esupportail.covoiturage.domain;

import org.joda.time.DateTime;

/**
 * Ce modèle représente les critères d'une recherche.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class Criterias {

    private final String from;
    private final String to;
    private final DateTime date;
    private final int fromTolerance;
    private final int toTolerance;
    private final int dateTolerance;

    /**
     * Constructeur.
     *
     * @param from
     * @param to
     * @param date
     * @param fromTolerance
     * @param toTolerance
     * @param dateTolerance
     */
    public Criterias(String from, String to, DateTime date, int fromTolerance, int toTolerance, int dateTolerance) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.fromTolerance = fromTolerance;
        this.toTolerance = toTolerance;
        this.dateTolerance = dateTolerance;
    }

    /**
     * Retourne le lieu de départ.
     *
     * @return adresse
     */
    public String getFrom() {
        return from;
    }

    /**
     * Retourne le lieu d'arrivée.
     *
     * @return adresse
     */
    public String getTo() {
        return to;
    }

    /**
     * Retourne la date.
     *
     * @return adresse
     */
    public DateTime getDate() {
        return date;
    }

    /**
     * Retourne la tolérance quant à la distance par rapport au lieu de départ.
     *
     * @return kilomètres
     */
    public int getFromTolerance() {
        return fromTolerance;
    }

    /**
     * Retourne la tolérance quant à la distance par rapport au lieu d'arrivée.
     *
     * @return kilomètres
     */
    public int getToTolerance() {
        return toTolerance;
    }

    /**
     * Retourne la tolérance quant à la date de départ.
     *
     * @return minutes
     */
    public int getDateTolerance() {
        return dateTolerance;
    }

}
