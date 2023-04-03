package path;

import models.Algorithms.AStar;
import models.Graph.Graph;
import models.vertex.CoordinateVertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class PathPlanner {

    private static final Logger logger = LoggerFactory.getLogger(PathPlanner.class);
    private final String routeID;
    private final double latInitial;
    private final double lonInitial;
    private final double latFinal;
    private final double lonFinal;

    private final double maxHeight;
    private Graph _graph;

    private double totalProcessTime = 0.0;

    private ArrayList<CoordinateVertex> foundRoute;

    public PathPlanner(String routeID, double[][][] coordinates, double latInitial, double lonInitial, double latFinal, double lonFinal) {
        this.routeID = routeID;
        this.latInitial = latInitial;
        this.lonInitial = lonInitial;
        this.latFinal = latFinal;
        this.lonFinal = lonFinal;

        double maxHeight = Double.NEGATIVE_INFINITY;
        this._graph = new Graph(coordinates.length, coordinates[0].length);

        long graphCreationTime = System.currentTimeMillis();
        logger.atInfo().log("Creating graph");

        for (int i = 0; i < coordinates.length; i++) {
            for (int j = 0; j < coordinates[i].length; j++) {
                double height = coordinates[i][j][2];
                if (height > maxHeight) maxHeight = height;
                Point2D currentPoint = new Point2D.Double(coordinates[i][j][1], coordinates[i][j][0]);
                CoordinateVertex newCoordinateVertex = new CoordinateVertex(currentPoint, height);
                this._graph.addVertex(newCoordinateVertex, i, j);
            }
        }
        logger.atInfo().log("Graph created");
        logger.atInfo().log("Graph creation time: " + (System.currentTimeMillis() - graphCreationTime) + "ms");
        this.totalProcessTime += System.currentTimeMillis() - graphCreationTime;

        this.maxHeight = maxHeight;

        long edgesCreationTime = System.currentTimeMillis();
        logger.atInfo().log("Creating edges");
        this._graph.createEdges();
        logger.atInfo().log("Edges created");
        logger.atInfo().log("Edges creation time: " + (System.currentTimeMillis() - edgesCreationTime) + "ms");
        this.totalProcessTime += System.currentTimeMillis() - edgesCreationTime;
    }

    public ArrayList<CoordinateVertex> traceRoute() {
        // Calculates the optimal path between two nodes(vertex)
        logger.atInfo().log("Calculating route");
        long routeCalculationTime = System.currentTimeMillis();
        ArrayList<CoordinateVertex> vertices = this._graph.getVertexes();
        AStar algorithm = new AStar();
        this.foundRoute = algorithm.ASearch(0, vertices.size() - 1, vertices, this.maxHeight);
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


    public double getLatInitial() {
        return latInitial;
    }

    public double getLonInitial() {
        return lonInitial;
    }

    public double getLatFinal() {
        return latFinal;
    }

    public double getLonFinal() {
        return lonFinal;
    }

    @Override
    public String toString() {
        return "\n  RouteID: " + routeID + "\n      Origin: (Lat, Lon) " + latInitial + ", " + lonInitial + "\n      Dest: (Lat, Lon) " + latFinal + ", " + lonFinal
                + "\n      Max Height: " + maxHeight + "\n      TotalVertices: " + _graph.totalVertices + "\n      ProcessTime:" + totalProcessTime + "ms" + "\n";
    }
}
