package utils.neo4j;

import models.vertex.CoordinateVertex;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;

import java.util.ArrayList;
import java.util.Map;

public class Neo4JDatabaseHandler {

    public Neo4JDatabaseHandler() {}

    public void createRouteNode(ArrayList<CoordinateVertex> coordinates, Session session, String pathID) {
        String cypherQuery = "CREATE (r:Route {pathID: $path, coordinates: $coord})";

        String coordinatesJson = "[";

        for (int i = 0; i < coordinates.size(); i++){
            coordinatesJson += coordinates.get(i).toString();
            coordinatesJson += ",";
        }

        coordinatesJson += "]";

        try (Transaction tx = session.beginTransaction()) {
            tx.run(cypherQuery, Map.of("path", pathID, "coord", coordinatesJson));

            tx.commit();
        }
    }

}
