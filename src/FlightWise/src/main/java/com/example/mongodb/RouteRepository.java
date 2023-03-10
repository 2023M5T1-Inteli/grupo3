package com.example.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RouteRepository  extends MongoRepository<Route, String> {

    public Route findByRouteID(String routeID);
    public List<Route> findByStatus(String status);
}
