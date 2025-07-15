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

    /**
     * Constructor with dependency injection.
     *
     * @param restTemplate Spring-injected RestTemplate
     */
    @Autowired
    public WeatherClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
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
            /**
             * Makes an HTTP GET request to the given URL and retrieves the response body as a String.
             * “Hey Spring, please GET this URL and convert the response body to a String.”
             * Uses RestTemplate's getForObject() method to perform a synchronous call.
             * Automatically handles connection, request execution, and response parsing.
             *
             * @param url the complete URL to call (including query parameters)
             * @return the response body as a plain String
             */
            String response = restTemplate.getForObject(url, String.class);

            /**
             * Parses the JSON response String into a Jackson JsonNode tree.
             *
             * Uses ObjectMapper's readTree() method to convert the raw JSON text
             * into a navigable tree structure, enabling easy access to fields
             * without defining a full Java class.
             *
             * @param response the JSON response body as a String
             * @return a JsonNode representing the root of the JSON tree
             */
            JsonNode root = objectMapper.readTree(response);

            /**
             * Extracts the weather description from the JSON tree.
             *
             * Navigates to the "weather" array in the JSON, selects the first object,
             * and retrieves the value of its "description" field as text.
             *
             * Example JSON path: weather[0].description
             *
             * @param root the root JsonNode of the parsed JSON response
             * @return the weather description text
             */
            String description = root.path("weather").get(0).path("description").asText();

            /**
             * Extracts the temperature value from the JSON tree.
             *
             * Navigates to the "main" object in the JSON and retrieves
             * the "temp" field as a double value representing the temperature in Celsius.
             *
             * Example JSON path: main.temp
             *
             * @param root the root JsonNode of the parsed JSON response
             * @return the temperature value in Celsius
             */
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
