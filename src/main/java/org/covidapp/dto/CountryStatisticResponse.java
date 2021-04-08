package org.covidapp.dto;

import java.util.List;

public class CountryStatisticResponse {
    private final List<CountryStatistic> data;

    public CountryStatisticResponse(List<CountryStatistic> data) {
        this.data = data;
    }

    public List<CountryStatistic> getData() {
        return data;
    }


}
