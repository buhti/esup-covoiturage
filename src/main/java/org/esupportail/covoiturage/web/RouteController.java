package org.esupportail.covoiturage.web;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.esupportail.covoiturage.domain.Customer;
import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.exception.LocationNotFoundException;
import org.esupportail.covoiturage.exception.RouteNotFoundException;
import org.esupportail.covoiturage.repository.RouteRepository;
import org.esupportail.covoiturage.service.GeocoderService;
import org.esupportail.covoiturage.web.form.RouteForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RouteController {

    @Resource
    private GeocoderService geocoderService;

    @Resource
    private RouteRepository routeRepository;

    @Resource(name = "predefinedLocations")
    private Map<String, String> predefinedLocations;

    @Resource(name = "possibleStatuses")
    private Map<String, String> possibleStatuses;

    @Resource(name = "availableSeats")
    private Map<String, String> availableSeats;

    @ModelAttribute("routeForm")
    private RouteForm getRouteForm() {
        return new RouteForm(predefinedLocations, possibleStatuses, availableSeats);
    }

    @RequestMapping(value = "/route/create", method = RequestMethod.GET)
    public void createForm() {
        // RouteForm is automatically injected to the model.
        // View is retrieved from the URL mapping.
    }

    @RequestMapping(value = "/route/create", method = RequestMethod.POST)
    public String create(@Valid RouteForm form, BindingResult formBinding, Model model) {
        Location from = null;
        Location to = null;

        if (StringUtils.hasText(form.getFromAddress())) {
            try {
                from = geocoderService.geocode(form.getFromAddress());
            } catch (LocationNotFoundException e) {
                formBinding.rejectValue("fromAddress", "geocoding.error");
            }
        }

        if (StringUtils.hasText(form.getToAddress())) {
            try {
                to = geocoderService.geocode(form.getToAddress());
            } catch (LocationNotFoundException e) {
                formBinding.rejectValue("toAddress", "geocoding.error");
            }
        }

        if (formBinding.hasErrors()) {
            return null;
        }

        Route route = new Route(0, new Customer(0), form.getStatus(), form.getSeats(), from, to);
        Long routeId = routeRepository.createRoute(route);
        return "redirect:route/" + routeId;
    }

    @RequestMapping(value = "/route/{routeId}")
    public String routeView(@PathVariable Long routeId, Model model, HttpServletResponse response) throws IOException {
        try {
            Route route = routeRepository.findOneById(routeId);
            model.addAttribute("route", route);
            return "route/view";
        } catch (RouteNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

}
