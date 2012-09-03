package org.esupportail.covoiturage.web.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Ce contrôleur permet de rediriger l'utilisateur vers le point d'entrée de
 * l'application.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String home(Principal user) {
        return user != null ? "redirect:/recherche" : "errors/401";
    }

}
