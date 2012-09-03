package org.esupportail.covoiturage.cron;

import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.domain.RouteRecurrent;
import org.esupportail.covoiturage.repository.RouteRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * Cette commande permet d'envoyer un message aux créateurs de trajets fréquents
 * sur le point d'expirer.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Component
public class NotifyRouteExpirationCommand implements CliCommand {

    @Resource
    RouteRepository routeRepository;

    @Resource
    MailSender mailSender;

    @Resource
    MessageSource messageSource;

    @Resource
    Locale locale;

    @Value("${smtp.defaultSender}")
    private String sender;

    @Value("${cron.warnBeforeEnd}")
    private int days;

    @Override
    public void execute(OutputStream out) {
        List<Route> routes = routeRepository.findNearlyExpiredRecurrentRoutes(days);

        int i = 0;
        int n = routes.size();

        SimpleMailMessage[] mails = new SimpleMailMessage[n];

        for (; i < n; i++) {
            RouteRecurrent route = (RouteRecurrent) routes.get(i);
            SimpleMailMessage mail = new SimpleMailMessage();

            mail.setTo(route.getOwner().getEmail());
            mail.setFrom(sender);
            mail.setSubject(messageSource.getMessage("cron.warn.subject", null, locale));
            mail.setText(messageSource.getMessage("cron.warn.body", null, locale));

            mails[i] = mail;
        }

        mailSender.send(mails);
    }

}
