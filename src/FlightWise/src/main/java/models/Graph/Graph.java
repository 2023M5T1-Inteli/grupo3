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

    /*
    * Return a ArrayList of vertices, with the path from the initial vertex to the destiny.
    * */
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

    /*
    * Applying the A* Algorithm to find the path with minimum cost
    * */
    public void ASearch(int indexInitial, int indexTarget){
        Haversine scorer = new Haversine();

        // Taking the vertices by its indices
        CoordinateVertex initialVertex = vertices.get(indexInitial);
        CoordinateVertex targetVertex = vertices.get(indexTarget);

        // Keeping the already explored vertices
        Set<CoordinateVertex> explored = new HashSet<CoordinateVertex>();

        // Setting a priority queue comparing the totalCost + minimalCost of two vertices
        PriorityQueue<CoordinateVertex> queue = new PriorityQueue<CoordinateVertex>(getVertexes().size(),
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
                double absoluteCost = childTotalCost + child.minimalCost;

                // If the explored already passed by the child, and the absoluteCost is more than child absoluteCost, keep throw the loop
                if (explored.contains(child) && (absoluteCost >= child.absoluteCost)){
                    continue;
                }
                else if(!queue.contains(child) || (absoluteCost < child.absoluteCost)){

                    // Otherwise, add to the child the calculated properties and add it to the queue
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
