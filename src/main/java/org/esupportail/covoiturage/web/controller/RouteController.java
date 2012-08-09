package org.esupportail.covoiturage.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.esupportail.covoiturage.repository.FormRepository;
import org.esupportail.covoiturage.repository.RouteRepository;
import org.esupportail.covoiturage.security.CustomerUserDetails;
import org.esupportail.covoiturage.service.GeocoderService;
import org.esupportail.covoiturage.web.form.RouteForm;
import org.esupportail.covoiturage.web.form.RouteOccasionalForm;
import org.esupportail.covoiturage.web.form.RouteRecurrentForm;
import org.esupportail.covoiturage.web.form.SearchForm;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({ "results", "criterias" })
public class RouteController {

    @Resource
    private GeocoderService geocoderService;

    @Resource
    private FormRepository formRepository;

    @Resource
    private RouteRepository routeRepository;

    @Resource(name = "smartValidator")
    private Validator smartValidator;

    @Value("${app.search.resultsPerPage}")
    private int resultsPerPage;

    @ModelAttribute("routeForm")
    private RouteForm getRouteForm() {
        return new RouteForm(formRepository.getPredefinedLocations(), formRepository.getAvailableSeats(),
                formRepository.getDays(), formRepository.getMonths(), formRepository.getYears(),
                formRepository.getHoursAndMinutes(), formRepository.getWeekDays());
    }

    @ModelAttribute("searchForm")
    private SearchForm getSearchForm() {
        return new SearchForm(formRepository.getPredefinedLocations(), formRepository.getDays(), 
                formRepository.getMonths(), formRepository.getYears(), formRepository.getHoursAndMinutes(), 
                formRepository.getDateTolerances(), formRepository.getDistanceTolerances());
    }

    @ModelAttribute("results")
    private List<Route> getSearchResults() {
        return new ArrayList<Route>();
    }

    @ModelAttribute("criterias")
    private Map<String, Object> getSearchCriterias() {
        return new HashMap<String, Object>();
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
        int distance = 0;

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
                formBinding.rejectValue("fromAddress", "geocoding.error", "geocoding.error");
                formBinding.rejectValue("toAddress", "geocoding.error", "geocoding.error");
                return null;
            }
        }

        // Create a reference the to current authenticated user
        Customer owner = (CustomerUserDetails) authentication.getPrincipal();

        // Create the route
        Route route;
        if (form.isRecurrent()) {
            RouteRecurrentForm subform = form.getRecurrentForm();
            route = new RouteRecurrent(0, owner, form.isDriver(), form.getSeats(), from, to, distance,
                    subform.getStartDate().toDateTime(), subform.getEndDate().toDateTime(),
                    subform.getWayOutTime().toLocalTime(), subform.getWayBackTime().toLocalTime(),
                    subform.getWeekDay());
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

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchForm(@ModelAttribute("criterias") Map<String, Object> criterias, @ModelAttribute("results") List<Route> results) {
        // Remove previous search results
        criterias.clear();
        results.clear();

        // SearchForm is automatically injected to the model.
        return "search/form";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@Valid SearchForm form, BindingResult formBinding, @ModelAttribute("criterias") Map<String, Object> criterias, @ModelAttribute("results") List<Route> results) {
        if (formBinding.hasErrors()) {
            return "search/form";
        }

        Location from = null;
        Location to = null;
        DateTime date = null;

        try {
            // Geocode the origin address
            from = geocoderService.geocode(form.getFrom());
        } catch (LocationNotFoundException e) {
            formBinding.rejectValue("from", "geocoding.error", "geocoding.error");
            return "search/form";
        }

        try {
            // Geocode the destination address
            to = geocoderService.geocode(form.getTo());
        } catch (LocationNotFoundException e) {
            formBinding.rejectValue("to", "geocoding.error", "geocoding.error");
            return "search/form";
        }

        date = form.getDate().toDateTime();

        criterias.clear();
        criterias.put("from", from.getCity());
        criterias.put("to", to.getCity());
        criterias.put("date", date);
        criterias.put("fromTolerance", formRepository.getDistanceTolerances().get(form.getFromTolerance()));
        criterias.put("toTolerance", formRepository.getDistanceTolerances().get(form.getToTolerance()));
        criterias.put("dateTolerance", formRepository.getDateTolerances().get(form.getDateTolerance()));

        List<Route> routes = routeRepository.findRoutesByTolerance(from, form.getFromTolerance(), to, form.getToTolerance(), date, form.getDateTolerance());
        results.clear();
        results.addAll(routes);

        return "redirect:/search/results";
    }

    @RequestMapping(value = "/search/results", method = RequestMethod.GET)
    public String results(@ModelAttribute("criterias") Map<String, Object> criterias, @ModelAttribute("results") List<Route> routes, Model model) {
        model.addAttribute("search", criterias);
        model.addAttribute("empty", routes.isEmpty());
        model.addAttribute("count", routes.size());
        return "search/results";
    }

    @RequestMapping(value = "/search/results/{page}", method = RequestMethod.GET)
    public String resultsJSON(@PathVariable Integer page, @ModelAttribute("results") List<Route> routes, Model model) {
        PagedListHolder<Route> results = new PagedListHolder<Route>(routes);
        results.setPageSize(resultsPerPage);

        if (results.getPageCount() >= page) {
            results.setPage(page);
            model.addAttribute("routes", results.getPageList());
        }

        return "search/results-fragment";
    }

}
