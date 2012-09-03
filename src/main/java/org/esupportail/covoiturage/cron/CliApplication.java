package org.esupportail.covoiturage.cron;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Ce point d'entrée permet est conçu pour être lancé au travers d'une interface
 * en ligne de commande par le biai de crontab. Son but est d'exéctuer les
 * diverses opérations de maintenance de l'application.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class CliApplication {

    /**
     * Point d'entrée de l'application.
     *
     * @param args Commandes possibles : <code>users</code>, <code>routes</code>
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            printUsage();
            return;
        }

        ApplicationContext ctx = new AnnotationConfigApplicationContext("org.esupportail.covoiturage.config");

        String commandName = args[0];
        CliCommand command = null;

        if (commandName.equals("users")) {
            command = ctx.getBean(NotifyRouteExpirationCommand.class);
        } else if (commandName.equals("routes")) {
            command = ctx.getBean(DeleteExpiredRoutesCommand.class);
        } else {
            printUsage();
            return;
        }

        command.execute(System.out);
    }

    private static void printUsage() {
    }

}
