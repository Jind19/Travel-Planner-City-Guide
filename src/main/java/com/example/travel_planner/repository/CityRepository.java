package com.example.travel_planner.repository;

import com.example.travel_planner.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing City documents in MongoDB.
 */
public interface CityRepository extends MongoRepository<City, String> {
}
