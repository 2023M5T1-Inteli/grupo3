package models.Graph;

// Importing necessary libraries
import models.Scorer.Haversine;
import models.edge.CoordinateEdge;
import models.vertex.CoordinateVertex;

import java.util.*;

/*
* The class Graph is the base to create the hole graph that represents a trajectory.
* It takes an ArrayList of vertices and apply  the necessary functions to implement the trajectory with the
* minimum cost
* */
public class Graph {
    private ArrayList<CoordinateVertex> vertices = new ArrayList<>();

    // The following function doesn't return anything, but adds a vertex to the array of vertices.
    public void addVertex(CoordinateVertex vertexNode) {
        vertexNode.setIndex(vertices.size()); // Setting the vertex index of the vertexNode to the current size of the array.
        vertices.add(vertexNode); // Adding the vertexNode
    }

    public ArrayList<CoordinateVertex> getVertexes() {
        return vertices;
    }

    /*
    * `addVertexEdgesByDistance` uses the distance property of each vertex to determinate if the vertex can make a edge between two vertices
    * The distance is calculated using the Haversine Formula:
    * */
    public void addVertexEdgesByDistance(double distance){
        Haversine scorer = new Haversine();

        // Calculating the distance between a currentVertex (i) and the other vertices (j)
        for (int i = 0; i < vertices.size(); i++){
            for (int j = 0; j < vertices.size(); j++){
                if (i == j) continue;
                double distanceToNextVertex = scorer.computeCost(vertices.get(i), vertices.get(j));

                if (distanceToNextVertex <= distance) {
                    vertices.get(i).addEdge(vertices.get(j), distanceToNextVertex);
                }
            }
        }
    }

    // Finding a path (insert comment)
    public ArrayList<CoordinateVertex> findPath(CoordinateVertex destinationVertex) {
        ArrayList<CoordinateVertex> path = new ArrayList<CoordinateVertex>();

        for (CoordinateVertex vertex = destinationVertex; vertex != null; vertex = vertex.previousVertex){
            path.add(vertex);
        }

        Collections.reverse(path);

        return path;
    }

    // ASearch innserting comment
    public void ASearch(int indexInitial, int indexTarget){
        Haversine scorer = new Haversine();

        CoordinateVertex initialVertex = vertices.get(indexInitial);
        CoordinateVertex targetVertex = vertices.get(indexTarget);

        Set<CoordinateVertex> explored = new HashSet<CoordinateVertex>();
        PriorityQueue<CoordinateVertex> queue = new PriorityQueue<CoordinateVertex>(getVertexes().size(),
                new Comparator<CoordinateVertex>() {
                    @Override
                    public int compare(CoordinateVertex o1, CoordinateVertex o2) {
                        return Double.compare((o1.totalCost + o1.minimalCost), (o2.totalCost + o2.minimalCost));
                    }
                });

        initialVertex.totalCost = 0;
        initialVertex.minimalCost = scorer.computeCost(initialVertex, targetVertex);

        queue.add(initialVertex);

        boolean found = false;

        while (!queue.isEmpty() && !found){
            CoordinateVertex currentVertex = queue.poll();

            explored.add(currentVertex);

            if (currentVertex.getIndex() == targetVertex.getIndex()){
                found = true;
            }

            for (CoordinateEdge ce: currentVertex.getEdges()){
                CoordinateVertex child = ce.targetVertex;
                double cost = ce.distance;
                double childTotalCost = currentVertex.totalCost + cost;
                double absoluteCost = childTotalCost + child.minimalCost;

                if (explored.contains(child) && (absoluteCost >= child.absoluteCost)){
                    continue;
                }
                else if(!queue.contains(child) || (absoluteCost < child.absoluteCost)){
                    child.previousVertex = currentVertex;
                    child.totalCost = childTotalCost;
                    child.absoluteCost = absoluteCost;

                    if (queue.contains(child)){
                        queue.remove(child);
                    }

                    queue.add(child);
                }
            }
        }
    }

}
