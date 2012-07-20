package org.esupportail.covoiturage.database;

public class DatabaseVersion {

    private final int major;
    private final int minor;
    private final int micro;

    public static DatabaseVersion zero() {
        return new DatabaseVersion(0, 0, 0);
    }

    public DatabaseVersion(int major, int minor, int micro) {
        this.major = major;
        this.minor = minor;
        this.micro = micro;
    }

    public DatabaseVersion(String version) {
        String[] parts = version.split("\\.");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid format");
        }

        this.major = Integer.parseInt(parts[0]);
        this.minor = Integer.parseInt(parts[1]);
        this.micro = Integer.parseInt(parts[2]);
    }

    public String toString() {
        return major + "." + minor + "." + micro;
    }

    public int hashCode() {
        return (major << 24) + (minor << 16) + (micro << 8);
    }

    public boolean equals(Object object) {
        if (!(object instanceof DatabaseVersion)) {
            return false;
        }
        return hashCode() == object.hashCode();
    }

    public boolean lessThan(DatabaseVersion version) {
        return hashCode() < version.hashCode();
    }

    public boolean greaterThan(DatabaseVersion version) {
        return hashCode() > version.hashCode();
    }

}