package org.esupportail.covoiturage.domain;

public enum StatType {

    LOGINS(1), REGISTRATIONS(2), ROUTES(3), SEARCHES(4);

    private int rawValue;

    private StatType(int value) {
        this.rawValue = value;
    }

    public int getRawValue() {
        return rawValue;
    }

}
