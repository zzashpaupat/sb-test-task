package org.covidapp.controller;

import org.covidapp.dto.CountryStatistic;
import org.covidapp.dto.CountryStatisticRequest;
import org.covidapp.service.CountryService;
import org.covidapp.service.CovidStatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stats")
public class CovidStatisticController {
    private static final Logger logger = LoggerFactory.getLogger(CovidStatisticController.class);

    private final CountryService countryService;
    private final CovidStatisticService covidStatisticService;

    @Autowired
    public CovidStatisticController(CountryService countryService,
                                    CovidStatisticService covidStatisticService) {
        this.countryService = countryService;
        this.covidStatisticService = covidStatisticService;
    }
    @PostMapping(value = "/covid")
    @ResponseBody
    public ResponseEntity<List<CountryStatistic>> calculateCovidStats(@RequestBody CountryStatisticRequest request) {
        Set<String> countrySlugs = request.getCountries();
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        if (startDate.isAfter(endDate)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // filter slugs by API-known values to reduce unnecessary API calls
        Set<String> filteredSlugs = countryService.filterByExistingSlugs(countrySlugs);

        List<CountryStatistic> countryStatistics = filteredSlugs.stream()
                .map(slug -> covidStatisticService.getMinAndMaxForCountry(slug, startDate, endDate))
                .collect(Collectors.toList());
        return ResponseEntity.ok(countryStatistics);
    }
}
