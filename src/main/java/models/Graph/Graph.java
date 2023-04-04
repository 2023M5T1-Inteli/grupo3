package models.Graph;

// Importing necessary libraries

import models.Scorer.Haversine;
import models.vertex.CoordinateVertex;

import java.util.ArrayList;
import java.util.HashMap;

/*
* The class Graph is the base to create the hole graph that represents a trajectory.
* It takes an ArrayList of vertices and apply  the necessary functions to implement the trajectory with the
* minimum cost
* */
public class Graph {
    private ArrayList<CoordinateVertex> vertices = new ArrayList<>();
    private CoordinateVertex[][] verticesMatrix;
    private HashMap<Integer, CoordinateVertex> verticesMap = new HashMap<>();

    public int totalVertices = 0;
    // The following function doesn't return anything, but adds a vertex to the array of vertices.
    public void addVertex(CoordinateVertex vertexNode, int rowPosition, int colPosition) {
//        vertexNode.setIndex(vertices.size()); // Setting the vertex index of the vertexNode to the current size of the array.
//        vertices.add(vertexNode); // Adding the vertexNode
        verticesMatrix[rowPosition][colPosition] = vertexNode;
        this.totalVertices++;
    }

    public Graph(int row, int col) {
        this.verticesMatrix = new CoordinateVertex[row][col];
    }


    public boolean contains(int vertexId) {
        return verticesMap.containsKey(vertexId);
    }

    public CoordinateVertex getVertex(int vertexId) {
        return verticesMap.get(vertexId);
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
                    vertices.get(i).addEdge(vertices.get(j), distanceToNextVertex,0);
                }
            }
        }
    }

    private void connectVertice(CoordinateVertex fromVertex, CoordinateVertex toVertex) {
        Haversine scorer = new Haversine();
        double distance = scorer.computeCost(fromVertex,toVertex);
        double heightRange = fromVertex.averageHeight - toVertex.averageHeight;
        fromVertex.addEdge(toVertex, distance, heightRange);
        toVertex.addEdge(fromVertex, distance, heightRange);
    }

    public void createEdges() {
        Haversine scorer = new Haversine();
        int counter = 0;
        for(int i =0; i < this.verticesMatrix.length; i++) {
            for(int j=0; j< this.verticesMatrix[i].length; j++) {
                CoordinateVertex currVertex = verticesMatrix[i][j];
                // connect to the right
                if(j < this.verticesMatrix[i].length - 1) {
                    CoordinateVertex nextVertex = verticesMatrix[i][j+1];
                    connectVertice(currVertex, nextVertex);
                }

                if(i < this.verticesMatrix.length - 1) {
                    CoordinateVertex nextVertex = verticesMatrix[i+1][j];
                    connectVertice(currVertex, nextVertex);
                }

                if(i < this.verticesMatrix.length - 1 && j > 0) {
                    CoordinateVertex nextVertex = verticesMatrix[i+1][j-1];
                    connectVertice(currVertex, nextVertex);
                }

                if(i < this.verticesMatrix.length - 1 && j < this.verticesMatrix[i].length - 1) {
                    CoordinateVertex nextVertex = verticesMatrix[i+1][j+1];
                    connectVertice(currVertex, nextVertex);
                }
                currVertex.setIndex(counter++);
                this.vertices.add(currVertex);
            }
        }
    }

   
}
