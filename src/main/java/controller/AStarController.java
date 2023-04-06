package controller;

import controller.mongodbModel.RouteItem;
import controller.mongodbRepository.ItemRepository;
import io.github.cdimascio.dotenv.Dotenv;
import models.vertex.CoordinateVertex;
import org.json.JSONArray;
import org.json.JSONObject;
import org.neo4j.driver.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import path.PathPlanner;
import utils.Points;
import utils.neo4j.Neo4JDatabaseHandler;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SpringBootApplication
@EnableMongoRepositories
@RestController
public class AStarController implements CommandLineRunner {
    Dotenv dotenv = Dotenv.load();

    @Autowired
    ItemRepository routeItemRepo; // Importing the mongodbRepository

    /**
     * This method set the status of a route on MongoDB to specified status
     *
     * @param routeID ID created at the moment that a requisition for a Route is called in the NodeJS Backend.
     * @param status the status of the route. Example: CREATING, DONE, ERROR etc.
     * */
    private void setStatusByRouteID(String routeID, String status) {
        RouteItem item = routeItemRepo.findRouteItemByRouteID(routeID);
        item.setStatus(status);
        routeItemRepo.save(item);
    }

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
     *             "exclusionPoints": [[latitude, longitude, raio]]
     *             "pathID": "ABC123A",
     *             "filePath": "./dted/Rio"
     *             }
     * @return ResponseEntity is an HTTP response
     */
    @PostMapping("executeAlg")
    public ResponseEntity<String> executeAlg(@RequestBody String data) throws Exception {
        JSONObject obj = new JSONObject(URLDecoder.decode(data, StandardCharsets.UTF_8));
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

        try {
            Driver driver = GraphDatabase.driver(neo4jURI, AuthTokens.basic(neo4jUsername, neo4jPassword));

            // Read the dt2 file and take the positions of the region
            Points points = new Points();
            double[][][] coordinates = points.Coordinates(filePath, 0.0011, 0.0014);

            double[] startPoint = new double[]{lonInitial, latInitial};
            double[] endPoint = new double[]{lonFinal, latFinal};
            PathPlanner pathPlanner = new PathPlanner(pathID, coordinates, startPoint, endPoint, exclusionPoints);
            ArrayList<CoordinateVertex> route = pathPlanner.traceRoute();

            // Send the local Graph structure to neo4J
            try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
                new Neo4JDatabaseHandler().createRouteNode(route, session, pathPlanner.getRouteID());
            }
            driver.close();
            setStatusByRouteID(pathID, "DONE");

            return ResponseEntity.ok("Rota criada com sucesso!");
        } catch (Exception e) {
            setStatusByRouteID(pathID, "ERROR");
            return new ResponseEntity<String>("Erro ao criar rota!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * This route can be tested by accessing: http://localhost:3000/getMapBounds.
     * It returns the maximum and minimum points of a DTED file.
     *
     * @param data Information provided when the API is called:
     *             {
     *               "filePath": "./dted/Rio"
     *             }
     * @return A ResponseEntity, that is a JSON with the minimum and maximum geographic points
     */

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("getMapBounds")
    public ResponseEntity<String> getMapBoundsJson(@RequestBody String data) throws Exception {
        JSONObject obj = new JSONObject(URLDecoder.decode(data, StandardCharsets.UTF_8));
        String filePath = obj.getString("filePath");

        try {
        Points points = new Points();
        double[][] mapBounds = points.mapBounds(filePath);
        JSONObject mapBoundsJson = new JSONObject();
        mapBoundsJson.put("minLat", mapBounds[0][0]);
        mapBoundsJson.put("maxLat", mapBounds[0][1]);
        mapBoundsJson.put("minLon", mapBounds[1][0]);
        mapBoundsJson.put("maxLon", mapBounds[1][1]);

        return ResponseEntity.ok(mapBoundsJson.toString());
        } catch (Exception e) {
            return new ResponseEntity<String>("Erro ao obter os limites do mapa!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This route can be tested by accessing: http://localhost:3000/getMapBounds.
     * It returns the maximum and minimum points of all the DTED file.
     *
     * @param data Information provided when the API is called:
     *             {
     *               "filePath": "./dted/Rio"
     *             }
     * @return A ResponseEntity, that is a JSON with the minimum and maximum of all DT2 files.
     */

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("listAllBounds")
    public ResponseEntity<String> listAllBounds(@RequestBody String data) throws Exception {
        // Decoding the characters to UTF-8
        String dataDecoded = URLDecoder.decode(data, StandardCharsets.UTF_8);

        // Parsing the decoded string to a JSON object and extracting the values
        JSONObject obj = new JSONObject(dataDecoded);

        String filePath = obj.getString("filePath");
        Points points = new Points();

        try {
            double[][][] bounds = points.listAllBounds(filePath);

            JSONObject mapBoundsJson = new JSONObject();
            mapBoundsJson.put("bounds", bounds);

            return ResponseEntity.ok(mapBoundsJson.toString());
        } catch (Exception e) {
            return new ResponseEntity<String>("Erro ao obter os limites do mapa!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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