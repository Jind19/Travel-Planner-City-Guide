package com.example.travel_planner.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a city the user wants to visit,
 * including the fetched weather info.
 * */
@Document(collection = "cities")
public class City {
    /** Unique MongoDB Identifier. */
    @Id
    private String id;

    /** Name of city. Must not be blank. */
    @NotBlank(message = "City name is required.")
    private String name;

    /** Country of the city (optional, but nice to have). */
    private String country;

    /** Current weather description fetched from OpenWeather. */
    private String weatherDescription;

    /** Current temperature in Celsius fetched from  OpenWeather.*/
    private double temperature;

    public City() {
    }

    public City(double temperature, String weatherDescription, String country, String name) {
        this.temperature = temperature;
        this.weatherDescription = weatherDescription;
        this.country = country;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
