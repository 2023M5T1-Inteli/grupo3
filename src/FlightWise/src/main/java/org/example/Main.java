package org.example;
import models.Graph.Graph;
import models.edge.CoordinateEdge;
import models.vertex.CoordinateVertex;
import org.jetbrains.annotations.NotNull;
import org.neo4j.driver.*;

import org.json.*;

import java.awt.geom.Point2D;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utils.Points;


import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // Inserts into the Neo4J database, all coordinate connections
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

    // Inserts into the Neo4J database, the final optimal path as a new connection type
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

    // Updates the Neo4J database, changing the final path vertexes type
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

    // Inserts into the Neo4J database all the coordinate vertexes
    public static void createCoordinateNodesAsync(ArrayList<CoordinateVertex> coordinates, Session session, String pathID) {
        String cypherQuery = "CREATE (c:Coordinate {index: $ind, latitude: $lat, longitude: $long, averageHeight: $avgHeight, pathID: $path})";

        try (Transaction tx = session.beginTransaction()) {
            for (int i = 0; i < coordinates.size(); i++) {
                CoordinateVertex coordinateVertex = coordinates.get(i);
                Point2D position = coordinateVertex.getPosition();


                tx.run(cypherQuery, Map.of("ind", coordinateVertex.getIndex(), "lat", position.getX(), "long", position.getY(), "avgHeight", coordinateVertex.averageHeight, "path", pathID));
            }

            tx.commit();
        }
    }

    public int getVertexIndex(@NotNull Graph g, double lon, double lat) {
        ArrayList<CoordinateVertex> vertices = g.getVertexes();
        int index = -1;
        for (CoordinateVertex vertex: vertices ) {
            Point2D location = vertex.getPosition();
            if (location.getX() == lon && location.getY() == lat) {
                index = vertex.getIndex();
            }
        }

        return index;
    }

    @PostMapping("executeAlg")
    public String executeAlg(@RequestBody String data) throws JSONException {
        String dataDecoded = URLDecoder.decode(data, StandardCharsets.UTF_8);

        JSONObject obj = new JSONObject(dataDecoded);
        Double lonInitial = obj.getDouble("lonInitial");
        Double latInitial = obj.getDouble("latInitial");

        Double lonFinal = obj.getDouble("lonFinal");
        Double latFinal = obj.getDouble("latFinal");

        String pathID = obj.getString("pathID");

        Driver driver = GraphDatabase.driver("neo4j+s://ea367293.databases.neo4j.io",
                AuthTokens.basic("neo4j","74OQ-dnMkAEveBhfOKbD1BBTTnvQ4ubORS86TwvT8mo"));

        //region creating cordinate nodes
        Points points = new Points();
        double[][] coordinates = points.Coordinates("dted/rio", lonInitial, latInitial, 5, 4, 0.0013, 0.0011);

        // Initializes a new Graph()
        Graph newGraph = new Graph();


        // Adds all positions to the new Graph
        for (int i = 0; i < coordinates.length; i++){
            Point2D currentPoint = new Point2D.Double(coordinates[i][0],  coordinates[i][1]);
            CoordinateVertex newCoordinateVertex = new CoordinateVertex(currentPoint, coordinates[i][2]);
            newGraph.addVertex(newCoordinateVertex);
        }


        // Create all vertex edges based on distance
        newGraph.addVertexEdgesByDistance(0.200);

        int targetIndex = getVertexIndex(newGraph, lonFinal, latFinal);

        // Calculates the optimal path between two nodes(vertex)
        newGraph.ASearch(0, targetIndex);

        // Returns the generated optimal path as an ArrayList;
        ArrayList<CoordinateVertex> newList = newGraph.findPath(newGraph.getVertexes().get(targetIndex));

        // Send the local Graph structure to neo4J
        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            createCoordinateNodesAsync(newGraph.getVertexes(), session, pathID);

            createCordinateEdgesAsync(newGraph.getVertexes(), session);

            createFinalPathEdges(newList, session);

            createFinalPathVertexes(newList, session);
        }

        // Ends the Neo4J session
        driver.close();

        return "Foi";
    }
}