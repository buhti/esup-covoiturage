package org.esupportail.covoiturage.cron;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CliApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            printUsage();
            return;
        }

        ApplicationContext ctx = new AnnotationConfigApplicationContext("org.esupportail.covoiturage.config");

        String commandName = args[0];
        CliCommand command = null;

        if (commandName.equals("warn")) {
            command = ctx.getBean(WarnCommand.class);
        } else if (commandName.equals("clean")) {
            command = ctx.getBean(CleanCommand.class);
        } else {
            printUsage();
            return;
        }

        command.execute(System.out);
    }

    private static void printUsage() {
    }

}
