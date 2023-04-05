package controller;

import controller.mongodbModel.RouteItem;
import controller.mongodbRepository.ItemRepository;
import io.github.cdimascio.dotenv.Dotenv;
import models.vertex.CoordinateVertex;
import org.json.JSONArray;
import org.json.JSONObject;
import org.neo4j.driver.AuthTokens;

import org.neo4j.driver.*;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import path.PathPlanner;
import utils.ExclusionStopPoints.PointAnalyzer;
import utils.Points;
import utils.neo4j.Neo4JDatabaseHandler;
import utils.popArray.PopulateArray;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * This class acts as our server and executes the route for the A* algorithm. Currently, it is responsible for handling
 * these tasks, but we plan to refactor our application using Spring MVC in Sprint 5.
 * <p>
 * During Sprint 4, we attempted to implement SpringMongoDB but encountered errors that prevented us from using it. As a result,
 * we had to resort to using this implementation instead.
 * <p>
 * While we understand that it is ideal to have the MongoDB connection as a separate service, we are unable to do so due to the
 * aforementioned errors. We hope to address this in future sprints.
 */
@SpringBootApplication
@EnableMongoRepositories
@RestController
public class AStarController implements CommandLineRunner {

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
     *
     * @param data information provided at the moment that the API are called:
     *             {
     *             "lonInitial": -43.7082,
     *             "latInitial":-22.0780,
     *             "lonFinal":-43.2056,
     *             "latFinal":-22.381300000000004,
     *             "pathID": "ABC123A",
     *             "filePath": "./dted/Rio"
     *             }
     * @return ResponseEntity is an HTTP response
     */

    @PostMapping("executeAlg")
    public ResponseEntity<String> executeAlg(@RequestBody String data) throws Exception {
        // Decoding the characters to UTF-8
        String dataDecoded = URLDecoder.decode(data, StandardCharsets.UTF_8);

        // Parsing the decoded string to a JSON object and extracting the values
        JSONObject obj = new JSONObject(dataDecoded);
        JSONArray exclusionPoints = obj.getJSONArray("exclusionPoints");

        String filePath = obj.getString("filePath");
        double lonInitial = obj.getDouble("lonInitial");
        double latInitial = obj.getDouble("latInitial");
        double lonFinal = obj.getDouble("lonFinal");
        double latFinal = obj.getDouble("latFinal");
        String pathID = obj.getString("pathID");

        String neo4jURI = dotenv.get("NEO4J_URI");
        String neo4jUsername = dotenv.get("NEO4J_USERNAME");
        String neo4jPassword = dotenv.get("NEO4J_PASSWORD");

        Driver driver = GraphDatabase.driver(neo4jURI, AuthTokens.basic(neo4jUsername, neo4jPassword));


        // Reading the dt2 file and taking the positions of the region
        Points points = new Points();
//        double startTime = System.nanoTime();
//        double endTime = (System.nanoTime() - startTime) / 1_000_000_000;

        System.out.println("Reading File...");


        double[][][] coordinates = points.Coordinates(filePath, -43.993166, -22, -42.03, -23.98, 0.0011, 0.0014, exclusionPoints);

        double[] startPoint = new double[]{lonInitial, latInitial};
        double[] endPoint = new double[]{lonFinal, latFinal};
            PathPlanner pathPlanner = new PathPlanner(pathID, coordinates, startPoint, endPoint);
        ArrayList<CoordinateVertex> route = pathPlanner.traceRoute();

        System.out.println(pathPlanner);


        // Returns the generated optimal path as an ArrayList;
        Neo4JDatabaseHandler neo4JDatabaseHandler = new Neo4JDatabaseHandler();

        // Send the local Graph structure to neo4J
        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            neo4JDatabaseHandler.createRouteNode(route, session, pathPlanner.getRouteID());
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

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("getMapBounds")
    public ResponseEntity<String> getMapBoundsJson(@RequestBody String data) throws Exception {
        String dataDecoded = URLDecoder.decode(data, StandardCharsets.UTF_8);
        JSONObject obj = new JSONObject(dataDecoded);
        String filePath = obj.getString("filePath");
        Points points = new Points();
        double[][] mapBounds = points.mapBounds(filePath);
        double minLat = mapBounds[0][0];
        double maxLat = mapBounds[0][1];
        double minLon = mapBounds[1][0];
        double maxLon = mapBounds[1][1];
        JSONObject mapBoundsJson = new JSONObject();
        mapBoundsJson.put("minLat", minLat);
        mapBoundsJson.put("maxLat", maxLat);
        mapBoundsJson.put("minLon", minLon);
        mapBoundsJson.put("maxLon", maxLon);
        return ResponseEntity.ok(mapBoundsJson.toString());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("listAllBounds")
    public ResponseEntity<String> listAllBounds(@RequestBody String data) throws Exception {
        // Decoding the characters to UTF-8
        String dataDecoded = URLDecoder.decode(data, StandardCharsets.UTF_8);

        // Parsing the decoded string to a JSON object and extracting the values
        JSONObject obj = new JSONObject(dataDecoded);

        String filePath = obj.getString("filePath");
        Points points = new Points();

        double[][][] bounds = points.listAllBounds(filePath);

        JSONObject mapBoundsJson = new JSONObject();
        mapBoundsJson.put("bounds", bounds);

        return ResponseEntity.ok(mapBoundsJson.toString());
    }
    /**
     * This function does not execute any functionality on its own, but it is essential for our implementation as it utilizes the
     * CommandLineRunner interface. The CommandLineRunner interface is required for establishing a connection to MongoDB and
     * running any initialization code necessary for the application.
     * Therefore, this method serves as an entry point to initialize our application and allow us to utilize MongoDB.
     */
    @Override
    public void run(String... args) throws Exception {

    }
}