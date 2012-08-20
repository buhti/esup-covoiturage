package org.esupportail.covoiturage.domain;

import org.esupportail.covoiturage.repository.StatRepository.StatType;

import org.joda.time.DateTime;

public class Stat {

    private final StatType type;
    private final int value;
    private final DateTime date;

    public Stat(StatType type, int value, DateTime date) {
        this.type = type;
        this.value = value;
        this.date = date;
    }

    public StatType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public DateTime getDate() {
        return date;
    }

}
