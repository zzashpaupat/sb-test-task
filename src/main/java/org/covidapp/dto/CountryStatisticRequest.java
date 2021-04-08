package org.covidapp.dto;

import java.time.LocalDate;
import java.util.Set;

public class CountryStatisticRequest {
    private Set<String> countries;
    private LocalDate startDate;
    private LocalDate endDate;

    public Set<String> getCountries() {
        return countries;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "CountryStatisticRequest{" +
                "countries=" + countries +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
