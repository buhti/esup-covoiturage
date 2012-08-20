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
import org.esupportail.covoiturage.repository.DataRepository;
import org.esupportail.covoiturage.repository.RouteRepository;
import org.esupportail.covoiturage.repository.StatRepository;
import org.esupportail.covoiturage.repository.StatRepository.StatType;
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
    private DataRepository dataRepository;

    @Resource
    private RouteRepository routeRepository;

    @Resource
    private StatRepository statRepository;

    @Value("${app.search.resultsPerPage}")
    private int resultsPerPage;

    @ModelAttribute("results")
    private List<Route> getSearchResults() {
        return new ArrayList<Route>();
    }

    @ModelAttribute("criterias")
    private Map<String, Object> getSearchCriterias() {
        return new HashMap<String, Object>();
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/recherche", method = RequestMethod.GET)
    public String searchForm(Model model) {
        model.addAttribute(new SearchForm());
        model.addAttribute("data", dataRepository);

        // Remove previous search results
        ((Map<String, Object>) model.asMap().get("criterias")).clear();
        ((List<Route>) model.asMap().get("results")).clear();

        // SearchForm is automatically injected to the model.
        return "search/form";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/recherche", method = RequestMethod.POST)
    public String search(@Valid SearchForm form, BindingResult formBinding, Model model) {
        if (formBinding.hasErrors()) {
            model.addAttribute("data", dataRepository);
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
        }

        try {
            // Geocode the destination address
            to = geocoderService.geocode(form.getTo());
        } catch (LocationNotFoundException e) {
            formBinding.rejectValue("to", "geocoding.error", "geocoding.error");
        }

        if (formBinding.hasErrors()) {
            model.addAttribute("data", dataRepository);
            return "search/form";
        }

        Map<String, Object> criterias = (Map<String, Object>) model.asMap().get("criterias");
        List<Route> results = (List<Route>) model.asMap().get("results");

        date = form.getDate().toDateTime();

        criterias.clear();
        criterias.put("from", from.getCity());
        criterias.put("to", to.getCity());
        criterias.put("date", date);
        criterias.put("fromTolerance", dataRepository.getDistanceTolerances().get(form.getFromTolerance()));
        criterias.put("toTolerance", dataRepository.getDistanceTolerances().get(form.getToTolerance()));
        criterias.put("dateTolerance", dataRepository.getDateTolerances().get(form.getDateTolerance()));

        List<Route> routes = routeRepository.findRoutesByTolerance(from, form.getFromTolerance(), to,
                form.getToTolerance(), date, form.getDateTolerance());

        results.clear();
        results.addAll(routes);

        statRepository.incrementStatistic(StatType.SEARCHES);

        return "redirect:/recherche/resultats";
    }

    @RequestMapping(value = "/recherche/resultats", method = RequestMethod.GET)
    public String results(@ModelAttribute("criterias") Map<String, Object> criterias,
            @ModelAttribute("results") List<Route> routes, Model model) {
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
