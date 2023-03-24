package models.Algorithms;

import models.Graph.Graph;
import models.Scorer.Haversine;
import models.edge.CoordinateEdge;
import models.vertex.CoordinateVertex;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
                        return Double.compare((o1.totalCost + o1.minimalCost), (o2.totalCost + o2.minimalCost));
                    }
                });

        initialVertex.totalCost = 0; // At the initial vertex we doens't have yet a total cost
        initialVertex.minimalCost = scorer.computeCost(initialVertex, targetVertex); // The minimum cost of the initial vetex is the distance of the initial and the target vertex, using Haversine

        queue.add(initialVertex);

        boolean found = false;


        while (!queue.isEmpty() && !found) { // Until the queue is empty and you arrive at the targetVertex.
            CoordinateVertex currentVertex = queue.poll(); // Keeping the vertex with the most priority in currentVertex and deleting it after.

            explored.add(currentVertex); // Adding this vertex in the set of already explored

            // If the currentVertex is the target
            if (currentVertex.getIndex() == targetVertex.getIndex()) {
                found = true;
            }


            // Mapping all the vertices that makes connection with the current vertex
            for (CoordinateEdge ce : currentVertex.getEdges()) {
                // Taking the target of each vertex that make connection with the current.
                CoordinateVertex child = ce.targetVertex;
                double cost = ce.distance;
                double childTotalCost = currentVertex.totalCost + cost;
                double absoluteCost = childTotalCost + child.minimalCost;

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
        // number of vertices in the graph (can be changed to test different performances)
        int testLength = 10000;
        double totalTime = 0.0;
        // The algorithm is executed 11 times and an average time is calculated

        double[] latitudes = new double[testLength]; // array to store latitudes
        double[] longitudes = new double[testLength]; // array to store longitudes
        int x = 0; // counter for arrays

        // reading the csv file with coordinates and adding the values to respective arrays.
        // pathname must be changed according to local path
        Scanner sc = new Scanner(new File("C:\\Users\\Vitor\\Documents\\Modulo5\\grupo3\\src\\FlightWise\\src\\main\\java\\models\\Algorithms\\coordinates.csv"));
        sc.nextLine(); // skip header line
        while (sc.hasNextLine() && x < testLength) {
            String line = sc.nextLine();
            String[] fields = line.split(",");
            double latitude = Double.parseDouble(fields[0]);
            double longitude = Double.parseDouble(fields[1]);
            latitudes[x] = latitude;
            longitudes[x] = longitude;
            x++;
        }
        sc.close();

        // creating a new graph
        Graph newGraph = new Graph();

        double[][] coordinates = new double[testLength][3];

        double max = 500.0;
        double min = 100.0;
        double range = max - min + 1;
        // adding information(longitude, latitude, height) of each point into the coordinates array.
        for (int c = 0; c < testLength; c++) {
            double height = (Math.random() * range) + min;
            coordinates[c] = new double[] {longitudes[c], latitudes[c], height};
        }

        // Adds all positions to the new Graph
        for (int i = 0; i < coordinates.length; i++) {
            Point2D currentPoint = new Point2D.Double(coordinates[i][0], coordinates[i][1]);
            CoordinateVertex newCoordinateVertex = new CoordinateVertex(currentPoint, coordinates[i][2]);
            newGraph.addVertex(newCoordinateVertex);

        }

        newGraph.addVertexEdgesByDistance(0.200);
        ArrayList<CoordinateVertex> vertices = newGraph.getVertexes();

        for (int t = 0; t < 11; t++) {
            double initialTime = System.nanoTime();
            // call the algorithm
            AStar algorithm = new AStar();
            ArrayList <CoordinateVertex> path = algorithm.ASearch(0, vertices.size()-1, vertices);
            // Final count of time
            double finalTime = System.nanoTime();

            // Calculing the total spend of time in algorithm
            double elapsedTime = (finalTime - initialTime)/1000000.0;
            if (t > 0) {
                totalTime += elapsedTime;
            }
        }
        System.out.println("Tempo decorrido: " + (totalTime/10.0) + " ms");
    }
}