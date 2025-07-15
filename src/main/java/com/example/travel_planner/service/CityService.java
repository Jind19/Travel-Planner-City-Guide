package com.example.travel_planner.service;

import com.example.travel_planner.client.WeatherClient;
import com.example.travel_planner.model.City;
import com.example.travel_planner.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Business logic for managing cities.
 */
@Service
public class CityService {


    private final CityRepository cityRepository;
    private final WeatherClient weatherClient;

    @Autowired
    public CityService(CityRepository cityRepository, WeatherClient weatherClient) {
        this.cityRepository = cityRepository; // DAO - 'Data Access Object': A repository interface for database access.
        this.weatherClient = weatherClient;   // http client.
    }


    /**
     * Saves a city, fetching its weather from OpenWeather.
     *
     * @param city City with name.
     * @return City with weather info saved.
     */
    public City saveCity(City city){
        WeatherClient.WeatherInfo weatherInfo = weatherClient.getWeatherForCity(city.getName());
        city.setWeatherDescription(weatherInfo.getDescription());
        city.setTemperature(weatherInfo.getTemperature());
         return cityRepository.insert(city);
    }

    /**
     * Retrieves all saved cities.
     */
    public List<City> getAllCities(){
        return cityRepository.findAll();
    }

    /**
     * Finds a city by ID.
     * Optional<City>: safe handling of “not found” cases.
     */
    public Optional<City> getCityById(String id) {
        return cityRepository.findById(id);
    }

    /**
     * Deletes a city by ID.
     */
    public void deleteCity(String id) {
        cityRepository.deleteById(id);
    }
}
