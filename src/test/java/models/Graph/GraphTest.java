package models.Graph;

import models.vertex.CoordinateVertex;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraphTest {
    @Test
    public void testAddVertex() {
        Graph graph = new Graph(2, 2);
        CoordinateVertex vertex = new CoordinateVertex(new Point2D.Double(20.0, 21.2), 2);
        graph.addVertex(vertex, 0, 0);
        assertEquals(1, graph.totalVertices);
    }

}
