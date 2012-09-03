package org.esupportail.covoiturage.cron;

import java.io.OutputStream;

import javax.annotation.Resource;

import org.esupportail.covoiturage.repository.RouteRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Cette commande permet de supprimer de la base de données tous les trajets
 * expirés. Le délai d'expiration est configurable.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Component
public class DeleteExpiredRoutesCommand implements CliCommand {

    @Resource
    RouteRepository routeRepository;

    @Value("${cron.daysBeforeDeletingRecurrentRoutes}")
    private int daysOfExpirationRecurrentRoutes;

    @Value("${cron.daysBeforeDeletingOccasionalRoutes}")
    private int daysOfExpirationOccasionalRoutes;

    @Override
    public void execute(OutputStream out) {
        routeRepository.deleteExpiredRecurrentRoutes(daysOfExpirationRecurrentRoutes);
        routeRepository.deleteExpiredOccasionalRoutes(daysOfExpirationOccasionalRoutes);
    }

}
