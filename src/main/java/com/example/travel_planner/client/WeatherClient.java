package com.example.travel_planner.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Client to fetch weather data from OpenWeatherMap API.
 */

@Component // Tells Spring to manage this class as a component of Spring boot application.
public class WeatherClient {

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.url}")
    private String apiUrl;


    private final RestTemplate restTemplate;

    // Constructor injection (recommended)
    @Autowired
    public WeatherClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherData getWeatherForCity(String cityName){
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, cityName, apiKey);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Class<String> result = method(params... , String.class): <T> is defined to be String at runtime.
        // means: Hey Spring, please GET this URL and convert the response body to String.
        /**
         * getForEntity: maes GET request
         * Returns ResponseEntity with body as String
         * Spring handles connection and response parsing.
         * */
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode json = mapper.readTree(response.getBody());
            String description = json.path("weather").get(0).path("description").asText();
            double temperature = json.path("main").path("temp").asDouble();
            return new WeatherData(description, temperature);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // ObjectMapper is parser
        // readTree parses the JSON string into a tree of JsonNode
        // You can then navigate it like a tree


    }

    /**
     * Simple POJO to hold a weather result.
     */
    public static class  WeatherData{
        private String description;
        private double temperature;

        public WeatherData(String description, double temperature) {
            this.description = description;
            this.temperature = temperature;
        }

        public String getDescription() { return description; }
        public double getTemperature() { return temperature; }
    }




}
