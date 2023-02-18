package models.edge;

import models.vertex.CoordinateVertex;

public class CoordinateEdge implements IEdge {
    public double distance;
    public double heightRange;
    public int fromVertexIndex;
    public int toVertexIndex;
    public CoordinateEdge(int cFromVertexIndex, int cToVertexIndex, double cDistance, double cHeight) {
        distance = cDistance;
        heightRange = cHeight;
        fromVertexIndex = cFromVertexIndex;
        toVertexIndex = cToVertexIndex;
    }
}
