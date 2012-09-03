package org.esupportail.covoiturage.exception;

import org.esupportail.covoiturage.domain.Location;

/**
 * Cette exception est levée quand une distance entre deux lieux ne peut être
 * calculée.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@SuppressWarnings("serial")
public class DistanceNotFoundException extends Exception {

    private final Location origin;
    private final Location destination;

    /**
     * Constructeur.
     *
     * @param origin Lieu d'origine
     * @param destination Lieu de destination
     */
    public DistanceNotFoundException(Location origin, Location destination) {
        super("Unable to retrieve the distance between " + origin.getAddress() + " and " + destination.getAddress());
        this.origin = origin;
        this.destination = destination;
    }

    /**
     * Retourne le lieu d'origine.
     *
     * @return lieu
     */
    public Location getOrigin() {
        return origin;
    }

    /**
     * Retourne le lieu de destination.
     *
     * @return lieu
     */
    public Location getDestination() {
        return destination;
    }

}
