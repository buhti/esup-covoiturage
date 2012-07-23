package org.esupportail.covoiturage.service;

import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.exception.LocationNotFoundException;

public interface GeocoderService {

    Location geocode(String address) throws LocationNotFoundException;

}
