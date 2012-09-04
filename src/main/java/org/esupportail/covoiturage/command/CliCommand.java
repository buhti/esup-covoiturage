package org.esupportail.covoiturage.command;

import java.io.OutputStream;

/**
 * Cette interface permet de décrire une commande exécutable au travers d'une
 * interface en ligne de commande par le biai de crontab.
 *
 * @see org.esupportail.covoiturage.command.CliApplication
 * @author Florent Cailhol (Anyware Services)
 */
public interface CliCommand {

    /**
     * Exécute la commande et écrit dans le flux de sortie spécifié.
     *
     * @param out Flux de sortie
     */
    void execute(OutputStream out);

}
