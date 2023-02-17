package models.Graph;

import models.vertex.CoordinateVertex;

import java.util.ArrayList;

public class Graph {
    private ArrayList<CoordinateVertex> vertexes = new ArrayList<>();
    public void addVertex(CoordinateVertex vertexNode) {
        vertexes.add(vertexNode);
    }
}
