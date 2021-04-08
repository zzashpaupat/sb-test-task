package org.covidapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final Covid19Api covid19Api;

    @Autowired
    public CountryService(Covid19Api covid19Api) {
        this.covid19Api = covid19Api;
    }

    /**
     * @param countrySlugs
     * @return Country slugs available for statistic querying
     */
    public Set<String> filterByExistingSlugs(Set<String> countrySlugs) {
        if (countrySlugs == null) {
            return Set.of();
        }
        return covid19Api.getAllCountries().stream()
                .map(Covid19Api.Country::getSlug)
                .filter(countrySlugs::contains)
                .collect(Collectors.toSet());
    }
}
