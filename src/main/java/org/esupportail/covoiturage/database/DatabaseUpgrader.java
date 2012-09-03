package org.esupportail.covoiturage.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cette classe permet de mettre à jour le schéma de la base.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class DatabaseUpgrader {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUpgrader.class);

    private final DataSource dataSource;
    private final Set<DatabaseChangeSet> changeSets = new TreeSet<DatabaseChangeSet>();

    private DatabaseVersion currentVersion;

    /**
     * Constructeur.
     *
     * @param dataSource Pointeur de connexion vers la base de données
     */
    public DatabaseUpgrader(DataSource dataSource) {
        this.dataSource = dataSource;
        this.currentVersion = findCurrentVersion();

        if (logger.isInfoEnabled()) {
            logger.info("Database is at Version " + currentVersion);
        }
    }

    /**
     * Retourne la version actuelle du schéma.
     *
     * @return Version
     */
    public DatabaseVersion getCurrentVersion() {
        return currentVersion;
    }

    /**
     * Ajoute un ensemble de modification.
     *
     * @param changeSet Ensemble de modification
     */
    public void addChangeSet(DatabaseChangeSet changeSet) {
        changeSets.add(changeSet);
    }

    /**
     * Exécute chaque ensemble de modification afin de mettre à jour le schéma.
     */
    public void run() {
        for (DatabaseChangeSet changeSet : changeSets) {
            if (currentVersion.lessThan(changeSet.getVersion())) {
                if (logger.isInfoEnabled()) {
                    logger.info("Upgrading Database to Version " + changeSet.getVersion());
                }
                currentVersion = changeSet.apply(dataSource);
                if (logger.isInfoEnabled()) {
                    logger.info("Database is at Version " + currentVersion);
                }
            }
        }
    }

    private DatabaseVersion findCurrentVersion() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet result = metadata.getTables(null, null, "DATABASEVERSION", null);
            try {
                if (result.next()) {
                    Statement stmt = connection.createStatement();
                    try {
                        stmt.execute("SELECT value FROM DatabaseVersion");
                        ResultSet queryResult = stmt.getResultSet();
                        try {
                            queryResult.next();
                            String value = queryResult.getString("value");
                            queryResult.close();
                            return new DatabaseVersion(value);
                        } finally {
                            queryResult.close();
                        }
                    } finally {
                        stmt.close();
                    }
                } else {
                    Statement stmt = connection.createStatement();
                    try {
                        stmt.execute("CREATE TABLE DatabaseVersion (value VARCHAR(11) NOT NULL)");
                        stmt.execute("INSERT INTO DatabaseVersion (value) VALUES ('0.0.0')");
                    } finally {
                        stmt.close();
                    }
                    return DatabaseVersion.zero();
                }
            } finally {
                result.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to determine database version", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }

}
