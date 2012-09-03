package org.esupportail.covoiturage.database;

/**
 * Cette classe permet de manipuler des numéros de version.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class DatabaseVersion {

    private final int major;
    private final int minor;
    private final int micro;

    /**
     * Retourne la version par défaut utilisée pour le versionnage du schéma de
     * l'application.
     *
     * @return Version 0.0.0
     */
    public static DatabaseVersion zero() {
        return new DatabaseVersion(0, 0, 0);
    }

    /**
     * Constructeur.
     *
     * @param major Version majeure
     * @param minor Version mineure
     * @param micro Version micro
     */
    public DatabaseVersion(int major, int minor, int micro) {
        this.major = major;
        this.minor = minor;
        this.micro = micro;
    }

    /**
     * Constructeur.
     *
     * @param version Version au format X.X.X
     */
    public DatabaseVersion(String version) {
        String[] parts = version.split("\\.");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid format");
        }

        this.major = Integer.parseInt(parts[0]);
        this.minor = Integer.parseInt(parts[1]);
        this.micro = Integer.parseInt(parts[2]);
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + micro;
    }

    @Override
    public int hashCode() {
        return (major << 24) + (minor << 16) + (micro << 8);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DatabaseVersion)) {
            return false;
        }
        return hashCode() == object.hashCode();
    }

    /**
     * @param version
     * @return <code>true<code> si la version actuelle est inférieure à la version spécifiée.
     */
    public boolean lessThan(DatabaseVersion version) {
        return hashCode() < version.hashCode();
    }

    /**
     * @param version
     * @return <code>true<code> si la version actuelle est supérieure à la version spécifiée.
     */
    public boolean greaterThan(DatabaseVersion version) {
        return hashCode() > version.hashCode();
    }

}