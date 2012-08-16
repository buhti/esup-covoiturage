package org.esupportail.covoiturage.repository;

import org.esupportail.covoiturage.domain.StatType;


public interface StatRepository {

    void incrementStatistic(StatType type);

}
