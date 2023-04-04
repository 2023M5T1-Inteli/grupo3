package models.vertex;

import models.edge.CoordinateEdge;

import java.awt.geom.Point2D;
import java.util.ArrayList;

// This interface intends to be developed during the next sprint
public interface IVertex {
    public void addEdge(CoordinateVertex targetVertex, double distance, double heightRange);

    public Point2D getPosition();

    public void setIndex(int index);

    public int getIndex();

    public ArrayList<CoordinateEdge> getEdges();
}
