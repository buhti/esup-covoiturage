package org.esupportail.covoiturage.repository;

import java.util.List;

import org.esupportail.covoiturage.domain.Stat;

/**
 * Cette interface décrit un service permettant de manipuler les informations
 * relatives aux statistiques de l'application.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public interface StatRepository {

    /**
     * Cette énumération représente les types de compteur disponibles.
     *
     * @author Florent Cailhol (Anyware Services)
     */
    public enum CounterType {
        /**
         * Nombre total de kilomètres ajoutées dans l'application
         */
        TOTAL_KILOMETERS
    }

    /**
     * Cette énumération représente les types de statistique disponibles.
     *
     * @author Florent Cailhol (Anyware Services)
     */
    public enum StatType {
        /**
         * Nombre de connexions
         */
        LOGINS,
        /**
         * Nombre d'inscriptions
         */
        REGISTRATIONS,
        /**
         * Nombre de trajets créés
         */
        ROUTES,
        /**
         * Nombre de recherches effectuées
         */
        SEARCHES
    }

    /**
     * Cette énumération représente les périodes de statistique disponibles.
     *
     * @author Florent Cailhol (Anyware Services)
     */
    public enum StatPeriod {
        /**
         * Valeurs de la semaine
         */
        WEEK,
        /**
         * Valeurs du mois
         */
        MONTH,
        /**
         * Valeurs de l'année
         */
        YEAR
    }

    /**
     * Incrémente le compteur spécifié.
     *
     * @param type Type de compteur
     * @param value Valeur à ajouter
     */
    void addToCounter(CounterType type, int value);

    /**
     * Récupère la valeur du compte spécifié.
     *
     * @param type Type de compteur
     * @return Valeur
     */
    int getCounter(CounterType type);

    /**
     * Incrémente la valeur journalière pour la statistique indiquée.
     *
     * @param type Type de statistique
     */
    void incrementStatistic(StatType type);

    /**
     * Récupère la liste des entrées de statistique pour sur une période donnée.
     *
     * @param type Type de statistique
     * @param period Période
     * @return Liste de statistiques
     */
    List<Stat> getStatistics(StatType type, StatPeriod period);

}
