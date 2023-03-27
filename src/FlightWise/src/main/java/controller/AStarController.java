package controller;

import controller.mongodbModel.RouteItem;
import io.github.cdimascio.dotenv.Dotenv;
import models.Graph.Graph;
import models.vertex.CoordinateVertex;
import models.Algorithms.AStar;
import org.json.JSONObject;
import org.neo4j.driver.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utils.ExclusionStopPoints.PointAnalyzer;
import utils.Points;
import utils.getIndex.GetIndexMethodClass;

import java.awt.geom.Point2D;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.neo4j.driver.Session;
import utils.neo4j.Neo4JDatabaseHandler;

import controller.mongodbRepository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import utils.popArray.PopulateArray;

/**

 This class acts as our server and executes the route for the A* algorithm. Currently, it is responsible for handling
 these tasks, but we plan to refactor our application using Spring MVC in Sprint 5.

 During Sprint 4, we attempted to implement SpringMongoDB but encountered errors that prevented us from using it. As a result,
 we had to resort to using this implementation instead.

 While we understand that it is ideal to have the MongoDB connection as a separate service, we are unable to do so due to the
 aforementioned errors. We hope to address this in future sprints.

 */
@SpringBootApplication
@EnableMongoRepositories
@RestController
public class AStarController implements CommandLineRunner{

    PointAnalyzer pointAnalyzer = new PointAnalyzer();
    PopulateArray popArray = new PopulateArray();

    Dotenv dotenv = Dotenv.load();

    @Autowired
    ItemRepository routeItemRepo; // Importing the mongodbRepository

    public static void main(String[] args) {
        SpringApplication.run(AStarController.class, args);
    }

    /**
     * You can test this route accessing: http://localhost:3000/executeAlg
     * @param data information provided at the moment that the API are called:
    {
    "lonInitial": -43.7082,
    "latInitial":-22.0780,
    "lonFinal":-43.2056,
    "latFinal":-22.381300000000004,
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
        String exclusionPointsStr = obj.getString("exclusionPoints");

        double lonInitial = obj.getDouble("lonInitial");
        double latInitial = obj.getDouble("latInitial");


        double lonFinal = obj.getDouble("lonFinal");
        double latFinal = obj.getDouble("latFinal");

        String pathID = obj.getString("pathID");

        String neo4jURI = dotenv.get("NEO4J_URI");
        String neo4jUsername = dotenv.get("NEO4J_USERNAME");
        String neo4jPassword = dotenv.get("NEO4J_PASSWORD");

        Driver driver = GraphDatabase.driver(neo4jURI,
                AuthTokens.basic(neo4jUsername,neo4jPassword));

        double[][] exclusionPoints = popArray.populateArray(exclusionPointsStr);

        // Reading the dt2 file and taking the positions of the region
        Points points = new Points();
        double[][] coordinates = points.Coordinates(filePath, lonInitial, latInitial,lonFinal, latFinal, 0.0011, 0.0014);

        // Initializes a new Graph()
        Graph newGraph = new Graph();


        // Adds all positions to the new Graph
        for (int i = 0; i < coordinates.length; i++){
            Point2D currentPoint = new Point2D.Double(coordinates[i][1],  coordinates[i][0]);

            for (int j = 0; j < exclusionPoints.length; i++) {
                Point2D exludedPoint = new Point2D.Double(exclusionPoints[j][0], exclusionPoints[j][1]);
                if(!pointAnalyzer.isExclusionPoint(currentPoint, exludedPoint, exclusionPoints[j][2])) {
                    CoordinateVertex newCoordinateVertex = new CoordinateVertex(currentPoint, coordinates[i][2]);
                    newGraph.addVertex(newCoordinateVertex);
                }
            }
        }

        // Create all vertex edges based on distance
        newGraph.addVertexEdgesByDistance(0.200);

        // Taking the index of the target position
        GetIndexMethodClass getIndex = new GetIndexMethodClass(newGraph, lonFinal, latInitial);

        // Calculates the optimal path between two nodes(vertex)
        ArrayList<CoordinateVertex> vertices = newGraph.getVertexes();
        AStar algorithm = new AStar();
        ArrayList <CoordinateVertex> newList = algorithm.ASearch(0, coordinates.length - 1, vertices);

        // Returns the generated optimal path as an ArrayList;
        

        System.out.println(newList.get(0).getIndex());
        System.out.println(newList.get(1).previousVertex.getIndex());


        Neo4JDatabaseHandler neo4JDatabaseHandler = new Neo4JDatabaseHandler();

        // Send the local Graph structure to neo4J
        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            neo4JDatabaseHandler.createFinalPathVertexes(newList, session, pathID);

            neo4JDatabaseHandler.createFinalPathEdges(newList, session);
        }

        // Ends the Neo4J session
        driver.close();

        getRouteItemByRouteID(pathID, "DONE");

        // Returns this message after complete
        return ResponseEntity.ok("Rota criada com sucesso!");
    }
    public void getRouteItemByRouteID(String routeID, String status) {
        System.out.println("Getting item by routeID: " + routeID);
        RouteItem item = routeItemRepo.findRouteItemByRouteID(routeID);
        item.setStatus(status);
        routeItemRepo.save(item);
    }

    /**
     * This function does not execute any functionality on its own, but it is essential for our implementation as it utilizes the
     * CommandLineRunner interface. The CommandLineRunner interface is required for establishing a connection to MongoDB and
     * running any initialization code necessary for the application.
     * Therefore, this method serves as an entry point to initialize our application and allow us to utilize MongoDB.
     * */
    @Override
    public void run(String... args) throws Exception {

    }
}