package controller.mongodbModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The collection has three fields:
 *      id: an auto-generated identifier assigned by MongoDB.
 *      routeID: a unique hash identifier that we can use to track and retrieve a specific route.
 *      status: a status field that can take on two values - "CREATING" or "DONE".
 *
 * This collection is used in our application to store information about routes that are being created or
 * have already been completed.
 *
 * The id field serves as the primary key for the collection and is automatically generated by MongoDB.
 * The routeID field is a custom hash value that we generate and use to identify individual routes in our system.
 * The status field indicates the current state of the route, which can be either "CREATING" or "DONE".
 * */
@Document("routes")
public class RouteItem {
    @Id
    private String id;
    private String routeID;
    private String status;

    public RouteItem(String routeID, String status) {
        super();
        this.routeID = routeID;
        this.status = status;
    }

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
