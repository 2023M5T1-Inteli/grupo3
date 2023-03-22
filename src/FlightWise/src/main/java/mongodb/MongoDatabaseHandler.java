package mongodb;

import mongodb.model.RouteItem;
import mongodb.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public class MongoDatabaseHandler  implements CommandLineRunner {
    private String routeID;
    public MongoDatabaseHandler(String routeID) {
        if (routeID == null) {
            throw new IllegalStateException();
        }

        this.routeID = routeID;
    }

    ItemRepository routeItemRepo;

    public void setStatusByRouteID() {
        System.out.println("Getting item by routeID: " + this.routeID);
        RouteItem item = routeItemRepo.findRouteItemByRouteID(this.routeID);
        item.setStatus("DONE");
        routeItemRepo.save(item);
    }
    @Override
    public void run(String... args) throws Exception {
        setStatusByRouteID();
    }
}
