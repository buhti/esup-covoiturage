package org.esupportail.covoiturage.web.form;

import javax.validation.Valid;

import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.domain.RouteOccasional;

public class RouteOccasionalForm {

    @Valid
    private DateTimeField wayOut;

    @Valid
    private DateTimeField wayBack;

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
