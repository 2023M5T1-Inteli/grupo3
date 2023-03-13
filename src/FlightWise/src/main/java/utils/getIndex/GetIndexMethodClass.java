package utils.getIndex;

import models.Graph.Graph;
import models.vertex.CoordinateVertex;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class GetIndexMethodClass {
    private Graph g;
    private double longitude;
    private double latitude;

    public GetIndexMethodClass(Graph g, double lon, double lat) {
        this.g = g;
        this.longitude = lon;
        this.latitude = lat;
    }

    /**
     * Take the index of determined vertex
     *
     * @return Returns an integer value, that represents the index of the vertex
     * */
    public int getVertexIndex() {
        ArrayList<CoordinateVertex> vertices = g.getVertexes();
        int index = -1;
        for (CoordinateVertex vertex: vertices ) {
            Point2D location = vertex.getPosition();
            if (location.getX() == longitude && location.getY() == latitude) {
                index = vertex.getIndex();
            }
        }

        return index;
    }
}
