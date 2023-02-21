package models.Graph;

import models.edge.CoordinateEdge;
import models.edge.IEdge;
import models.vertex.CoordinateVertex;
import models.vertex.IVertex;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Graph {
    private ArrayList<CoordinateVertex> vertexes = new ArrayList<>();

    public void addVertex(CoordinateVertex vertexNode) {
        vertexNode.setIndex(vertexes.size());
        vertexes.add(vertexNode);
    }

    public ArrayList<CoordinateVertex> getVertexes() {
        return vertexes;
    }

    public void addVertexEdges(){
        for (int i = 0; i < vertexes.size(); i++){
            for (int j = 0; j < vertexes.size(); j++){
                if (i == j) continue;

                double distanceToNextVertex = calculateDistanceToVertex(vertexes.get(i), vertexes.get(j));

                if (distanceToNextVertex <= 0.7) {
                    vertexes.get(i).addEdge(vertexes.get(j).getIndex(), distanceToNextVertex);
                }
            }
        }
    }

    public double calculateDistanceToVertex(IVertex firstVertex, IVertex secondVertex) {
        Point2D firstVertexPosition = firstVertex.getPosition();
        Point2D secondVertexPosition = secondVertex.getPosition();

        double secondVertexLat = secondVertexPosition.getX();
        double secondVertexLong = secondVertexPosition.getY();

        double firstVertexLat = firstVertexPosition.getX();
        double firstVertexLong = firstVertexPosition.getY();

        if (secondVertexLat == firstVertexLat && secondVertexLong == firstVertexLong)
            return 0.0;

        double theta = firstVertexLong - secondVertexLong;
        double distance = Math.sin(Math.toRadians(firstVertexLat)) * Math.sin(Math.toRadians(secondVertexLat))
                + Math.cos(Math.toRadians(firstVertexLat)) * Math.cos(Math.toRadians(secondVertexLat))
                * Math.cos(Math.toRadians(theta));
        distance = Math.acos(distance);
        distance = Math.toDegrees(distance);
        distance = distance * 60 * 1.1515;
        distance = distance * 1.609344;

        return distance;
    }
}
