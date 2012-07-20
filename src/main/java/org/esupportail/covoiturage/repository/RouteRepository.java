package org.esupportail.covoiturage.repository;

import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.exception.RouteNotFoundException;

public interface RouteRepository {

	long createRoute(Route route);
	Route findOneById(long id) throws RouteNotFoundException;

}
