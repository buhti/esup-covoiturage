package org.esupportail.covoiturage.repository;

import javax.annotation.Resource;

import org.esupportail.covoiturage.domain.StatType;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcStatRepository implements StatRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void incrementStatistic(StatType type) {
        int rawType = type.getRawValue();
        try {
            // Try to update current value
            long id = jdbcTemplate.queryForLong(SELECT_CURRENT_STAT, rawType);
            jdbcTemplate.update(UPDATE_CURRENT_VALUE, id);
        } catch (EmptyResultDataAccessException e) {
            // Insert a new record
            jdbcTemplate.update(INSERT_STAT, rawType);
        }
    }

    private static final String INSERT_STAT = "INSERT INTO Stat (stat_type, stat_value, stat_date) VALUES (?, 1, CURDATE())";

    private static final String SELECT_CURRENT_STAT = "SELECT stat_id FROM Stat WHERE stat_type = ? AND stat_date = CURDATE()";

    private static final String UPDATE_CURRENT_VALUE = "UPDATE Stat SET stat_value = stat_value + 1 WHERE stat_id = ?";

}
