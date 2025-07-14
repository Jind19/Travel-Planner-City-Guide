package com.example.travel_planner.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Client to fetch weather data from OpenWeatherMap API.
 */

@Component // Tells Spring to manage this class as a component of Spring boot application.
public class WeatherClient {

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.url}")
    private String apiUrl;


    private final RestTemplate restTemplate = new RestTemplate();



}
