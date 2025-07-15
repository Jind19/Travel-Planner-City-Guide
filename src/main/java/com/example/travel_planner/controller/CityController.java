package com.example.travel_planner.controller;

import com.example.travel_planner.model.City;
import com.example.travel_planner.service.CityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing city planner endpoints.
 */
@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Add a new city to the planner.
     *
     * @param city City object (validated)
     * @return Saved City with weather info
     */
    @PostMapping
    public City addCity(@Valid @RequestBody City city) {
        return cityService.saveCity(city);
    }


    /**
     * List all saved cities.
     */
    @GetMapping
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    /**
     * Get city by ID.
     * Fetch a city from the database by its ID.
     * Path: /api/cities/{id}
     * Return:
     *      200 OK with the city JSON if found.
     *      404 Not Found if no city exists with that ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable String id){
        Optional<City> city = cityService.getCityById(id);
        if (city.isPresent()){
            return ResponseEntity.ok(city.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete city by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable String id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }





}
