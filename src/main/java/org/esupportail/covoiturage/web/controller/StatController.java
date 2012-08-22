package org.esupportail.covoiturage.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.esupportail.covoiturage.domain.Stat;
import org.esupportail.covoiturage.repository.StatRepository;
import org.esupportail.covoiturage.repository.StatRepository.StatPeriod;
import org.esupportail.covoiturage.repository.StatRepository.StatType;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/statistiques")
public class StatController {

    @Resource
    private StatRepository statRepository;

    @RequestMapping("connexions")
    public String viewLoginStats(Model model) {
        model.addAttribute("type", StatType.LOGINS.name());
        return "stat/chart";
    }

    @RequestMapping("inscriptions")
    public String viewRegistrationStats(Model model) {
        model.addAttribute("type", StatType.REGISTRATIONS.name());
        return "stat/chart";
    }

    @RequestMapping("trajets")
    public String viewRouteStats(Model model) {
        model.addAttribute("type", StatType.ROUTES.name());
        return "stat/chart";
    }

    @RequestMapping("recherches")
    public String viewSearchStats(Model model) {
        model.addAttribute("type", StatType.SEARCHES.name());
        return "stat/chart";
    }

    @RequestMapping("json/{type}/{period}")
    public @ResponseBody List<Stat> requestStatistics(@PathVariable String type, @PathVariable String period) {
        StatType statType = StatType.valueOf(type);
        StatPeriod statPeriod = StatPeriod.valueOf(period);

        return statRepository.getStatistics(statType, statPeriod);
    }

}
