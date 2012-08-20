package org.esupportail.covoiturage.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.esupportail.covoiturage.domain.Stat;
import org.esupportail.covoiturage.repository.StatRepository;
import org.esupportail.covoiturage.repository.StatRepository.StatPeriod;
import org.esupportail.covoiturage.repository.StatRepository.StatType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public @ResponseBody List<Stat> requestStatistics(@PathVariable String type, @PathVariable String period) {
        StatType statType = StatType.valueOf(type);
        StatPeriod statPeriod = StatPeriod.valueOf(period);

        return statRepository.getStatistics(statType, statPeriod);
    }

}
