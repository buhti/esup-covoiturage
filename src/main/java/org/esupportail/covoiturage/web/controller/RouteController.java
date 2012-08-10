package org.esupportail.covoiturage.web.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.esupportail.covoiturage.domain.Customer;
import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.exception.DistanceNotFoundException;
import org.esupportail.covoiturage.exception.LocationNotFoundException;
import org.esupportail.covoiturage.exception.RouteNotFoundException;
import org.esupportail.covoiturage.repository.DataRepository;
import org.esupportail.covoiturage.repository.RouteRepository;
import org.esupportail.covoiturage.security.CustomerUserDetails;
import org.esupportail.covoiturage.service.GeocoderService;
import org.esupportail.covoiturage.web.form.RouteForm;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RouteController {

    @Resource
    private GeocoderService geocoderService;

    @Resource
    private DataRepository dataRepository;

    @Resource
    private RouteRepository routeRepository;

    @Resource(name = "smartValidator")
    private Validator smartValidator;

    @ModelAttribute("routeForm")
    private RouteForm getRouteForm() {
        return new RouteForm(dataRepository.getPredefinedLocations(), dataRepository.getAvailableSeats(),
                dataRepository.getDays(), dataRepository.getMonths(), dataRepository.getYears(),
                dataRepository.getHoursAndMinutes(), dataRepository.getWeekDays());
    }

    @RequestMapping(value = "/proposer-trajet", method = RequestMethod.GET)
    public String createForm() {
        // RouteForm is automatically injected to the model.
        return "route/create";
    }

    @RequestMapping(value = "/proposer-trajet", method = RequestMethod.POST)
    public String create(@Valid RouteForm form, BindingResult formBinding, Model model, Authentication authentication) {
        // Validate subfrom
        if (form.isRecurrent()) {
            formBinding.pushNestedPath("recurrentForm");
            smartValidator.validate(form.getRecurrentForm(), formBinding);
        } else {
            formBinding.pushNestedPath("occasionalForm");
            smartValidator.validate(form.getOccasionalForm(), formBinding);
        }

        formBinding.popNestedPath();

        // Check if validation failed
        if (formBinding.hasErrors()) {
            return "route/create";
        }

        Location from = null;
        Location to = null;
        int distance = 0;

        try {
            // Geocode the origin address
            from = geocoderService.geocode(form.getFromAddress());
        } catch (LocationNotFoundException e) {
            formBinding.rejectValue("fromAddress", "geocoding.error", "geocoding.error");
            return "route/create";
        }

        try {
            // Geocode the destination address
            to = geocoderService.geocode(form.getToAddress());
        } catch (LocationNotFoundException e) {
            formBinding.rejectValue("toAddress", "geocoding.error", "geocoding.error");
            return "route/create";
        }

        if (from != null && to != null) {
            try {
                // Fetch the distance between the orign and the destination
                distance = geocoderService.distance(from, to);
            } catch (DistanceNotFoundException e) {
                formBinding.rejectValue("fromAddress", "geocoding.error", "geocoding.error");
                formBinding.rejectValue("toAddress", "geocoding.error", "geocoding.error");
                return "route/create";
            }
        }

        // Create a reference the to current authenticated user
        Customer owner = (CustomerUserDetails) authentication.getPrincipal();

        // Create the route
        Route route = form.toRoute(owner, from, to, distance);

        // Persist the route
        Long routeId = routeRepository.createRoute(route);

        // Redirect to the newly created route page
        return "redirect:/trajet/" + routeId;
    }

    @RequestMapping(value = "/trajet/{routeId}")
    public String viewRoute(@PathVariable Long routeId, Model model, HttpServletResponse response) throws IOException {
        try {
            Route route = routeRepository.findOneById(routeId);
            model.addAttribute("route", route);
            return "route/view";
        } catch (RouteNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @RequestMapping(value = "/trajet/{routeId}/supprimer")
    public String deleteRoute(@PathVariable Long routeId, Authentication authentication, HttpServletResponse response)
            throws IOException {
        // Create a reference the to current authenticated user
        Customer currentUser = (CustomerUserDetails) authentication.getPrincipal();
        Route route;

        try {
            route = routeRepository.findOneById(routeId);
        } catch (RouteNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        if (route.getOwner().getId() != currentUser.getId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        routeRepository.deleteRoute(route);
        return "redirect:/mes-trajets";
    }

    @RequestMapping(value = "/mes-trajets")
    public String listCustomerRoutes(Model model, Authentication authentication) {
        // Create a reference the to current authenticated user
        Customer owner = (CustomerUserDetails) authentication.getPrincipal();

        // Get routes
        List<Route> routes = routeRepository.findRoutesByOwner(owner);
        model.addAttribute("routes", routes);
        model.addAttribute("editMode", true);
        return "route/list";
    }

}
