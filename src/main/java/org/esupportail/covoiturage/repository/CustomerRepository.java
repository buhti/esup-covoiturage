package org.esupportail.covoiturage.repository;

import org.esupportail.covoiturage.domain.Customer;

public interface CustomerRepository {

    Customer findOneByLogin(String login);

}
