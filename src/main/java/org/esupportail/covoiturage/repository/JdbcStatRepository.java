package org.esupportail.covoiturage.repository;

import java.util.List;

import javax.annotation.Resource;

import org.esupportail.covoiturage.domain.Stat;

import org.joda.time.DateTime;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Ce service permet de manipuler les informations relatives aux statistiques.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Repository
public class JdbcStatRepository implements StatRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private JdbcStatMapper statMapper;

    @Override
    public void addToCounter(CounterType type, int value) {
        jdbcTemplate.update(INSERT_OR_UPDATE_COUNTER, type.ordinal(), value);
    }

    @Override
    public int getCounter(CounterType type) {
        try {
            return jdbcTemplate.queryForInt(SELECT_COUNTER, type.ordinal());
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public void incrementStatistic(StatType type) {
        jdbcTemplate.update(INSERT_OR_UPDATE_STAT, type.ordinal());
    }

    @Override
    public List<Stat> getStatistics(StatType type, StatPeriod period) {
        String sql = null;
        DateTime dateLimit = null;
        switch (period) {
            case WEEK:
                sql = SELECT_STATS_WEEK;
                dateLimit = DateTime.now().minusWeeks(1);
                break;

            case MONTH:
                sql = SELECT_STATS_MONTH;
                dateLimit = DateTime.now().minusMonths(1);
                break;

            case YEAR:
                sql = SELECT_STATS_YEAR;
                dateLimit = DateTime.now().minusYears(1);
                break;
        }

        return jdbcTemplate.query(sql, statMapper, type.ordinal(), dateLimit.toDate());
    }

    private static final String INSERT_OR_UPDATE_COUNTER = "INSERT INTO StatCounter (count_key, count_value) VALUES (?, ?) ON DUPLICATE KEY UPDATE count_value = count_value + VALUES(count_value)";
    private static final String SELECT_COUNTER = "SELECT count_value FROM StatCounter WHERE count_key = ?";
    private static final String INSERT_OR_UPDATE_STAT = "INSERT INTO Stat (stat_type, stat_date, stat_value) VALUES (?, CURDATE(), 1) ON DUPLICATE KEY UPDATE stat_value = stat_value + 1";

    private static final String SELECT_STATS_SUM = "SELECT stat_type, SUM(stat_value) AS stat_value, stat_date FROM Stat WHERE stat_type = ? AND stat_date >= ? ";
    private static final String SELECT_STATS_WEEK = "SELECT * FROM Stat WHERE stat_type = ? AND stat_date >= ?";
    private static final String SELECT_STATS_MONTH = SELECT_STATS_WEEK;
    private static final String SELECT_STATS_YEAR = SELECT_STATS_SUM + "GROUP BY YEAR(stat_date), MONTH(stat_date)";

}
