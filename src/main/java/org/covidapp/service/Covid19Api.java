package org.covidapp.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.covidapp.config.Covid19ApiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class Covid19Api {
    private static final Logger logger = LoggerFactory.getLogger(Covid19Api.class);

    private final int maxRetryTimes;
    private final Duration minBackoff;

    private final WebClient webClient;

    @Autowired
    public Covid19Api(WebClient.Builder webClientBuilder,
                      Covid19ApiConfig covid19ApiConfig) {
        this.maxRetryTimes = covid19ApiConfig.getMaxRetryTimes();
        this.webClient = webClientBuilder.baseUrl(covid19ApiConfig.getUrl()).build();
        this.minBackoff = Duration.ofSeconds(covid19ApiConfig.getRetryMinBackoffSeconds());
    }

    @Cacheable("countries")
    public List<Country> getAllCountries() {
        logger.info("Start getting info about Countries");
        List<Country> countries = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/countries").build())
                .retrieve()
                .bodyToFlux(Country.class)
                .retryWhen(Retry.backoff(maxRetryTimes, minBackoff)
                        .filter(Covid19Api::is5xxError))
                .collectList()
                .block();
        logger.info("End getting info about Countries. {} records received", countries.size());
        return countries;
    }

    private static boolean is5xxError(Throwable throwable) {
        return throwable instanceof WebClientResponseException &&
                ((WebClientResponseException) throwable).getStatusCode().is5xxServerError();
    }


    @Cacheable("stats")
    public List<Statistic> getStatistic(String countrySlug) {
        logger.info("Start getting stats for {}", countrySlug);
        List<Statistic> stats = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/total/dayone/country/" + countrySlug + "/status/confirmed").build())
                .retrieve()
                .bodyToFlux(Statistic.class)
                .collectList()
                .block();
        logger.info("End getting stats for {}, {} records received", countrySlug, stats.size());
        return stats;
    }


    @CacheEvict(allEntries = true, cacheNames = {"countries", "stats"})
    @Scheduled(fixedRateString = "${covid19.api.cache-ttl-ms}")
    public void evictCache() {
        logger.info("=== Country and statistics cache evicted ===");
    }

    public static class Statistic {
        @JsonProperty("Country")
        private String country;

        @JsonProperty("Cases")
        private long cases;

        @JsonProperty("Date")
        private LocalDateTime dateTime;

        public String getCountry() {
            return country;
        }

        public long getCases() {
            return cases;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }
    }

    public static class Country {
        @JsonProperty("Country")
        private String country;

        @JsonProperty("Slug")
        private String slug;

        @JsonProperty("ISO2")
        private String ISO2;

        public String getCountry() {
            return country;
        }

        public String getSlug() {
            return slug;
        }

        public String getISO2() {
            return ISO2;
        }
    }
}
