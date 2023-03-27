package models.Algorithms;

import models.Graph.Graph;
import models.Scorer.Haversine;
import models.edge.CoordinateEdge;
import models.vertex.CoordinateVertex;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//import controller.model.RouteItem;
import models.Graph.Graph;
import models.vertex.CoordinateVertex;
import org.json.JSONObject;
import utils.Points;
import utils.getIndex.GetIndexMethodClass;

import java.awt.geom.Point2D;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

        

public class AStar {

    public ArrayList<CoordinateVertex> findPath(CoordinateVertex destinationVertex) {
        ArrayList<CoordinateVertex> path = new ArrayList<CoordinateVertex>();

        /*
        The for loop starts from the destination vertex and is added in the "path" ArrayList until the vertex is null
        the previous vertex assumes the position inside the loop.

        It will be like: TargetVertex -> Vertex -> Vertex -> ... -> InitialVertex
        * */
        for (CoordinateVertex vertex = destinationVertex; vertex != null; vertex = vertex.previousVertex){
            path.add(vertex);
        }

        // Reverting the order of the path. Now it starts from the InitialVertex
        Collections.reverse(path);

        return path;
    }

    public ArrayList<CoordinateVertex> ASearch(int indexInitial, int indexTarget, ArrayList<CoordinateVertex> vertices) {

        Haversine scorer = new Haversine();

        // Taking the vertices by its indices
        CoordinateVertex initialVertex = vertices.get(indexInitial);
        CoordinateVertex targetVertex = vertices.get(indexTarget);

        // Keeping the already explored vertices
        Set<CoordinateVertex> explored = new HashSet<CoordinateVertex>();

        // Setting a priority queue comparing the totalCost + minimalCost of two vertices
        PriorityQueue<CoordinateVertex> queue = new PriorityQueue<CoordinateVertex>(vertices.size(),
                new Comparator<CoordinateVertex>() {
                    @Override
                public int compare(CoordinateVertex o1, CoordinateVertex o2) {
                        return Double.compare((o1.totalCost + o1.minimalCost + o1.averageHeight), (o2.totalCost + o2.minimalCost + o2.averageHeight));
                    }
                });

        initialVertex.totalCost = 0; // At the initial vertex we doens't have yet a total cost
        initialVertex.minimalCost = scorer.computeCost(initialVertex, targetVertex); // The minimum cost of the initial vetex is the distance of the initial and the target vertex, using Haversine

        queue.add(initialVertex);

        boolean found = false;


        while (!queue.isEmpty() && !found){ // Until the queue is empty and you arrive at the targetVertex.
            CoordinateVertex currentVertex = queue.poll(); // Keeping the vertex with the most priority in currentVertex and deleting it after.

            explored.add(currentVertex); // Adding this vertex in the set of already explored

            // If the currentVertex is the target
            if (currentVertex.getIndex() == targetVertex.getIndex()){
                found = true;
            }


            // Mapping all the vertices that makes connection with the current vertex
            for (CoordinateEdge ce: currentVertex.getEdges()) {
                // Taking the target of each vertex that make connection with the current.
                CoordinateVertex child = ce.targetVertex;
                double cost = ce.distance;
                double childTotalCost = currentVertex.totalCost + cost;
                double absoluteCost = childTotalCost + child.minimalCost + child.averageHeight;

                // If the explored already passed by the child, and the absoluteCost is more than child absoluteCost, keep throw the loop
                if (explored.contains(child) && (absoluteCost >= child.absoluteCost)) {
                    continue;
                } else if (!queue.contains(child) || (absoluteCost < child.absoluteCost)) {

                    // Otherwise, add to the child the calculated properties and add it to the queue
                    child.previousVertex = currentVertex;
                    child.totalCost = childTotalCost;
                    child.absoluteCost = absoluteCost;

                    if (queue.contains(child)) {
                        queue.remove(child);
                    }

                    queue.add(child);
                }
            }
        }

        ArrayList <CoordinateVertex> bestPath = findPath(targetVertex);

        return bestPath;

    }
    
    // This method is only to test the running time of the algorithm.
    public static void main(String[] args) throws FileNotFoundException {
        
        int testLength = 10000;
        double totalTime = 0.0;
        
        
        /**
         * Currently this class is being responsible for acting as our server and
         * executing the route for the A* algorithm.
         */

            
          
      
        
        // Decoding the characters to UTF-8
        //String dataDecoded = URLDecoder.decode(data, StandardCharsets.UTF_8);

        // Parsing the decoded string to a JSON object and extracting the values
        //JSONObject obj = new JSONObject(dataDecoded);

//        String filePath = obj.getString("filePath");
//
//        Double lonInitial = obj.getDouble("lonInitial");
//        Double latInitial = obj.getDouble("latInitial");
//
//
//        Double lonFinal = obj.getDouble("lonFinal");
//        Double latFinal = obj.getDouble("latFinal");

        

        // Reading the dt2 file and taking the positions of the region
        Points points = new Points();
        double[][] coordinates = points.Coordinates("./dted/Rio", -43.4082, -22.1780,-43.5056, -22.2813, 0.0011, 0.0014);

        // Initializes a new Graph()
        Graph newGraph = new Graph();


        // Adds all positions to the new Graph
        for (int i = 0; i < coordinates.length; i++){
            Point2D currentPoint = new Point2D.Double(coordinates[i][1],  coordinates[i][0]);
            CoordinateVertex newCoordinateVertex = new CoordinateVertex(currentPoint, coordinates[i][2]);
            newGraph.addVertex(newCoordinateVertex);
        }

        // Create all vertex edges based on distance
        newGraph.addVertexEdgesByDistance(0.100);

        // Taking the index of the target position
        GetIndexMethodClass getIndex = new GetIndexMethodClass(newGraph, -43.5056, -22.1780);


        
        ArrayList<CoordinateVertex> vertices = newGraph.getVertexes();

        for (int t = 0; t < 11; t++) {
            double initialTime = System.nanoTime();
            // call the algorithm
            AStar algorithm = new AStar();
            ArrayList <CoordinateVertex> newList = algorithm.ASearch(0, vertices.size()-1, vertices);
            // Final count of time
            double finalTime = System.nanoTime();

            // Calculing the total spend of time in algorithm
            double elapsedTime = (finalTime - initialTime)/1000000.0;
            if (t > 0) {
                totalTime += elapsedTime;
            }
        }
        System.out.println("Tempo decorrido: " + (totalTime/10.0) + " ms");
//        System.out.println(newList.get(0).getIndex());
//                System.out.println(newList.get(1).previousVertex.getIndex());
    }
}
