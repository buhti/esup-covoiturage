package org.esupportail.covoiturage.domain;

import org.esupportail.covoiturage.repository.StatRepository.StatType;

import org.joda.time.DateTime;

/**
 * Ce modèle représente une entrée statistique.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class Stat {

    private final StatType type;
    private final int value;
    private final DateTime date;

    /**
     * Constructeur.
     *
     * @param type
     * @param value
     * @param date
     */
    public Stat(StatType type, int value, DateTime date) {
        this.type = type;
        this.value = value;
        this.date = date;
    }

    /**
     * Retourne le type de statistique auquel correspond cette entrée.
     *
     * @return type
     */
    public StatType getType() {
        return type;
    }

    /**
     * Retourne la valeur de l'entrée.
     *
     * @return valeur
     */
    public int getValue() {
        return value;
    }

    /**
     * Retourne la date d'enregistrement de l'entrée.
     *
     * @return date
     */
    public DateTime getDate() {
        return date;
    }

}
