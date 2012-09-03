package org.esupportail.covoiturage.exception;

/**
 * Cette exception est levée quand un trajet ne peut être trouvé.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@SuppressWarnings("serial")
public class RouteNotFoundException extends RouteException {

    /**
     * Constructeur.
     *
     * @param routeId ID du trajet
     */
    public RouteNotFoundException(long routeId) {
        super(routeId, "No such route with id '" + routeId + "'");
    }

}
