package org.example;
import models.Graph.Graph;
import models.edge.CoordinateEdge;
import models.vertex.CoordinateVertex;
import org.neo4j.driver.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;


public class Main {
    public static void createCordinateEdgesAsync(ArrayList<CoordinateVertex> coordinates, Session session){
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

    public static void createFinalPathEdges(ArrayList<CoordinateVertex> coordinates, Session session){
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

    public static void createFinalPathVertexes(ArrayList<CoordinateVertex> coordinates, Session session){
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

    public static void createCoordinateNodesAsync(ArrayList<CoordinateVertex> coordinates, Session session) {
        String cypherQuery = "CREATE (c:Coordinate {index: $ind, latitude: $lat, longitude: $long, averageHeight: $avgHeight})";

        try (Transaction tx = session.beginTransaction()) {
            for (int i = 0; i < coordinates.size(); i++) {
                CoordinateVertex coordinateVertex = coordinates.get(i);
                Point2D position = coordinateVertex.getPosition();


                tx.run(cypherQuery, Map.of("ind", coordinateVertex.getIndex(), "lat", position.getX(), "long", position.getY(), "avgHeight", coordinateVertex.averageHeight));
            }

            tx.commit();
        }
    }

    public static void main(String[] args) {
        Driver driver = GraphDatabase.driver("neo4j+s://41f6b34f.databases.neo4j.io",
                AuthTokens.basic("neo4j","9Mk9OO68J2Xw1z-GaVY2XcPdIa-y4gwwcIqdKXdGYWE"));

        //region creating cordinate nodes
        ArrayList<Point2D> positionsArray = new ArrayList<>(Arrays.asList(
                new Point2D.Double(50.0, 30.0),
                new Point2D.Double(49.995, 30.0),
                new Point2D.Double(49.990, 30.0),
                new Point2D.Double(49.985, 30.0),
                new Point2D.Double(49.980, 30.0),
                new Point2D.Double(49.975, 30.0),
                new Point2D.Double(50.0, 29.995),
                new Point2D.Double(49.995, 29.995),
                new Point2D.Double(49.990, 29.995),
                new Point2D.Double(49.985, 29.995),
                new Point2D.Double(49.980, 29.995),
                new Point2D.Double(49.975, 29.990),
                new Point2D.Double(50.0, 29.990),
                new Point2D.Double(49.995, 29.990),
                new Point2D.Double(49.990, 29.990),
                new Point2D.Double(49.985, 29.990),
                new Point2D.Double(49.980, 29.990),
                new Point2D.Double(49.975, 29.990)
        ));
        //endregion

        Graph newGraph = new Graph();

        for (int i = 0; i < positionsArray.size(); i++){
            CoordinateVertex newCoordinateVertex = new CoordinateVertex(positionsArray.get(i), 100.0);
            newGraph.addVertex(newCoordinateVertex);
        }

        newGraph.addVertexEdgesByDistance(0.7);
        newGraph.ASearch(0, 17);

        ArrayList<CoordinateVertex> newList = newGraph.findPath(newGraph.getVertexes().get(17));

//        for (CoordinateVertex v: newList){
//            System.out.println(v.getIndex());
//        }

        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            createCoordinateNodesAsync(newGraph.getVertexes(), session);

            createCordinateEdgesAsync(newGraph.getVertexes(), session);

            createFinalPathEdges(newList, session);

            createFinalPathVertexes(newList, session);
        }


        driver.close();
    }
}