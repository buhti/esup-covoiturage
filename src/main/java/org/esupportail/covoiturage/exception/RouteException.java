package org.esupportail.covoiturage.exception;

@SuppressWarnings("serial")
public class RouteException extends Exception {

	private final long routeId;

	public RouteException(long routeId, String message) {
		super(message);
		this.routeId = routeId;
	}

	public long getRouteId() {
		return routeId;
	}

}
