package org.covidapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CovidCasesService {
    private final Covid19Api covid19Api;

    @Autowired
    public CovidCasesService(Covid19Api covid19Api) {
        this.covid19Api = covid19Api;
    }

    @Cacheable("stats")
    public Map<LocalDate, Long> getCasesByDay(String countrySlug) {
        List<Covid19Api.Statistic> statistic = covid19Api.getStatistic(countrySlug);
        if (statistic == null || statistic.isEmpty()) {
            return Map.of();
        }

        statistic.sort(Comparator.comparing(Covid19Api.Statistic::getDateTime));
        Map<LocalDate, Long> casesByDay = new HashMap<>();

        // we have only cumulative numbers, so we need to subtract previous day to get today's new cases
        Covid19Api.Statistic firstEntry = statistic.get(0);
        long previous = firstEntry.getCases();
        casesByDay.put(firstEntry.getDateTime().toLocalDate(), previous);

        for (int i = 1; i < statistic.size(); i++) {
            Covid19Api.Statistic entry = statistic.get(i);
            long current = entry.getCases();
            casesByDay.put(entry.getDateTime().toLocalDate(), current - previous);
            previous = current;
        }

        return casesByDay;
    }
}
