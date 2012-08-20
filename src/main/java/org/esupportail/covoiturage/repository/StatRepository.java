package org.esupportail.covoiturage.repository;

import java.util.List;

import org.esupportail.covoiturage.domain.Stat;

public interface StatRepository {

    public enum CounterType {
        TOTAL_KILOMETERS
    }

    public enum StatType {
        LOGINS, REGISTRATIONS, ROUTES, SEARCHES
    }

    public enum StatPeriod {
        WEEK, MONTH, YEAR
    }

    void addToCounter(CounterType type, int value);

    int getCounter(CounterType type);

    void incrementStatistic(StatType type);

    List<Stat> getStatistics(StatType type, StatPeriod period);

}
