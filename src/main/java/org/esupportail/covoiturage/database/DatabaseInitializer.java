package org.esupportail.covoiturage.database;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Cette classe permet de définir le schéma de l'application. Le schéma est
 * décrit au travers d'un ensemble de modifications réparties en scripts SQL.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class DatabaseInitializer {

    private static final String CURRENT_VERSION = "1.0.0";

    private final DatabaseUpgrader upgrader;

    /**
     * Constructeur.
     *
     * @param dataSource Pointeur de connexion vers la base de données
     */
    public DatabaseInitializer(DataSource dataSource) {
        this.upgrader = createUpgrader(dataSource);
    }

    /**
     * Crée le schéma de l'application si ce n'est pas déjà fait. Sinon met à
     * jour ce dernier en fonction de sa version actuelle.
     */
    public void run() {
        upgrader.run();
    }

    private DatabaseUpgrader createUpgrader(DataSource dataSource) {
        DatabaseUpgrader upgrader = new DatabaseUpgrader(dataSource);
        if (upgrader.getCurrentVersion().equals(DatabaseVersion.zero())) {
            addInstallChangeSet(upgrader);
        } else {
            addUpgradeChangeSets(upgrader);
        }
        return upgrader;
    }

    private void addInstallChangeSet(DatabaseUpgrader upgrader) {
        DatabaseChangeSet changeSet = new DatabaseChangeSet(new DatabaseVersion(CURRENT_VERSION));
        changeSet.add(installScript("Customer.sql"));
        changeSet.add(installScript("Route.sql"));
        changeSet.add(installScript("Stat.sql"));
        upgrader.addChangeSet(changeSet);
    }

    private void addUpgradeChangeSets(DatabaseUpgrader upgrader) {
    }

    private Resource installScript(String resource) {
        return new ClassPathResource("META-INF/sql/install/" + resource);
    }

    private Resource upgradeScript(String resource) {
        return new ClassPathResource("META-INF/sql/upgrade/" + resource);
    }

}
