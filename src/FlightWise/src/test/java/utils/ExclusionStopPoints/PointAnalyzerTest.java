package utils.ExclusionStopPoints;

import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

public class PointAnalyzerTest {
    @Test
    void testIsExclusionPoint() {
        PointAnalyzer testPointAnalyzer = new PointAnalyzer();
        Point2D point1 = new Point2D.Double(-74.0060, 40.7128); // New York City
        Point2D point2 = new Point2D.Double(-118.2437, 34.0522); // Los Angeles
        Point2D excludedPoint = new Point2D.Double(-122.4194, 37.7749); // San Francisco
        double radius = 700; // radius in km

        assertFalse(testPointAnalyzer.isExclusionPoint(point1, excludedPoint, radius)); // The point 1 is not in the exclusion zone
        assertTrue(testPointAnalyzer.isExclusionPoint(point2, excludedPoint, radius)); // The point 2 is in the exclusion zone

    }
}
