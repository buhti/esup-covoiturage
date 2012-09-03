package org.esupportail.covoiturage.domain;

import org.joda.time.DateTime;

/**
 * Ce modèle représente un trajet occasionnel.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class RouteOccasional extends Route {

    private final DateTime wayOutDate;
    private final DateTime wayBackDate;

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
     * @param wayOutDate
     * @param wayBackDate
     */
    public RouteOccasional(long id, Customer owner, boolean driver, boolean ladiesOnly,
            int seats, Location from, Location to, int distance, boolean roundTrip,
            DateTime wayOutDate, DateTime wayBackDate) {

        super(id, owner, driver, ladiesOnly, seats, from, to, distance, roundTrip);

        this.wayOutDate = wayOutDate;
        this.wayBackDate = wayBackDate;
    }

    @Override
    public boolean isRecurrent() {
        return false;
    }

    /**
     * Retourne la date et l'heure de départ du travers.
     *
     * @return date et heure
     */
    public DateTime getWayOutDate() {
        return wayOutDate;
    }

    /**
     * Retourne la date et l'heure de retour du travers.
     *
     * @return date et heure
     */
    public DateTime getWayBackDate() {
        return wayBackDate;
    }

}
