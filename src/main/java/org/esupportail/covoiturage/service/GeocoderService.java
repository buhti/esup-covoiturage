package org.esupportail.covoiturage.service;

import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.exception.DistanceNotFoundException;
import org.esupportail.covoiturage.exception.LocationNotFoundException;

/**
 * Ce service permet d'effectuer des opérations de géocodage.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public interface GeocoderService {

    /**
     * Recherche le lieu et retourne ses coordonnées GPS.
     *
     * @param address Adresse
     * @return Lieu
     * @throws LocationNotFoundException quand le lieu n'est pas trouvé
     */
    Location geocode(String address) throws LocationNotFoundException;

    /**
     * Calcule la distance entre deux lieux.
     *
     * @param origin Lieu d'origine
     * @param destination Lieu de destination
     * @return Distance en kilomètres
     * @throws DistanceNotFoundException quand la distance ne peut être calculée
     */
    int distance(Location origin, Location destination) throws DistanceNotFoundException;

}
