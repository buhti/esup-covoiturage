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

public class DatabaseUpgrader {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseUpgrader.class);

	private final DataSource dataSource;
	private final Set<DatabaseChangeSet> changeSets = new TreeSet<DatabaseChangeSet>();

	private DatabaseVersion currentVersion;

	public DatabaseUpgrader(DataSource dataSource) {
		this.dataSource = dataSource;
		this.currentVersion = findCurrentVersion();

		if (logger.isInfoEnabled()) {
			logger.info("Database is at Version " + currentVersion);
		}
	}

	public DatabaseVersion getCurrentVersion() {
		return currentVersion;
	}

	public void addChangeSet(DatabaseChangeSet changeSet) {
		changeSets.add(changeSet);
	}

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
						stmt.execute("select value from DatabaseVersion");
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
						stmt.execute("create table DatabaseVersion (value varchar(11) not null)");
						stmt.execute("insert into DatabaseVersion (value) values ('0.0.0')");
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
