package models.Graph;

import models.edge.CoordinateEdge;
import models.vertex.CoordinateVertex;

import java.awt.geom.Point2D;
import java.util.ArrayList;

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
        Graph graphToTest = new Graph();

        assertEquals(0, graphToTest.getVertexes().size());
        graphToTest.addVertex(vertexToTest);
        assertEquals(1, graphToTest.getVertexes().size());
    }

    /*
    * "addVertexEdgesByDistance" only add an Edge between two vertices if is in the range of a specific distance.
    *
    * To test it, we will add two vertices more into the graph: one of them, is not in the specific range of distance, and
    * the other is in.
    * */

    /*
    * The test bellow will be implemented in the next sprint, with the implementation of the interface
    * */
    @org.junit.jupiter.api.Test
    void addVertexEdgesByDistance() {

    }

    @org.junit.jupiter.api.Test
    void findPath() {

    }

    @org.junit.jupiter.api.Test
    void ASearch() {

    }

}
