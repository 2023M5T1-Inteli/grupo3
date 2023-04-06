package models.Graph;

// Importing necessary libraries

import models.Scorer.Haversine;
import models.vertex.CoordinateVertex;

import java.util.ArrayList;
import java.util.HashMap;

/*
* The class Graph is the base to create the hole graph that represents a trajectory.
* It takes an ArrayList and a HashMap of vertices and apply  the necessary functions to implement the trajectory with the
* minimum cost
* */
public class Graph {
    private ArrayList<CoordinateVertex> vertices = new ArrayList<>();
    private CoordinateVertex[][] verticesMatrix;
    private HashMap<Integer, CoordinateVertex> verticesMap = new HashMap<>();

    public int totalVertices = 0;
    // The following function doesn't return anything, but adds a vertex to the array of vertices.
    public void addVertex(CoordinateVertex vertexNode, int rowPosition, int colPosition) {
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

    private void connectVertice(CoordinateVertex fromVertex, CoordinateVertex toVertex) {
        Haversine scorer = new Haversine();
        double distance = scorer.computeCost(fromVertex,toVertex);
        double heightRange = fromVertex.averageHeight - toVertex.averageHeight;
        fromVertex.addEdge(toVertex, distance, heightRange);
        toVertex.addEdge(fromVertex, distance, heightRange);
    }

    public void createEdges() {
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
