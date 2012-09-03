package org.esupportail.covoiturage.repository;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.esupportail.covoiturage.domain.Customer;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ce service permet de manipuler les informations relatives aux utilisateurs.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private JdbcCustomerMapper customerMapper;

    @Transactional
    @Override
    public Customer createCustomer(Customer customer) {
        jdbcTemplate.update(INSERT_CUSTOMER, customer.getLogin(), customer.getEmail(),
                customer.getFirstname(), customer.getLastname(), customer.isChatting(),
                customer.isSmoking(), customer.isListeningMusic());

        return findOneByLogin(customer.getLogin());
    }

    @Override
    public void updateCustomer(Customer customer) {
        jdbcTemplate.update(UPDATE_CUSTOMER, customer.getEmail(), customer.getFirstname(),
                customer.getLastname(), customer.isChatting(), customer.isSmoking(),
                customer.isListeningMusic(), customer.getLogin());
    }

    @Override
    public void updateLastConnectionDate(Customer customer) {
        jdbcTemplate.update(UPDATE_LAST_CONNECTION, customer.getId());
    }

    @Override
    public Customer findOneByLogin(String login) {
        List<Customer> results = jdbcTemplate.query(SELECT_CUSTOMER, customerMapper, login);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<Long> findInactiveCustomersID(int days) {
        Date expirationDate = DateTime.now().minusDays(days).toDate();
        return jdbcTemplate.queryForList(SELECT_INACTIVE_CUSTOMERS_ID, Long.class, expirationDate);
    }

    @Override
    public void deleteCustomer(long id) {
        jdbcTemplate.update(DELETE_CUSTOMER, id);
    }

    private static final String INSERT_CUSTOMER = "INSERT INTO Customer (login, email, firstname, lastname, chatting, smoking, listening_music, last_connection) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
    private static final String UPDATE_CUSTOMER = "UPDATE Customer SET email = ?, firstname = ?, lastname = ?, chatting = ?, smoking = ?, listening_music = ? WHERE login = ?";
    private static final String UPDATE_LAST_CONNECTION = "UPDATE Customer SET last_connection = NOW() WHERE customer_id = ?";
    private static final String DELETE_CUSTOMER = "DELETE FROM Customer WHERE customer_id = ?";
    private static final String SELECT_CUSTOMER = "SELECT * FROM Customer WHERE login = ?";
    private static final String SELECT_INACTIVE_CUSTOMERS_ID = "SELECT customer_id FROM Customer WHERE last_connection <= ?";

}
