package org.esupportail.covoiturage.repository;

import java.util.List;

import org.esupportail.covoiturage.domain.Customer;
import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.exception.RouteNotFoundException;

import org.joda.time.DateTime;

public interface RouteRepository {

    long createRoute(Route route);

    void deleteRoute(Route route);

    Route findOneById(long id) throws RouteNotFoundException;

    List<Route> findRoutesByTolerance(Location from, int fromTolerence, Location to, int toTolerence, DateTime date, int dateTolerence);

    List<Route> findRoutesByOwner(Customer owner);

}
