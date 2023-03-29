package controller.mongodbRepository;

import controller.mongodbModel.RouteItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * This interface follows the same structure as MongoRepositories, which provides a convenient set
 * of methods for interacting with MongoDB. For more information on MongoRepositories,
 * please refer to the documentation: "https://spring.io/guides/gs/accessing-data-mongodb/"
 * */
public interface ItemRepository extends MongoRepository<RouteItem, String> {

    @Query("{routeID:'?0'}")
    RouteItem findRouteItemByRouteID(String routeID);

    @Query(value="{status:'?0'}", fields="{'routeID' : 1}")
    List<RouteItem> findAll(String status);

    public long count();

}
