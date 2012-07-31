package org.esupportail.covoiturage.web.form;

import javax.validation.Valid;

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

}
