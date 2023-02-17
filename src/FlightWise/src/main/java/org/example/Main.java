package org.example;
import models.vertex.CoordinateVertex;
import org.neo4j.driver.*;

import java.awt.geom.Point2D;
import java.util.Map;


public class Main {

    public static void createCoodinateNode(CoordinateVertex coordinateVertex, Session session) {
        
        String cypherQuery = "CREATE (p:Coordinate {latitude: $lat, longitude: $long, averageHeight: $avgHeight})";
        try (Transaction tx = session.beginTransaction()) {
            Point2D position = coordinateVertex.getPosition();
            tx.run(cypherQuery, Map.of("lat", position.getX(),"long", position.getY(), "avgHeight", coordinateVertex.averageHeight));
            tx.commit();
        }
    }

    public static void main(String[] args) {
        String personName = args[0];
        String personTitle = args[1];

        Driver driver = GraphDatabase.driver("neo4j+s://41f6b34f.databases.neo4j.io",
                AuthTokens.basic("neo4j","<password>"));

        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            Point2D position = new Point2D.Double(0.0, 0.0);
            CoordinateVertex newCoordinateVertex = new CoordinateVertex(position, 0.0);
            createCoodinateNode(newCoordinateVertex, session);

        }
        driver.close();
    }
}