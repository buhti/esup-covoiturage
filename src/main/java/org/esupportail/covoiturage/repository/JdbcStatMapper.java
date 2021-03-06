package org.esupportail.covoiturage.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.esupportail.covoiturage.domain.Stat;
import org.esupportail.covoiturage.repository.StatRepository.StatType;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * Ce mapper permet d'instancier une entrée de statistique à partir de son
 * entrée dans la base de données.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Component
public class JdbcStatMapper implements RowMapper<Stat> {

    @Override
    public Stat mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Stat(StatType.values()[rs.getInt("stat_type")], rs.getInt("stat_value"),
                new DateTime(rs.getDate("stat_date").getTime()));
    }

}
