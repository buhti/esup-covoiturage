package org.esupportail.covoiturage.repository;

import java.util.List;

import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.exception.RouteNotFoundException;

import org.joda.time.DateTime;

/**
 * Cette interface décrit un service permettant de manipuler les informations
 * relatives aux trajets.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public interface RouteRepository {

    /**
     * Sauvegarde un trajet.
     *
     * @param route Trajet
     * @return ID du trajet
     */
    long createRoute(Route route);

    /**
     * Met à jour un trajet.
     *
     * @param ownerId ID du propriétaire
     * @param routeId ID du trajet
     * @param route Trajet
     */
    void updateRoute(long ownerId, long routeId, Route route);

    /**
     * Supprime un trajet.
     *
     * @param ownerId ID du propriétaire
     * @param routeId ID du trajet
     */
    void deleteRoute(long ownerId, long routeId);

    /**
     * Récupère un trajet à partir de son ID.
     *
     * @param routeId ID du trajet
     * @return Trajet
     * @throws RouteNotFoundException quand le trajet n'existe pas
     */
    Route findOneById(long routeId) throws RouteNotFoundException;

    /**
     * Recherche les trajets correspondants aux critères.
     *
     * @param from Adresse de départ
     * @param fromTolerence Tolérance de distance par rapport au lieu de départ
     * @param to Adresse de d'arrivée
     * @param toTolerence Tolérance de distance par rapport au lieu d'arrivée
     * @param date Date de départ
     * @param dateTolerence Tolérance sur l'horaire de départ
     * @return Liste de trajets
     */
    List<Route> findRoutesByTolerance(Location from, int fromTolerence, Location to, int toTolerence, DateTime date, int dateTolerence);

    /**
     * Récupère la liste des trajets créés par l'utilisateur spécifié.
     *
     * @param ownerId ID du propriétaire
     * @return Liste de trajets
     */
    List<Route> findRoutesByOwner(long ownerId);

    /**
     * Récupère les trajets sur le point d'expirer.
     *
     * @param days Nombre de jours avant expiration
     * @return Liste de trajets
     */
    List<Route> findNearlyExpiredRecurrentRoutes(int days);

    /**
     * Supprimer les trajets fréquents expirés depuis le nombre de jours
     * indiqué.
     *
     * @param days Nombre de jours
     */
    void deleteExpiredRecurrentRoutes(int days);

    /**
     * Supprimer les trajets occasionnels expirés depuis le nombre de jours
     * indiqué.
     *
     * @param days Nombre de jours
     */
    void deleteExpiredOccasionalRoutes(int days);

    /**
     * Supprimer les trajets d'un utilisateur donné.
     *
     * @param ownerId ID du propriétaire
     */
    void deleteRoutesByOwner(long ownerId);

}
