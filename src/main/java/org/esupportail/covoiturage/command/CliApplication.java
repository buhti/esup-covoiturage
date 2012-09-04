package org.esupportail.covoiturage.command;

import java.io.PrintStream;

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
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            printUsage(System.out);
            return;
        }

        ApplicationContext ctx = new AnnotationConfigApplicationContext("org.esupportail.covoiturage.config");

        String commandName = args[0];
        CliCommand command = null;

        if (commandName.equals("notify-route-expiration")) {
            command = ctx.getBean(NotifyRouteExpirationCommand.class);
        } else if (commandName.equals("delete-expired-routes")) {
            command = ctx.getBean(DeleteExpiredRoutesCommand.class);
        } else if (commandName.equals("delete-inactive-users")) {
            command = ctx.getBean(DeleteInactiveUsersCommand.class);
        } else {
            printUsage(System.out);
            return;
        }

        command.execute(System.out);
    }

    private static void printUsage(PrintStream out) {
        out.println("Available commands:");
        out.println("  * notify-route-expiration");
        out.println("  * delete-expired-routes");
        out.println("  * delete-inactive-users");
    }

}
