package org.esupportail.covoiturage.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.exception.LocationNotFoundException;
import org.esupportail.covoiturage.repository.FormRepository;
import org.esupportail.covoiturage.repository.RouteRepository;
import org.esupportail.covoiturage.service.GeocoderService;
import org.esupportail.covoiturage.web.form.SearchForm;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({ "results", "criterias" })
public class SearchController {

    @Resource
    private GeocoderService geocoderService;

    @Resource
    private FormRepository formRepository;

    @Resource
    private RouteRepository routeRepository;

    @Value("${app.search.resultsPerPage}")
    private int resultsPerPage;

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

    @RequestMapping(value = "/recherche", method = RequestMethod.GET)
    public String searchForm(@ModelAttribute("criterias") Map<String, Object> criterias, @ModelAttribute("results") List<Route> results) {
        // Remove previous search results
        criterias.clear();
        results.clear();

        // SearchForm is automatically injected to the model.
        return "search/form";
    }

    @RequestMapping(value = "/recherche", method = RequestMethod.POST)
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

        return "redirect:/recherche/resultats";
    }

    @RequestMapping(value = "/recherche/resultats", method = RequestMethod.GET)
    public String results(@ModelAttribute("criterias") Map<String, Object> criterias, @ModelAttribute("results") List<Route> routes, Model model) {
        model.addAttribute("search", criterias);
        model.addAttribute("empty", routes.isEmpty());
        model.addAttribute("count", routes.size());
        return "search/results";
    }

    @RequestMapping(value = "/recherche/resultats/{page}", method = RequestMethod.GET)
    public String results(@PathVariable Integer page, @ModelAttribute("results") List<Route> routes, Model model) {
        PagedListHolder<Route> results = new PagedListHolder<Route>(routes);
        results.setPageSize(resultsPerPage);

        if (results.getPageCount() >= page) {
            results.setPage(page);
            model.addAttribute("routes", results.getPageList());
        }

        return "search/results-fragment";
    }

}
