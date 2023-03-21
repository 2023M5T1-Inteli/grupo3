package controller;

import io.github.cdimascio.dotenv.Dotenv;
import models.Graph.Graph;
import models.edge.CoordinateEdge;
import models.vertex.CoordinateVertex;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.neo4j.driver.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utils.Points;
import utils.getIndex.GetIndexMethodClass;

import java.awt.geom.Point2D;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;


/**
 * Currently this class is being responsible for acting as our server and
 * executing the route for the A* algorithm.
 */
@SpringBootApplication
@RestController
public class AStarController {
    // Now the main method is only providing the server in the localhost 3000
    Dotenv dotenv = Dotenv.load();
    public static void main(String[] args) {
        SpringApplication.run(AStarController.class, args);
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

                int lastNodeIndex = -1;

                if (coordinateVertex.previousVertex != null){
                    lastNodeIndex = coordinateVertex.previousVertex.getIndex();
                }


                tx.run(cypherQuery, Map.of("ind", coordinateVertex.getIndex(), "lat", position.getX(), "long", position.getY(), "avgHeight", coordinateVertex.averageHeight, "path", pathID));
            }

            tx.commit();
        }
    }


    /**
     * You can test this route accessing: http://localhost:3000/executeAlg
     * @param data information provided at the moment that the API are called:
     *    {
     *     “lonInitial”: Double,
     *     “latInitial”: Double,
     *     “lonFinal”: Double,
     *     “latFinal”: Double,
     *     “pathID”: String,
     *     “filePath”: String (are not used yet)
     *    }
     * @return ResponseEntity is an HTTP response
     * */
    @PostMapping("executeAlg")
    public ResponseEntity<String> executeAlg(@RequestBody String data) throws Exception {
        // Decoding the characters to UTF-8
        String dataDecoded = URLDecoder.decode(data, StandardCharsets.UTF_8);

        // Parsing the decoded string to a JSON object and extracting the values
        JSONObject obj = new JSONObject(dataDecoded);
        String filePath = obj.getString("filePath");

//        Double lonStep = obj.getDouble("lonStep");
//        Double latStep = obj.getDouble("latStep");
        Double lonInitial = obj.getDouble("lonInitial");
        Double latInitial = obj.getDouble("latInitial");


        Double lonFinal = obj.getDouble("lonFinal");
        Double latFinal = obj.getDouble("latFinal");

        String pathID = obj.getString("pathID");

        String neo4jURI = dotenv.get("NEO4J_URI");
        String neo4jUsername = dotenv.get("NEO4J_Username");
        String neo4jPassword = dotenv.get("NEO4J_PASSOWORD");

        Driver driver = GraphDatabase.driver(neo4jURI,
                AuthTokens.basic(neo4jUsername,neo4jPassword));
        
        // Reading the dt2 file and taking the positions of the region
        Points points = new Points();
        double[][] coordinates = points.Coordinates(filePath, lonInitial, latInitial, lonFinal, latFinal, 0.0014, 0.0011);

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

        // Taking the index of the target position
        GetIndexMethodClass getIndex = new GetIndexMethodClass(newGraph, lonFinal, latInitial);
        int targetIndex = getIndex.getVertexIndex();

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

        // Returns this message after complete
        return ResponseEntity.ok("Rota criada com sucesso!");
    }
}