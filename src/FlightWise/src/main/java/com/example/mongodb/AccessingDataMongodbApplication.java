package com.example.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccessingDataMongodbApplication implements CommandLineRunner{
    @Autowired
    RouteRepository route;

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataMongodbApplication.class, args);
    }

    public AccessingDataMongodbApplication() {}
    public void searchARoute(String routeID) throws Exception {
        System.out.println(route.findByRouteID(routeID));
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
