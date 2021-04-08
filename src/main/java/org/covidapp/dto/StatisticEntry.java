package org.covidapp.dto;

import java.time.LocalDate;
import java.util.List;

public class StatisticEntry {
    // there could be more than one date with max/min cases
    private final List<LocalDate> date;
    private final long casesCount;

    public StatisticEntry(List<LocalDate> date, long casesCount) {
        this.date = date;
        this.casesCount = casesCount;
    }

    public List<LocalDate> getDates() {
        return date;
    }

    public long getCasesCount() {
        return casesCount;
    }

    @Override
    public String toString() {
        return "StatisticEntry{" +
                "date=" + date +
                ", casesCount=" + casesCount +
                '}';
    }
}
