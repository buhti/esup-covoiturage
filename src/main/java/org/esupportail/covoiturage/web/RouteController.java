package org.esupportail.covoiturage.web;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.exception.RouteNotFoundException;
import org.esupportail.covoiturage.repository.RouteRepository;
import org.esupportail.covoiturage.web.form.RouteForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RouteController {

	@Resource
	private RouteRepository routeRepository;

	@Resource(name = "predefinedLocations")
	private Map<String, String> predefinedLocations;

	@Resource(name = "possibleStatuses")
	private Map<String, String> possibleStatuses;

	@Resource(name = "availableSeats")
	private Map<String, String> availableSeats;

	@RequestMapping(value = "/route/create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute(new RouteForm(predefinedLocations, possibleStatuses, availableSeats));
		return "routes/create";
	}

	@RequestMapping(value = "/route/create", method = RequestMethod.POST)
	public String create(@Valid RouteForm form, BindingResult formBinding, Model model) {
		if (formBinding.hasErrors()) {
			return null;
		}
		Long routeId = routeRepository.createRoute(form.createRoute());
		return "redirect:routes/" + routeId;
	}

	@RequestMapping(value = "/route/{routeId}")
	public String routeView(@PathVariable Long routeId, Model model, HttpServletResponse response) throws IOException {
		try {
			Route route = routeRepository.findOneById(routeId);
			model.addAttribute("route", route);
			return "routes/view";
		} catch (RouteNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}

}
