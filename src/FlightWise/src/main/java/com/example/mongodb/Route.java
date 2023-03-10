package com.example.mongodb;

import org.springframework.data.annotation.Id;

public class Route {
    @Id
    private String id;

    private String routeID;
    private String status;

    public Route (String routeID, String status) {
        this.routeID = routeID;
        this.status = status;
    }
}
