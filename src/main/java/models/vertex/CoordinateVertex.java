package models.vertex;

import models.edge.CoordinateEdge;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/*
*  `CoordinateVertex` represents a vertex in a graph. In our case,
*   the vertex represents a location with an XY position and the following properties.
* */
public class CoordinateVertex implements IVertex {
    private int vertexIndex;
    private ArrayList<CoordinateEdge> adjacencies = new ArrayList<>(); //The adjacencies ArrayList allows us to keep all the edges the vertex makes with other vertices.
    public double averageHeight; // The avarege height of the geolocation
    public double minimalCost; // The minimum value of the relationship  between other vertices.
    public double totalCost; // The sum of the previous cost, is used in the A* algorithm
    public double absoluteCost; // Cost distance between the current vertex and the target
    public CoordinateVertex previousVertex;

    private Point2D _position; // 2D point that representes tha longitude and latitude.

    public CoordinateVertex(Point2D position, double averageHeight) {
        this._position = position;
        this.averageHeight = averageHeight;
    }

    /* The addEdge method inserts an edge into the adjacencies ArrayList, connecting it to another vertex,
    with the calculated distance between the two points. For this sprint,
    we are not considering variations in height.
     */
    public void addEdge(CoordinateVertex targetVertex, double distance) {
        CoordinateEdge newEdge = new CoordinateEdge(targetVertex, distance, 0.0);
        this.adjacencies.add(newEdge);
    }

    // Setting an index for it.
    public void setIndex(int index){
        vertexIndex = index;
    }

    // Returning the index value
    public int getIndex() {
        return vertexIndex;
    }

    // Returning a Point2D with XY position
    public Point2D getPosition() {
        return _position;
    }

    // Returning an ArrayList with the edges connected with the current vertex
    public ArrayList<CoordinateEdge> getEdges() {
        return adjacencies;
    }
}