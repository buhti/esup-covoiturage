package org.esupportail.covoiturage.cron;

import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;

import org.esupportail.covoiturage.repository.CustomerRepository;
import org.esupportail.covoiturage.repository.RouteRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Cette commande permet de supprimer de la base de données tous les
 * utilisateurs inactifs depuis un certain nombre de jours ainsi que les trajets
 * qu'ils ont créés.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Component
public class DeleteInactiveUsersCommand implements CliCommand {

    @Resource
    CustomerRepository customerRepository;

    @Resource
    RouteRepository routeRepository;

    @Value("${cron.maxInactivityDuration}")
    private int maxInactivityDuration;

    @Override
    public void execute(OutputStream out) {
        List<Long> customerIDs = customerRepository.findInactiveCustomersID(maxInactivityDuration);
        for (long id : customerIDs) {
            customerRepository.deleteCustomer(id);
            routeRepository.deleteRoutesByOwner(id);
        }
    }

}
