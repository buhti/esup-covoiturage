package org.esupportail.covoiturage.domain;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

/**
 * Ce modèle représente un trajet fréquent.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class RouteRecurrent extends Route {

    private final DateTime startDate;
    private final DateTime endDate;
    private final LocalTime wayOutTime;
    private final LocalTime wayBackTime;
    private final int[] weekDays;

    /**
     * Constructeur.
     *
     * @param id
     * @param owner
     * @param driver
     * @param ladiesOnly
     * @param seats
     * @param from
     * @param to
     * @param distance
     * @param roundTrip
     * @param startDate
     * @param endDate
     * @param wayOutTime
     * @param wayBackTime
     * @param weekDays
     */
    public RouteRecurrent(long id, Customer owner, boolean driver, boolean ladiesOnly, int seats, Location from,
            Location to, int distance, boolean roundTrip, DateTime startDate, DateTime endDate, LocalTime wayOutTime,
            LocalTime wayBackTime, int[] weekDays) {

        super(id, owner, driver, ladiesOnly, seats, from, to, distance, roundTrip);

        this.startDate = startDate;
        this.endDate = endDate;
        this.wayOutTime = wayOutTime;
        this.wayBackTime = wayBackTime;
        this.weekDays = weekDays;
    }

    @Override
    public boolean isRecurrent() {
        return true;
    }

    /**
     * Retourne la date de création du trajet.
     *
     * @return date
     */
    public DateTime getStartDate() {
        return startDate;
    }

    /**
     * Retourne la date d'expiration du trajet.
     *
     * @return date
     */
    public DateTime getEndDate() {
        return endDate;
    }

    /**
     * Retourne l'heure de départ du trajet.
     *
     * @return heure
     */
    public LocalTime getWayOutTime() {
        return wayOutTime;
    }

    /**
     * Retourne l'heure de retour du trajet.
     *
     * @return heure
     */
    public LocalTime getWayBackTime() {
        return wayBackTime;
    }

    /**
     * Retourne le numéro des jours de la semaine où le trajet à lieu.
     *
     * @return jours
     */
    public int[] getWeekDays() {
        return weekDays;
    }

}
