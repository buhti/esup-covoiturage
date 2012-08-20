package org.esupportail.covoiturage.web.controller;

import javax.annotation.Resource;

import org.esupportail.covoiturage.repository.StatRepository;
import org.esupportail.covoiturage.repository.StatRepository.StatPeriod;
import org.esupportail.covoiturage.repository.StatRepository.StatType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/statistiques")
public class StatController {

    @Resource
    private StatRepository statRepository;

    @RequestMapping("connexions")
    public String viewLoginStats() {
        return "stat/chart";
    }

    @RequestMapping("json/{type}/{period}")
    public void requestStatistics(@PathVariable String type, @PathVariable String period) {
        StatType statType = StatType.valueOf(type);
        StatPeriod statPeriod = StatPeriod.valueOf(period);

        statRepository.getStatistics(statType, statPeriod);
    }

}
