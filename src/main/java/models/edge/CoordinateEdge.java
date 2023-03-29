package models.edge;

import models.vertex.CoordinateVertex;

/*
* `CoordinateEdge` represents an edge of the graph or trajectory. This class implements
* the edge that connects the current vertex to the target with a specific distance.
*
* For a better understanding of the modeling of the problem consult the documentation
* */
public class  CoordinateEdge implements IEdge {
    // Properties of the edge: distance, heightRange and the target vertex
    public double distance;
    public double heightRange;
    public CoordinateVertex targetVertex;

    public final double cost;
    public CoordinateEdge(CoordinateVertex targetVertex, double distance, double heightRange) {
        this.targetVertex = targetVertex;
        this.distance = distance;
        this.heightRange = heightRange;

        /* "The cost" is the primary property that relates to the distance and the difference in height.
        It is crucial for the algorithm to calculate the minimum cost to map the graph.
        */
        this.cost = distance + heightRange;
    }
}
