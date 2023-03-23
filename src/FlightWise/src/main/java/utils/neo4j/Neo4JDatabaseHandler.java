package utils.neo4j;

import models.edge.CoordinateEdge;
import models.vertex.CoordinateVertex;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;

public class Neo4JDatabaseHandler {

    public Neo4JDatabaseHandler() {}
    // Inserts into the Neo4J database, all coordinate connections
    public void createCordinateEdgesAsync(ArrayList<CoordinateVertex> coordinates, Session session){
        String cypherQuery = "MATCH (n:Coordinate {index: $ind1})\n" +
                "MATCH (c:Coordinate {index: $ind2})\n" +
                "CREATE (n)-[r:Connection {distance: $dist, heighRange: $heig}]->(c)";

        for (int i = 0; i < coordinates.size(); i++) {
            CoordinateVertex coordinateVertex = coordinates.get(i);
            try (Transaction tx = session.beginTransaction()) {
                for (int j = 0; j < coordinateVertex.getEdges().size(); j++){
                    CoordinateEdge coordinateEdge = coordinateVertex.getEdges().get(j);
                    tx.run(cypherQuery, Map.of("ind1", coordinateVertex.getIndex(), "ind2", coordinateEdge.targetVertex.getIndex(), "dist", coordinateEdge.distance, "heig", coordinateEdge.heightRange));
                }
                tx.commit();
            }
        }
    }

    // Inserts into the Neo4J database, the final optimal path as a new connection type
    public void createFinalPathEdges(ArrayList<CoordinateVertex> coordinates, Session session){
        String cypherQuery = "MATCH (n:Coordinate {index: $ind1})\n" +
                "MATCH (c:Coordinate {index: $ind2})\n" +
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

    // Inserts into the Neo4J database all the coordinate vertexes
    public void createCoordinateNodesAsync(ArrayList<CoordinateVertex> coordinates, Session session, String pathID) {
        String cypherQuery = "CREATE (c:Coordinate {index: $ind, latitude: $lat, longitude: $long, averageHeight: $avgHeight, pathID: $path})";

        try (Transaction tx = session.beginTransaction()) {
            for (int i = 0; i < coordinates.size(); i++) {
                CoordinateVertex coordinateVertex = coordinates.get(i);
                Point2D position = coordinateVertex.getPosition();

                int lastNodeIndex = -1;

                if (coordinateVertex.previousVertex != null){
                    lastNodeIndex = coordinateVertex.previousVertex.getIndex();
                }


                tx.run(cypherQuery, Map.of("ind", coordinateVertex.getIndex(), "lat", position.getX(), "long", position.getY(), "avgHeight", coordinateVertex.averageHeight, "path", pathID));
            }

            tx.commit();
        }
    }

    // Updates the Neo4J database, changing the final path vertexes type
    public void createFinalPathVertexes(ArrayList<CoordinateVertex> coordinates, Session session){
        String cypherQuery = "MATCH (n:Coordinate {index: $ind})\n" +
                "REMOVE n:Coordinate\n" +
                "SET n:NewCoodinate";
        try (Transaction tx = session.beginTransaction()) {
            for (int i = 0; i < coordinates.size() - 1; i++) {
                CoordinateVertex coordinateVertex = coordinates.get(i);
                tx.run(cypherQuery, Map.of("ind", coordinateVertex.getIndex()));
            }
            tx.commit();
        }
    }
}
