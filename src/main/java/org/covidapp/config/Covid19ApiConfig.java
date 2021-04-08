package org.covidapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("covid19.api")
public class Covid19ApiConfig {
    private String url;
    private int maxRetryTimes;
    private int retryMinBackoffSeconds;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public int getRetryMinBackoffSeconds() {
        return retryMinBackoffSeconds;
    }

    public void setRetryMinBackoffSeconds(int retryMinBackoffSeconds) {
        this.retryMinBackoffSeconds = retryMinBackoffSeconds;
    }
}
