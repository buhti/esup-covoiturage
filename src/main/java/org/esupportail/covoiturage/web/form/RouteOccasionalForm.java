package org.esupportail.covoiturage.web.form;

import javax.validation.Valid;

import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.domain.RouteOccasional;

/**
 * Ce formulaire permet la création et l'édition de trajets occasionnels.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class RouteOccasionalForm {

    @Valid
    private DateTimeField wayOut;

    @Valid
    private DateTimeField wayBack;

    /**
     * Constructeur.
     */
    public RouteOccasionalForm() {
        wayOut = new DateTimeField();
        wayBack = new DateTimeField();
    }

    public DateTimeField getWayOut() {
        return wayOut;
    }

    public DateTimeField getWayBack() {
        return wayBack;
    }

    /**
     * Remplit les champs du formulaire en fonction du trajet passé en
     * paramètre.
     *
     * @param route Trajet
     */
    public void populate(Route route) {
        if (route instanceof RouteOccasional) {
            RouteOccasional r = (RouteOccasional) route;
            wayOut = new DateTimeField(r.getWayOutDate());

            if (r.isRoundTrip()) {
                wayBack = new DateTimeField(r.getWayBackDate());
            }
        }
    }

}
