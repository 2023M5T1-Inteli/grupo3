package models.Scorer;

import models.vertex.CoordinateVertex;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HaversineTest {

    @Test
    void computeCost() {
        Haversine haversine = new Haversine();
        // Setting two vertices to calculate their distance
        CoordinateVertex currentVertexToTest = new CoordinateVertex((new Point2D.Double(30.4, 12.3)), 1000);
        CoordinateVertex targetVertexToTest = new CoordinateVertex((new Point2D.Double(50.3, 20.3)), 500);

        // Calculating the distance from the points above, it is approximately 2311
        assertEquals(2311, (int) haversine.computeCost(currentVertexToTest, targetVertexToTest));
    }

    @Test
    void computeDistanceByPoint2D() {
        Haversine haversine = new Haversine();

        Point2D point1 = new Point2D.Double(30.4, 12.3);
        Point2D point2 = new Point2D.Double(50.3, 20.3);

        assertEquals(2311, (int) haversine.computeDistanceByPoint2D(point1, point2));
    }
}