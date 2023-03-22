package mongodb;


import java.util.ArrayList;
import java.util.List;

import mongodb.model.RouteItem;
import mongodb.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories
public class MdbSpringBootApplication implements CommandLineRunner{


    @Autowired
    ItemRepository routeItemRepo;

    public static void main(String[] args) {
        SpringApplication.run(MdbSpringBootApplication.class, args);
    }

    public void run(String... args) {


        System.out.println("\n--------------GET ITEM BY ROUTE ID-----------------------------------\n");
        routeItemRepo.save(new RouteItem("WAD34", "DONE"));
        getRouteItemByRouteID("YAO895I", "DONE");

        System.out.println("\n-------------------THANK YOU---------------------------");

    }

    // CRUD operations


    // 2. Get item by name
    public void getRouteItemByRouteID(String routeID, String status) {
        System.out.println("Getting item by routeID: " + routeID);
        RouteItem item = routeItemRepo.findRouteItemByRouteID(routeID);
        item.setStatus(status);
        routeItemRepo.save(item);
        System.out.println(getItemDetails(item));
    }

    public String getItemDetails(RouteItem item) {

        System.out.println(
                "Route ID: " + item.getRouteID() +
                        ", \nRoute STATUS: " + item.getStatus()
        );

        return "";
    }
}

