package org.example;
import models.Graph.Graph;
import models.edge.CoordinateEdge;
import models.vertex.CoordinateVertex;
import org.neo4j.driver.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;

import java.util.concurrent.TimeUnit;


public class Main {
    public static void createCordinateEdgesAsync(ArrayList<CoordinateVertex> coordinates, Session session){
        String cypherQuery = "MATCH (n:Coordinate {index: $ind1})\n" +
                "MATCH (c:Coordinate {index: $ind2})\n" +
                "CREATE (n)-[r:Connection {distance: $dist, heighRange: $heig}]->(c)";
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < coordinates.size(); i++) {
            CoordinateVertex coordinateVertex = coordinates.get(i);

            for (int j = 0; j < coordinateVertex.getEdges().size(); j++){
                CoordinateEdge coordinateEdge = coordinateVertex.getEdges().get(j);

                Thread thread = new Thread(() -> {
                    try (Transaction tx = session.beginTransaction()) {
                        tx.run(cypherQuery, Map.of("ind1", coordinateEdge.fromVertexIndex, "ind2", coordinateEdge.toVertexIndex, "dist", coordinateEdge.distance, "heig", coordinateEdge.heightRange));
                        tx.commit();
                    }
                });

                threads.add(thread);
                thread.start();
            }
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // handle exception
            }
        }
    }

    public static void createCoordinateNodesAsync(ArrayList<CoordinateVertex> coordinates, Session session) {
        String cypherQuery = "CREATE (c:Coordinate {index: $ind, latitude: $lat, longitude: $long, averageHeight: $avgHeight})";
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < coordinates.size(); i++) {
            CoordinateVertex coordinateVertex = coordinates.get(i);
            Point2D position = coordinateVertex.getPosition();

            Thread thread = new Thread(() -> {
                try (Transaction tx = session.beginTransaction()) {
                    tx.run(cypherQuery, Map.of("ind", coordinateVertex.getIndex(), "lat", position.getX(), "long", position.getY(), "avgHeight", coordinateVertex.averageHeight));
                    tx.commit();
                }
            });

            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // handle exception
            }
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
                new Point2D.Double(49.975, 29.995)
        ));
        //endregion

        Graph newGraph = new Graph();

        for (int i = 0; i < positionsArray.size(); i++){
            CoordinateVertex newCoordinateVertex = new CoordinateVertex(positionsArray.get(i), 100.0);
            newGraph.addVertex(newCoordinateVertex);
        }

        newGraph.addVertexEdges();

        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            createCoordinateNodesAsync(newGraph.getVertexes(), session);

            Thread.sleep(12500);

            createCordinateEdgesAsync(newGraph.getVertexes(), session);
        }
        catch (InterruptedException e) {
            // tratamento de exceção, se necessário
        }


        driver.close();
    }
}