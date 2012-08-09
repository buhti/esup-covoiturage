package org.esupportail.covoiturage.web.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String home(Principal user) {
        return user != null ? "redirect:/recherche" : "errors/401";
    }

}
