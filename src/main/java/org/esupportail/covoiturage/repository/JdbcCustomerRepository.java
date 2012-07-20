package org.esupportail.covoiturage.repository;

import javax.annotation.Resource;

import org.esupportail.covoiturage.domain.Customer;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcCustomerRepository implements CustomerRepository {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public Customer findOneByLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

}
