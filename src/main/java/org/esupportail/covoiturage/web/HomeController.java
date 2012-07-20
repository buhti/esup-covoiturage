package org.esupportail.covoiturage.web;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String home(Principal user) {
        return user != null ? "redirect:/search" : "errors/401";
    }

}
