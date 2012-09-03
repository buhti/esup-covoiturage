package org.esupportail.covoiturage.repository;

import java.util.List;

import org.esupportail.covoiturage.domain.Customer;

/**
 * Cette interface décrit un service permettant de manipuler les informations
 * relatives aux utilisateurs.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public interface CustomerRepository {

    /**
     * Sauvegarde un utilisateur.
     *
     * @param customer
     * @return
     */
    Customer createCustomer(Customer customer);

    /**
     * Met à jour les informations de l'utilisateur spécifié.
     *
     * @param customer
     */
    void updateCustomer(Customer customer);

    /**
     * Met à jour la date de dernière connexion de l'utilisateur spécifié.
     *
     * @param customer
     */
    void updateLastConnectionDate(Customer customer);

    /**
     * Récupère un utilisateur à partir de son login.
     *
     * @param login
     * @return
     */
    Customer findOneByLogin(String login);

    /**
     * Récupère la liste des ID des utilisateurs inactifs depuis un nombre de
     * jours spécifiés.
     *
     * @param days Durée de la période d'inactivé en jours
     * @return
     */
    List<Long> findInactiveCustomersID(int days);

    /**
     * Supprime un utilisateur à partir de son ID.
     *
     * @param id
     */
    void deleteCustomer(long id);

}
