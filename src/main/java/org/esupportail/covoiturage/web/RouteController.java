package org.esupportail.covoiturage.web;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.esupportail.covoiturage.domain.Customer;
import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.domain.RouteOccasional;
import org.esupportail.covoiturage.domain.RouteRecurrent;
import org.esupportail.covoiturage.exception.DistanceNotFoundException;
import org.esupportail.covoiturage.exception.LocationNotFoundException;
import org.esupportail.covoiturage.exception.RouteNotFoundException;
import org.esupportail.covoiturage.repository.RouteRepository;
import org.esupportail.covoiturage.security.UserDetailsImpl;
import org.esupportail.covoiturage.service.GeocoderService;
import org.esupportail.covoiturage.web.form.RouteForm;
import org.esupportail.covoiturage.web.form.RouteOccasionalForm;
import org.esupportail.covoiturage.web.form.RouteRecurrentForm;

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
    private RouteRepository routeRepository;

    @Resource(name = "smartValidator")
    private Validator smartValidator;

    @Resource(name = "predefinedLocations")
    private Map<String, String> predefinedLocations;

    @Resource(name = "availableSeats")
    private Map<String, String> availableSeats;

    @ModelAttribute("routeForm")
    private RouteForm getRouteForm() {
        return new RouteForm(predefinedLocations, availableSeats);
    }

    @RequestMapping(value = "/route/create", method = RequestMethod.GET)
    public void createForm() {
        // RouteForm is automatically injected to the model.
        // View is retrieved from the URL mapping.
    }

    @RequestMapping(value = "/route/create", method = RequestMethod.POST)
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
            return null;
        }

        Location from = null;
        Location to = null;
        String distance = null;

        try {
            // Geocode the origin address
            from = geocoderService.geocode(form.getFromAddress());
        } catch (LocationNotFoundException e) {
            formBinding.rejectValue("fromAddress", "geocoding.error", "geocoding.error");
            return null;
        }

        try {
            // Geocode the destination address
            to = geocoderService.geocode(form.getToAddress());
        } catch (LocationNotFoundException e) {
            formBinding.rejectValue("toAddress", "geocoding.error", "geocoding.error");
            return null;
        }

        if (from != null && to != null) {
            try {
                // Fetch the distance between the orign and the destination
                distance = geocoderService.distance(from, to);
            } catch (DistanceNotFoundException e) {
                distance = "? km";
            }
        }

        // Create a reference the to current authenticated user
        Customer owner = new Customer(((UserDetailsImpl) authentication.getPrincipal()).getId());
        
        // Create the route
        Route route;
        if (form.isRecurrent()) {
            RouteRecurrentForm subform = form.getRecurrentForm();
            route = new RouteRecurrent(0, owner, form.isDriver(), form.getSeats(), from, to, distance,
                    subform.getStartDate().toDateTime(), subform.getEndDate().toDateTime(),
                    subform.getWayOutTime(), subform.getWayBackTime());
        } else {
            RouteOccasionalForm subform = form.getOccasionalForm();
            route = new RouteOccasional(0, owner, form.isDriver(), form.getSeats(), from, to, distance,
                    subform.getWayOut().toDateTime(), subform.getWayBack().toDateTime());
        }

        // Persist the route
        Long routeId = routeRepository.createRoute(route);

        // Redirect to the newly created route page
        return "redirect:/route/" + routeId;
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
