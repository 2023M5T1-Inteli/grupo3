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
        System.out.println("Total points:" + vertices.size());
        // Calculating the distance between a currentVertex (i) and the other vertices (j)
        for (int i = 0; i < vertices.size(); i++){
            //System.out.println("Connecting vertice: " + i);
            for (int j = 0; j < vertices.size(); j++){
                if (i == j) continue;
                double distanceToNextVertex = scorer.computeCost(vertices.get(i), vertices.get(j));

                if (distanceToNextVertex <= distance) {
                    vertices.get(i).addEdge(vertices.get(j), distanceToNextVertex);
                }
            }
        }
    }

   
}
