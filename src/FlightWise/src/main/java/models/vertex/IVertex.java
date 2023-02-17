package models.vertex;

import models.edge.IEdge;
import org.neo4j.driver.util.Pair;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public interface IVertex {
    public void addEdge(IVertex endVertex);
    public Point2D getPosition();

    public int getIndex();

    public double calculateDistanceToVertex(IVertex vertex);
    public void removeEdge(IVertex endVertex);
    public ArrayList<? extends IEdge> getEdges();
}
