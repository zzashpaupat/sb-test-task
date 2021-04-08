package org.covidapp.dto;

public class CountryStatistic {
    private final String countrySlug;

    private final StatisticEntry min;
    private final StatisticEntry max;

    public CountryStatistic(String countrySlug, StatisticEntry min, StatisticEntry max) {
        this.countrySlug = countrySlug;
        this.min = min;
        this.max = max;
    }

    public String getCountrySlug() {
        return countrySlug;
    }

    public StatisticEntry getMin() {
        return min;
    }

    public StatisticEntry getMax() {
        return max;
    }

    @Override
    public String toString() {
        return "CountryStatistic{" +
                "countrySlug='" + countrySlug + '\'' +
                ", min=" + min +
                ", max=" + max +
                '}';
    }
}
