package org.esupportail.covoiturage.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class DatabaseChangeSet implements Comparable<DatabaseChangeSet> {

    private final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    private final DatabaseVersion version;

    public DatabaseChangeSet(DatabaseVersion version) {
        this.version = version;
    }

    public void add(Resource resource) {
        populator.addScript(resource);
    }

    public DatabaseVersion getVersion() {
        return version;
    }

    public int hashCode() {
        return version.hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof DatabaseChangeSet)) {
            return false;
        }
        DatabaseChangeSet changeSet = (DatabaseChangeSet) o;
        return version.equals(changeSet.version);
    }

    public int compareTo(DatabaseChangeSet changeSet) {
        if (version.lessThan(changeSet.getVersion())) {
            return -1;
        } else if (version.greaterThan(changeSet.getVersion())) {
            return 1;
        } else {
            return 0;
        }
    }

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
            PreparedStatement statement = connection.prepareStatement("update DatabaseVersion set value = ?");
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
