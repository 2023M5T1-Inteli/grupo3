package path;

import models.Algorithms.AStar;
import models.Graph.Graph;
import models.Scorer.Haversine;
import models.vertex.CoordinateVertex;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ExclusionStopPoints.PointAnalyzer;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class coordinates the creation of vertices, graph, and the application of logic for exclusion points.
 * All of these methods are separated into different classes, and PathPlanner serves as the pipeline for calling them.
 */

public class PathPlanner {

    private static final Logger logger = LoggerFactory.getLogger(PathPlanner.class);
    private final String routeID;
    private final HashMap<String, CoordinateVertex> originDest = new HashMap<String, CoordinateVertex>();
    private final double maxHeight;
    private Graph _graph;

    private double totalProcessTime = 0.0;

    private ArrayList<CoordinateVertex> foundRoute;

    private final JSONArray excludedPoints;

    /**
     * Executes the process of populate a Graph with CoordinateVertices
     * */
    public PathPlanner(String routeID, double[][][] coordinates, double[] startCoordinate, double[] targetCoordinate, JSONArray excludedPoints){
        this.routeID = routeID;
        this.excludedPoints = excludedPoints;

        double minStartDistance = Double.MAX_VALUE;
        double minEndDistance = Double.MAX_VALUE;

        Haversine scorer = new Haversine();

        double maxHeight = Double.NEGATIVE_INFINITY;
        this._graph = new Graph(coordinates.length, coordinates[0].length);

        long graphCreationTime = System.currentTimeMillis();
        logger.atInfo().log("Creating graph");

        Point2D startPoint = new Point2D.Double(startCoordinate[1], startCoordinate[0]);
        Point2D targetPoint = new Point2D.Double(targetCoordinate[1], targetCoordinate[0]);

        for (int i = 0; i < coordinates.length; i++) {
            for (int j = 0; j < coordinates[i].length; j++) {
                double height = coordinates[i][j][2];
                if (height > maxHeight) maxHeight = height;
                Point2D currentPoint = new Point2D.Double(coordinates[i][j][1], coordinates[i][j][0]);
                CoordinateVertex newCoordinateVertex = new CoordinateVertex(currentPoint, height);

                if(isExcludedPoint(newCoordinateVertex))
                    newCoordinateVertex.exclusionCost = 1000000; // The vertex cost are increased in 1000000 if the point are in the exclusion zone

                this._graph.addVertex(newCoordinateVertex, i, j);

                /**
                 * To maintain the indices of the starting point and the destination point,
                 * we use the logic below and store them in a HashMap
                 * */

                double currentStartDistance = Math.abs(scorer.computeDistanceByPoint2D(currentPoint, startPoint));
                if(currentStartDistance < minStartDistance) {
                    minStartDistance = currentStartDistance;
                    originDest.put("start", newCoordinateVertex);
                }

                double currentEndDistance = Math.abs(scorer.computeDistanceByPoint2D(currentPoint, targetPoint));
                if(currentEndDistance < minEndDistance) {
                    minEndDistance = currentEndDistance;
                    originDest.put("end", newCoordinateVertex);
                }
            }
        }
        logger.atInfo().log("Graph created");
        logger.atInfo().log("Graph creation time: " + (System.currentTimeMillis() - graphCreationTime) + "ms");
        logger.atInfo().log("Total Vertices: " + this._graph.totalVertices + "");
        this.totalProcessTime += System.currentTimeMillis() - graphCreationTime;

        this.maxHeight = maxHeight;

        long edgesCreationTime = System.currentTimeMillis();
        logger.atInfo().log("Creating edges");
        this._graph.createEdges();
        logger.atInfo().log("Edges created");
        logger.atInfo().log("Edges creation time: " + (System.currentTimeMillis() - edgesCreationTime) + "ms");
        this.totalProcessTime += System.currentTimeMillis() - edgesCreationTime;
    }

    /**
     * Given a JSONArray of exclusion points and a vertex, this function calls the <b>isInExclusionZone</b>
     * function to determine whether the point is in the exclusion zone.
     *
     * @param vertex The CoordinateVertex to verify.
     * @return True if the point is in the zone, false otherwise.
     */
    private boolean isExcludedPoint(CoordinateVertex vertex) {
        if (this.excludedPoints.length() != 0) {
            PointAnalyzer pointAnalyzer = new PointAnalyzer();

            for (int k = 0; k < this.excludedPoints.length(); k++) {
                JSONArray excludePoint = this.excludedPoints.getJSONArray(k);

                double excludeLong = excludePoint.getDouble(0);
                double excludeLat = excludePoint.getDouble(1);
                double radius = excludePoint.getDouble(2);

                boolean isInExclusionZone = pointAnalyzer.isExclusionPoint(vertex.getPosition(), new Point2D.Double(excludeLong, excludeLat), radius);

                if (isInExclusionZone) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * In this method, we obtain the vertices of the graph and call the AStar Algorithm to work with
     * the start and target point indices.
     * */
    public ArrayList<CoordinateVertex> traceRoute() {
        // Calculates the optimal path between two nodes(vertex)
        logger.atInfo().log("Calculating route");
        long routeCalculationTime = System.currentTimeMillis();
        ArrayList<CoordinateVertex> vertices = this._graph.getVertexes();

        logger.atInfo().log("OriginVerticePosition: " + vertices.get(originDest.get("start").getIndex()).getPosition() + " " + originDest.get("start").getIndex());
        logger.atInfo().log("DestVerticePosition: " + vertices.get(originDest.get("end").getIndex()).getPosition() + " " + originDest.get("end").getIndex());

        AStar algorithm = new AStar();
        this.foundRoute = algorithm.ASearch(originDest.get("start").getIndex(), originDest.get("end").getIndex(), vertices, this.maxHeight);

        logger.atInfo().log("Route calculated");
        logger.atInfo().log("Route calculation time: " + (System.currentTimeMillis() - routeCalculationTime) + "ms");
        this.totalProcessTime += System.currentTimeMillis() - routeCalculationTime;
        return foundRoute;
    }

    public List<CoordinateVertex> getFoundRoute() {
        return foundRoute;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public Graph getGraph() {
        return _graph;
    }

    public String getRouteID() {
        return routeID;
    }

    @Override
    public String toString() {
        return "\n  RouteID: " + routeID + "\n      Origin: (Lat, Lon) " + 0.0 + ", " + 0.0 + "\n      Dest: (Lat, Lon) " + 0.0 + ", " + 0.0
                + "\n      Max Height: " + maxHeight + "\n      TotalVertices: " + _graph.totalVertices + "\n      ProcessTime:" + totalProcessTime + "ms" + "\n";
    }
}
