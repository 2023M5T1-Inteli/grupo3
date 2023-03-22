package controller.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
