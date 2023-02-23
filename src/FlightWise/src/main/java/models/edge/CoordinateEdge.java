package models.edge;

import models.vertex.CoordinateVertex;
import models.vertex.IVertex;

public class CoordinateEdge implements IEdge {
    public double distance;
    public double heightRange;
    public IVertex targetVertex;

    public final double cost;
    public CoordinateEdge(IVertex targetVertex, double distance, double heightRange) {
        this.targetVertex = targetVertex;
        this.distance = distance;
        this.heightRange = heightRange;
        this.cost = distance + heightRange;
    }
}
