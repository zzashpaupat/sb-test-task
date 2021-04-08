package org.covidapp.query;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.language.SourceLocation;
import org.covidapp.dto.CountryStatistic;
import org.covidapp.service.CountryService;
import org.covidapp.service.CovidStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static graphql.ErrorType.ValidationError;

@Component
public class CovidStatsQueryResolver implements GraphQLQueryResolver {

    private final CountryService countryService;
    private final CovidStatisticService covidStatisticService;

    @Autowired
    public CovidStatsQueryResolver(CountryService countryService,
                                   CovidStatisticService covidStatisticService) {

        this.countryService = countryService;
        this.covidStatisticService = covidStatisticService;
    }

    public List<CountryStatistic> covidStats(Set<String> countries,
                                             LocalDate startDate,
                                             LocalDate endDate) {
        if (startDate.isAfter(endDate)){
            throw new DateValidationException("startDate should be not after endDate");
        }
        // filter slugs by API-known values to reduce unnecessary API calls
        Set<String> filteredSlugs = countryService.filterByExistingSlugs(countries);

        return filteredSlugs.stream()
                .map(slug -> covidStatisticService.getMinAndMaxForCountry(slug, startDate, endDate))
                .collect(Collectors.toList());
    }

    static class DateValidationException extends RuntimeException implements GraphQLError {

        public DateValidationException(String message) {
            super(message);
        }

        @Override
        public List<SourceLocation> getLocations() {
            return List.of();
        }

        @Override
        public ErrorClassification getErrorType() {
            return ValidationError;
        }
    }
}
