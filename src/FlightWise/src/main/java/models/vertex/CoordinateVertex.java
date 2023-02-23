package models.vertex;

import models.edge.CoordinateEdge;
import models.edge.IEdge;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class CoordinateVertex implements IVertex {
    private int vertexIndex;
    private ArrayList<CoordinateEdge> adjacencies = new ArrayList<>();
    public double averageHeight;
    public double minimalCost;
    public double totalCost;
    public double absoluteCost;
    public CoordinateVertex previousVertex;

    private Point2D _position;

    public CoordinateVertex(Point2D position, double averageHeight) {
        this._position = position;
        this.averageHeight = averageHeight;
    }

    public void addEdge(CoordinateVertex targetVertex, double distance) {
        CoordinateEdge newEdge = new CoordinateEdge(targetVertex, distance, 0.0);
        this.adjacencies.add(newEdge);
    }

    public void setIndex(int index){
        vertexIndex = index;
    }

    public int getIndex() {
        return vertexIndex;
    }

    public Point2D getPosition() {
        return _position;
    }
    

    public void removeEdge(IVertex targetVertex) {
        adjacencies.remove(adjacencies.stream().filter(x -> x.targetVertex.getIndex() == targetVertex.getIndex()));
    }

    public ArrayList<CoordinateEdge> getEdges() {
        return adjacencies;
    }
}