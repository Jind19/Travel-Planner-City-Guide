package com.example.travel_planner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class to provide a RestTemplate bean.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Creates and exposes a RestTemplate bean for dependency injection.
     *
     * @return RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
