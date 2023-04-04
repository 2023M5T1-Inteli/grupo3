package utils.neo4j;

import models.vertex.CoordinateVertex;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;

public class Neo4JDatabaseHandler {

    public Neo4JDatabaseHandler() {}

    // Inserts into the Neo4J database, the final optimal path as a new connection type
    public void createFinalPathEdges(ArrayList<CoordinateVertex> coordinates, Session session){
        String cypherQuery = "MATCH (n:NewCoordinate {index: $ind1})\n" +
                "MATCH (c:NewCoordinate {index: $ind2})\n" +
                "CREATE (n)-[r:Route]->(c)";
        try (Transaction tx = session.beginTransaction()) {
            for (int i = 0; i < coordinates.size() - 1; i++) {
                CoordinateVertex coordinateVertex = coordinates.get(i);
                CoordinateVertex nextVertex = coordinates.get(i+1);
                tx.run(cypherQuery, Map.of("ind1", coordinateVertex.getIndex(), "ind2", nextVertex.getIndex()));
            }
            tx.commit();
        }
    }

    // Updates the Neo4J database, changing the final path vertexes type
    public void createFinalPathVertexes(ArrayList<CoordinateVertex> coordinates, Session session, String pathID){
        String cypherQuery = "CREATE (c:NewCoordinate {index: $ind, lastNode: $last, latitude: $lat, longitude: $long, averageHeight: $avgHeight, pathID: $path})";

        try (Transaction tx = session.beginTransaction()) {
            for (int i = 0; i < coordinates.size(); i++) {
                CoordinateVertex coordinateVertex = coordinates.get(i);
                Point2D position = coordinateVertex.getPosition();

                int lastNodeIndex = -1;

                if (coordinateVertex.previousVertex != null){
                    lastNodeIndex = coordinateVertex.previousVertex.getIndex();
                }


                int previousVertexIndex = -1;
                if (coordinateVertex.previousVertex != null) {
                    previousVertexIndex = coordinateVertex.previousVertex.getIndex();
                }
                tx.run(cypherQuery, Map.of("ind", coordinateVertex.getIndex(), "last", previousVertexIndex, "lat", position.getX(), "long", position.getY(), "avgHeight", coordinateVertex.averageHeight, "path", pathID));


            }

            tx.commit();
        }
    }

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
