package org.esupportail.covoiturage.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.repository.RouteRepository;
import org.esupportail.covoiturage.web.form.SearchForm;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({ "results" })
public class SearchController {

    @Resource
    private RouteRepository routeRepository;

    @Resource(name = "distanceTolerances")
    private Map<String, String> distanceTolerances;

    @Resource(name = "predefinedLocations")
    private Map<String, String> predefinedLocations;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchForm(Model model) {
        model.addAttribute(new SearchForm(distanceTolerances, predefinedLocations));
        return "search/form";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@Valid SearchForm form, BindingResult formBinding, Model model) {
        if (formBinding.hasErrors()) {
            return null;
        }
        model.addAttribute("results", null);
        return "search/results";
    }

    @RequestMapping(value = "/search/results/{page}", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody List<Route> results(@PathVariable Integer page, @ModelAttribute("results") PagedListHolder<Route> results) {
        if (results.getPageCount() > page) {
            results.setPage(page);
            return results.getPageList();
        }
        return new ArrayList<Route>();
    }

}
