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
import org.esupportail.covoiturage.service.GeocoderService;
import org.esupportail.covoiturage.web.form.RouteForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
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

    @RequestMapping(value = "/proposer-trajet", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute(new RouteForm());
        model.addAttribute("data", dataRepository);
        return "route/create";
    }

    @RequestMapping(value = "/proposer-trajet", method = RequestMethod.POST)
    public String create(@Valid RouteForm form, BindingResult formBinding, Model model, Customer customer) {
        // Validate form and get the route
        Route route = validateFormAndGetRoute(form, formBinding, customer);

        // Check if validation failed
        if (route == null) {
            model.addAttribute("data", dataRepository);
            return "route/create";
        }

        // Persist the route
        Long routeId = routeRepository.createRoute(route);

        // Redirect to the newly created route page
        return "redirect:/trajet/" + routeId;
    }

    @RequestMapping(value = "/trajet/{routeId}/modifier", method = RequestMethod.GET)
    public String editForm(@PathVariable Long routeId, Model model, HttpServletResponse response) throws IOException {
        Route route;
        try {
            route = routeRepository.findOneById(routeId);
        } catch (RouteNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        model.addAttribute(new RouteForm(route));
        model.addAttribute("data", dataRepository);
        return "route/edit";
    }

    @RequestMapping(value = "/trajet/{routeId}/modifier", method = RequestMethod.POST)
    public String edit(@PathVariable Long routeId, @Valid RouteForm form, BindingResult formBinding, Model model, Customer customer) {
        // Validate form and get the route
        Route route = validateFormAndGetRoute(form, formBinding, customer);

        // Check if validation failed
        if (route == null) {
            model.addAttribute("data", dataRepository);
            return "route/edit";
        }

        // Update the route
        routeRepository.updateRoute(customer.getId(), routeId, route);

        // Redirect to the newly updated route page
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
    public String deleteRoute(@PathVariable long routeId, Customer customer) {
        routeRepository.deleteRoute(customer.getId(), routeId);
        return "redirect:/mes-trajets";
    }

    @RequestMapping(value = "/mes-trajets")
    public String listCustomerRoutes(Model model, Customer customer) {
        List<Route> routes = routeRepository.findRoutesByOwner(customer.getId());
        model.addAttribute("routes", routes);
        model.addAttribute("editMode", true);
        return "route/list";
    }

    private Route validateFormAndGetRoute(RouteForm form, BindingResult formBinding, Customer customer) {
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
            return null;
        }

        Location from = null;
        Location to = null;
        int distance = 0;

        try {
            // Geocode the origin address
            from = geocoderService.geocode(form.getFromAddress());
        } catch (LocationNotFoundException e) {
            formBinding.rejectValue("fromAddress", "geocoding.error", "geocoding.error");
        }

        try {
            // Geocode the destination address
            to = geocoderService.geocode(form.getToAddress());
        } catch (LocationNotFoundException e) {
            formBinding.rejectValue("toAddress", "geocoding.error", "geocoding.error");
        }

        // Validate route distance
        if (from != null && to != null) {
            try {
                // Fetch the distance between the origin and the destination
                distance = geocoderService.distance(from, to);
            } catch (DistanceNotFoundException e) {
                formBinding.rejectValue("fromAddress", "geocoding.error", "geocoding.error");
                formBinding.rejectValue("toAddress", "geocoding.error", "geocoding.error");
            }
        }

        if (formBinding.hasErrors()) {
            return null;
        }

        // Create the route
        return form.toRoute(customer, from, to, distance);
    }

}
