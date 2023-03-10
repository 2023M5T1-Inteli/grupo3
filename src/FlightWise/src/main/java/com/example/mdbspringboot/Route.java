package com.example.mdbspringboot;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("routes")
public class Route {
    @Id
    private String id;

    private String routeID;
    private String status;

    public Route(String id, String routeID, String status) {
        super();
        this.id = id;
        this.routeID = routeID;
        this.status = status;
    }
}
