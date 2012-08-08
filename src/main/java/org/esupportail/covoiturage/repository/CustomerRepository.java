package org.esupportail.covoiturage.repository;

import org.esupportail.covoiturage.domain.Customer;

public interface CustomerRepository {

    Customer createCustomer(Customer customer);

    void updateCustomer(Customer customer);

    Customer findOneByLogin(String login);

}
