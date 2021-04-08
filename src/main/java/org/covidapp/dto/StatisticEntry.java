package org.covidapp.dto;

import java.time.LocalDate;
import java.util.List;

public class StatisticEntry {
    // there could be more than one date with max/min cases
    private final List<LocalDate> date;
    private final long cases;

    public StatisticEntry(List<LocalDate> date, long cases) {
        this.date = date;
        this.cases = cases;
    }

    public List<LocalDate> getDates() {
        return date;
    }

    public long getCases() {
        return cases;
    }

    @Override
    public String toString() {
        return "StatisticEntry{" +
                "date=" + date +
                ", casesCount=" + cases +
                '}';
    }
}
