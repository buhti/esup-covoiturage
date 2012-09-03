package org.esupportail.covoiturage.repository;

import java.util.Collection;
import java.util.Map;

/**
 * Cette interface décrit un service permettant de manipuler les informations
 * relatives aux données statique de l'application.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public interface DataRepository {

    /**
     * Retourne la liste des valeurs possibles lors du choix du nombre de places
     * disponibles pour un trajet.
     *
     * @return liste de choix
     */
    Map<Integer, String> getAvailableSeats();

    /**
     * Retourne la liste des valeurs possibles lors du choix de la tolérance
     * quant à la date d'un trajet.
     *
     * @return liste de choix
     */
    Map<Integer, String> getDateTolerances();

    /**
     * Retourne la liste des valeurs possibles lors du choix de la tolérance par
     * rapport à la distance du lieu de départ ou d'arrivée d'un trajet.
     *
     * @return liste de choix
     */
    Map<Integer, String> getDistanceTolerances();

    /**
     * Retourne la liste des lieux prédéfinis.
     *
     * @return lieux
     */
    Map<String, String> getPredefinedLocations();

    /**
     * Retourne la liste des lieux prédéfinis au format JSON.
     *
     * @return lieux
     */
    String getPredefinedLocationsJSON();

    /**
     * Retourne la liste des jours (de 1 à 31).
     *
     * @return jours
     */
    Collection<Integer> getDays();

    /**
     * Retourne la liste des mois en associant à chaque numéro son nom.
     *
     * @return mois
     */
    Map<Integer, String> getMonths();

    /**
     * Retourne la liste des années.
     *
     * @return années
     */
    Collection<Integer> getYears();

    /**
     * Retourne la liste des horaires de départ et d'arrivées possibles.
     *
     * @return horaires
     */
    Collection<String> getHoursAndMinutes();

    /**
     * Retourne la liste des jours de la semaine en associant à chaque numéro
     * son nom.
     *
     * @return jours de la semaine
     */
    Map<Integer, String> getWeekDays();

}
