package models.vertex;

import models.edge.CoordinateEdge;
import models.edge.IEdge;
import org.neo4j.driver.util.Pair;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public interface IVertex {
    public void addEdge(CoordinateVertex targetVertex, double distance);
    public Point2D getPosition();

    public void setIndex(int index);
    public int getIndex();

    public void removeEdge(IVertex targetVertex);
    public ArrayList<CoordinateEdge> getEdges();
}
