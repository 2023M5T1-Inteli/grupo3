package models.vertex;

import models.edge.CoordinateEdge;
import models.edge.IEdge;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class CoordinateVertex implements IVertex {
    private int vertexIndex;
    private ArrayList<CoordinateEdge> edges = new ArrayList<>();
    public double averageHeight;

    private Point2D _position;

    public CoordinateVertex(Point2D position, double cAverageHeight) {
        _position = position;
        averageHeight = cAverageHeight;
    }

    public void addEdge(int targetVertexIndex, double distance) {
//        double distance = this.calculateDistanceToVertex(targetVertex);

        CoordinateEdge newEdge = new CoordinateEdge(vertexIndex, targetVertexIndex, distance, 0.0);
        edges.add(newEdge);
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

    public void removeEdge(IVertex endVertex) {
        edges.remove(edges.stream().filter(x -> x.toVertexIndex == endVertex.getIndex()));
    }

    public ArrayList<CoordinateEdge> getEdges() {
        return edges;
    }
}