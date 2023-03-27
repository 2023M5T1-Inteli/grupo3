package models.edge;

import models.vertex.CoordinateVertex;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoordinateEdgeTest {
    /*
     * The "CoordinateEdge" class does not have any methods to test. Therefore,
     * to ensure that the class is initialized, we verify its properties after initialization as the target vertex, distance and height range
     * */
    public static void verifyInitializationClass() {
        Point2D targetPoint = new Point2D.Double(40.1, 30.3);
        CoordinateVertex targetVertex = new CoordinateVertex(targetPoint, 200);
        CoordinateEdge coordinateEdgeTest = new CoordinateEdge(targetVertex, 300, 100);

        assertEquals(targetVertex, coordinateEdgeTest.targetVertex);
        assertEquals(300, coordinateEdgeTest.distance);
        assertEquals(100, coordinateEdgeTest.heightRange);
    }

    public static  void main(String[] args){
        verifyInitializationClass();
    }
}