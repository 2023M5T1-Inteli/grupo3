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

import org.neo4j.driver.Session;
import utils.neo4j.Neo4JDatabaseHandler;


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

    /**
     * You can test this route accessing: http://localhost:3000/executeAlg
     * @param data information provided at the moment that the API are called:
    {
    "lonInitial": -43.4082,
    "latInitial":-22.1780,
    "lonFinal":-43.4056,
    "latFinal":-22.181300000000004,
    "pathID": "ABC123A",
    "filePath": "./dted/Rio"
    }
     * @return ResponseEntity is an HTTP response
     * */
    @PostMapping("executeAlg")
    public ResponseEntity<String> executeAlg(@RequestBody String data) throws Exception {
        // Decoding the characters to UTF-8
        String dataDecoded = URLDecoder.decode(data, StandardCharsets.UTF_8);

        // Parsing the decoded string to a JSON object and extracting the values
        JSONObject obj = new JSONObject(dataDecoded);

        String filePath = obj.getString("filePath");

        Double lonInitial = obj.getDouble("lonInitial");
        Double latInitial = obj.getDouble("latInitial");


        Double lonFinal = obj.getDouble("lonFinal");
        Double latFinal = obj.getDouble("latFinal");

        String pathID = obj.getString("pathID");

        String neo4jURI = dotenv.get("NEO4J_URI");
        String neo4jUsername = dotenv.get("NEO4J_USERNAME");
        String neo4jPassword = dotenv.get("NEO4J_PASSWORD");

        Driver driver = GraphDatabase.driver(neo4jURI,
                AuthTokens.basic(neo4jUsername,neo4jPassword));

        // Reading the dt2 file and taking the positions of the region
        Points points = new Points();
        double[][] coordinates = points.Coordinates(filePath, lonInitial, latInitial,lonFinal, latFinal, 0.0011, 0.0014);

        // Initializes a new Graph()
        Graph newGraph = new Graph();


        // Adds all positions to the new Graph
        for (int i = 0; i < coordinates.length; i++){
            Point2D currentPoint = new Point2D.Double(coordinates[i][1],  coordinates[i][0]);
            CoordinateVertex newCoordinateVertex = new CoordinateVertex(currentPoint, coordinates[i][2]);
            newGraph.addVertex(newCoordinateVertex);
        }

        // Create all vertex edges based on distance
        newGraph.addVertexEdgesByDistance(0.200);

        // Taking the index of the target position
        GetIndexMethodClass getIndex = new GetIndexMethodClass(newGraph, lonFinal, latInitial);

        // Calculates the optimal path between two nodes(vertex)
        newGraph.ASearch(0, coordinates.length - 1);

        // Returns the generated optimal path as an ArrayList;
        ArrayList<CoordinateVertex> newList = newGraph.findPath(newGraph.getVertexes().get(coordinates.length - 1));


        Neo4JDatabaseHandler neo4JDatabaseHandler = new Neo4JDatabaseHandler();

        // Send the local Graph structure to neo4J
        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            neo4JDatabaseHandler.createCoordinateNodesAsync(newGraph.getVertexes(), session, pathID);

            neo4JDatabaseHandler.createCordinateEdgesAsync(newGraph.getVertexes(), session);

            neo4JDatabaseHandler.createFinalPathEdges(newList, session);

            neo4JDatabaseHandler.createFinalPathVertexes(newList, session);
        }

        // Ends the Neo4J session
        driver.close();

        // Returns this message after complete
        return ResponseEntity.ok("Rota criada com sucesso!");
    }
}