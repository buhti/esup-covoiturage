package org.esupportail.covoiturage.web.controller;

import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;

import org.esupportail.covoiturage.command.CliCommand;
import org.esupportail.covoiturage.command.DeleteExpiredRoutesCommand;
import org.esupportail.covoiturage.command.DeleteInactiveUsersCommand;
import org.esupportail.covoiturage.command.NotifyRouteExpirationCommand;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Ce contrôleur permet d'exécuter les diverses opérations de maintenance de
 * l'application.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Controller
@RequestMapping("/cron")
public class CronController {

    @Resource
    private DeleteExpiredRoutesCommand deleteExpiredRoutesCommand;

    @Resource
    private DeleteInactiveUsersCommand deleteInactiveUsersCommand;

    @Resource
    private NotifyRouteExpirationCommand notifyRouteExpirationCommand;

    @RequestMapping(value = "delete-expired-routes", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteExpiredRoutes() {
        return executeCommand(deleteExpiredRoutesCommand);
    }

    @RequestMapping(value = "delete-inactive-users", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteInactiveUsers() {
        return executeCommand(deleteInactiveUsersCommand);
    }

    @RequestMapping(value = "notify-route-expiration", method = RequestMethod.HEAD)
    @ResponseBody
    public String notifyRouteExpiration() {
        return executeCommand(notifyRouteExpirationCommand);
    }

    private String executeCommand(CliCommand command) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        command.execute(out);
        return out.toString();
    }

}
