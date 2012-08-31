package org.esupportail.covoiturage.cron;

import java.io.OutputStream;

public interface CliCommand {

    void execute(OutputStream out);

}
