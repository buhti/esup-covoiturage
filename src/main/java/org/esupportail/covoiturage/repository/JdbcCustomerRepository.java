package org.esupportail.covoiturage.repository;

import java.util.List;

import javax.annotation.Resource;

import org.esupportail.covoiturage.domain.Customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private JdbcCustomerMapper customerMapper;

    @Transactional
    @Override
    public Customer createCustomer(Customer customer) {
        jdbcTemplate.update(INSERT_CUSTOMER, customer.getLogin(), customer.getEmail(), customer.getFirstname(),
                customer.getLastname());

        return findOneByLogin(customer.getLogin());
    }

    @Override
    public void updateCustomer(Customer customer) {
        jdbcTemplate.update(UPDATE_CUSTOMER, customer.getEmail(), customer.getFirstname(), customer.getLastname(),
                customer.getLogin());
    }

    @Override
    public Customer findOneByLogin(String login) {
        List<Customer> results = jdbcTemplate.query(SELECT_CUSTOMER, customerMapper, login);
        return results.isEmpty() ? null : results.get(0);
    }

    private static final String INSERT_CUSTOMER = "INSERT INTO Customer (login, email, firstname, lastname) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_CUSTOMER = "UPDATE Customer SET email = ?, firstname = ?, lastname = ? WHERE login = ?";
    private static final String SELECT_CUSTOMER = "SELECT * FROM Customer WHERE login = ?";

}
