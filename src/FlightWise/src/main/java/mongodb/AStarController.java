package mongodb;


import io.github.cdimascio.dotenv.Dotenv;
import controller.model.RouteItem;
import controller.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories
public class AStarController implements CommandLineRunner{


    @Autowired
    ItemRepository routeItemRepo;

    public static void main(String[] args) {
        SpringApplication.run(AStarController.class, args);
    }

    public void run(String... args) {


        System.out.println("\n--------------GET ITEM BY ROUTE ID-----------------------------------\n");
        routeItemRepo.save(new RouteItem("WAD34", "DONE"));
        getRouteItemByRouteID("YAO895I", "DONE");

        System.out.println("\n-------------------THANK YOU---------------------------");

    }
    public void getRouteItemByRouteID(String routeID, String status) {
        System.out.println("Getting item by routeID: " + routeID);
        RouteItem item = routeItemRepo.findRouteItemByRouteID(routeID);
        item.setStatus(status);
        routeItemRepo.save(item);
    }

    Dotenv dotenv = Dotenv.load();
}

