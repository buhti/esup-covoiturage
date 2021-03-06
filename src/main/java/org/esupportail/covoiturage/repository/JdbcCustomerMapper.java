package org.esupportail.covoiturage.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.esupportail.covoiturage.domain.Customer;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * Ce mapper permet d'instancier un utilisateur à partir de son entrée dans la
 * base de données.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Component
public class JdbcCustomerMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Customer(rs.getLong("customer_id"), rs.getString("login"), rs.getString("email"),
                rs.getString("firstname"), rs.getString("lastname"), rs.getBoolean("chatting"),
                rs.getBoolean("smoking"), rs.getBoolean("listening_music"));
    }

}
