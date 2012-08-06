package org.esupportail.covoiturage.service;

import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.exception.DistanceNotFoundException;
import org.esupportail.covoiturage.exception.LocationNotFoundException;

public interface GeocoderService {

    Location geocode(String address) throws LocationNotFoundException;

    String distance(Location origin, Location destination) throws DistanceNotFoundException;

}
