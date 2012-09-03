package org.esupportail.covoiturage.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * Cette classe représente un ensemble de modifications à application au schéma
 * de l'application.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class DatabaseChangeSet implements Comparable<DatabaseChangeSet> {

    private final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    private final DatabaseVersion version;

    /**
     * Constructeur.
     *
     * @param version Version du schéma après application des modifications
     */
    public DatabaseChangeSet(DatabaseVersion version) {
        this.version = version;
    }

    /**
     * Retourne la version du schéma après application des modifications.
     *
     * @return Version
     */
    public DatabaseVersion getVersion() {
        return version;
    }

    /**
     * Ajoute un script SQL.
     *
     * @param resource Emplacement du fichier de script
     */
    public void add(Resource resource) {
        populator.addScript(resource);
    }

    @Override
    public int hashCode() {
        return version.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DatabaseChangeSet)) {
            return false;
        }
        DatabaseChangeSet changeSet = (DatabaseChangeSet) o;
        return version.equals(changeSet.version);
    }

    @Override
    public int compareTo(DatabaseChangeSet changeSet) {
        if (version.lessThan(changeSet.getVersion())) {
            return -1;
        } else if (version.greaterThan(changeSet.getVersion())) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Applique les modifications au schéma de l'application puis met à jour la
     * version de ce dernier.
     *
     * @param dataSource Pointeur de connexion vers la base de données
     * @return Version du schéma après application des modifications
     */
    public DatabaseVersion apply(DataSource dataSource) {
        Connection connection = getTransactionalConnection(dataSource);
        try {
            try {
                populator.populate(connection);
                updateDatabaseVersion(version, connection);
            } catch (SQLException e) {
                rollbackTransaction(connection);
                throw new RuntimeException("Failed to apply", e);
            } catch (RuntimeException e) {
                rollbackTransaction(connection);
                throw e;
            }
            commitTransaction(connection);
            return version;
        } finally {
            closeConnection(connection);
        }
    }

    private Connection getTransactionalConnection(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            beginTransaction(connection);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Could not get JDBC Connection", e);
        }
    }

    private void beginTransaction(Connection connection) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException("Could not begin database transaction", e);
        }
    }

    private void updateDatabaseVersion(DatabaseVersion version, Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE DatabaseVersion SET VALUE = ?");
            statement.setString(1, version.toString());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update database version", e);
        }
    }

    private void commitTransaction(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Could not commit", e);
        }
    }

    private void rollbackTransaction(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("Could not rollback", e);
        }
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            // ignore
        }
    }

}
