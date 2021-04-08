package org.covidapp.service;

import org.covidapp.dto.CountryStatistic;
import org.covidapp.dto.StatisticEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CovidStatisticService {

    private final CovidCasesService covidCasesService;

    @Autowired
    public CovidStatisticService(CovidCasesService covidCasesService) {
        this.covidCasesService = covidCasesService;
    }


    public CountryStatistic getMinAndMaxForCountry(String countrySlug, LocalDate startDate, LocalDate endDate) {

        Map<LocalDate, Long> casesByDay = covidCasesService.getCasesByDay(countrySlug);

        List<LocalDate> minDates = new ArrayList<>();
        List<LocalDate> maxDates = new ArrayList<>();
        long minCases = Long.MAX_VALUE;
        long maxCases = Long.MIN_VALUE;

        LocalDate endExclusiveBound = endDate.plusDays(1);
        for (LocalDate date = startDate; date.isBefore(endExclusiveBound); date = date.plusDays(1)) {
            Long cases = casesByDay.get(date);
            if (cases != null) {
                if (cases == maxCases) {
                    maxDates.add(date);
                }
                if (cases > maxCases) {
                    maxCases = cases;
                    maxDates = new ArrayList<>();
                    maxDates.add(date);
                }

                if (cases == minCases) {
                    minDates.add(date);
                }
                if (cases < minCases) {
                    minCases = cases;
                    minDates = new ArrayList<>();
                    minDates.add(date);
                }
            }
        }

        return new CountryStatistic(countrySlug,
                new StatisticEntry(minDates, minCases),
                new StatisticEntry(maxDates, maxCases));
    }

}
