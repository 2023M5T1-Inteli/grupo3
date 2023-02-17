package models.vertex;

import models.edge.CoordinateEdge;
import models.edge.IEdge;
import org.neo4j.driver.util.Pair;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class CoordinateVertex implements IVertex {
    public int vertexIndex = 0;
    private ArrayList<CoordinateEdge> edges;
    public double averageHeight;

    private Point2D _position;

    public CoordinateVertex(Point2D position, double cAverageHeight) {
        _position = position;
        averageHeight = cAverageHeight;
    }

    public double calculateDistanceToVertex(IVertex target) {
        Point2D targetPosition = target.getPosition();
        double targetLat = targetPosition.getX();
        double targetLong = targetPosition.getY();

        double currentLat = _position.getX();
        double currentLong = _position.getY();

        if (targetLat == currentLat && targetLong == currentLong)
            return 0;

        double theta = currentLong - targetLong;
        double distance = Math.sin(Math.toRadians(currentLat)) * Math.sin(Math.toRadians(targetLat))
                + Math.cos(Math.toRadians(currentLat)) * Math.cos(Math.toRadians(targetLat))
                        * Math.cos(Math.toRadians(theta));
        distance = Math.acos(distance);
        distance = Math.toDegrees(distance);
        distance = distance * 60 * 1.1515;
        distance = distance * 1.609344;
        return distance;
    }

    public void addEdge(IVertex targetVertex) {
     double distance = this.calculateDistanceToVertex(targetVertex);
     int targetVertexIndex = targetVertex.getIndex();
     if(distance <= 0.030) {
         CoordinateEdge newEdge = new CoordinateEdge(vertexIndex, targetVertexIndex, distance, 0.0);
     }

    }

    public int getIndex() {
        return vertexIndex;
    }

    public Point2D getPosition() {
        return _position;
    }

    public void removeEdge(IVertex endVertex) {

    }

    public ArrayList<? extends IEdge> getEdges() {
        return edges;
    }
}