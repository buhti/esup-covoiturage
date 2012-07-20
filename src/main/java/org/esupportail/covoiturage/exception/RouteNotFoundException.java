package org.esupportail.covoiturage.exception;

@SuppressWarnings("serial")
public class RouteNotFoundException extends RouteException {

	public RouteNotFoundException(long routeId) {
		super(routeId, "No such route with id '" + routeId + "'");
	}

}
