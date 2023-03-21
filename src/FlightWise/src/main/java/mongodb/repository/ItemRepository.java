package mongodb.repository;

import java.util.List;

import mongodb.model.RouteItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ItemRepository extends MongoRepository<RouteItem, String> {

    @Query("{routeID:'?0'}")
    RouteItem findRouteItemByRouteID(String routeID);

    @Query(value="{status:'?0'}", fields="{'routeID' : 1}")
    List<RouteItem> findAll(String status);

    public long count();

}
