package org.esupportail.covoiturage.exception;

/**
 * Cette exception sert de base aux autres exceptions relatives aux trajets.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@SuppressWarnings("serial")
public abstract class RouteException extends Exception {

    private final long routeId;

    /**
     * Constructeur.
     *
     * @param routeId ID du trajet
     * @param message Message d'erreur
     */
    public RouteException(long routeId, String message) {
        super(message);
        this.routeId = routeId;
    }

    /**
     * Retourne l'ID du trajet.
     *
     * @return ID
     */
    public long getRouteId() {
        return routeId;
    }

}
