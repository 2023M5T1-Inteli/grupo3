package models.Graph;

import models.vertex.CoordinateVertex;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraphTest {
    /*
     * "addVertex" is a void method, so it's hard to determine if it is performing as expected.
     * To verify this, we can initialize a "Graph" and a "CoordinateVertex" object and compare the size of the
     * ArrayList before and after calling "addVertex".
     *
     * By using this approach, we can test both the "addVertex" and "getVertexes" methods at the same time.
     * */
    CoordinateVertex vertexToTest = new CoordinateVertex((new Point2D.Double(30.4, 12.3)), 1000);

    @org.junit.jupiter.api.Test
    void addVertex() {
        Graph graphToTest = new Graph(1,1);

        assertEquals(0, graphToTest.getVertexes().size());
        graphToTest.addVertex(vertexToTest, 0,0);
        assertEquals(1, graphToTest.getVertexes().size());
    }



}
