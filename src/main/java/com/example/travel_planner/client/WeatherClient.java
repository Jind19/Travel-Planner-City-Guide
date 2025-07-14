package com.example.travel_planner.client;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Fetches weather data from OpenWeatherMap API.
 */
@Component
public class WeatherClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.url}")
    private String apiUrl;

    @Autowired
    public WeatherClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    /**
     * Fetches current weather for the given city name.
     *
     * @param cityName Name of the city
     * @return WeatherInfo containing description and temperature
     */
    public WeatherInfo getWeatherForCity(String cityName) {
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, cityName, apiKey);

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            String description = root.path("weather").get(0).path("description").asText();
            double temperature = root.path("main").path("temp").asDouble();

            return new WeatherInfo(description, temperature);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage());
        }
    }

    /**
     * Simple POJO to hold weather data.
     */
    public static class WeatherInfo {
        private String description;
        private double temperature;

        public WeatherInfo(String description, double temperature) {
            this.description = description;
            this.temperature = temperature;
        }

        public String getDescription() {
            return description;
        }

        public double getTemperature() {
            return temperature;
        }
    }
}
