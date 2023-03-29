package utils.ExclusionStopPoints;

import models.Scorer.Haversine;

import java.awt.geom.Point2D;

public class PointAnalyzer {

    public PointAnalyzer() {}
    /**
     * Determines if a given point (represented by a Point2D object) is within an exclusion zone by comparing the distance between
     * the two points using the Haversine formula and the radius value.
     *
     * @param point the point to be evaluated for exclusion.
     * @param excludedPoint the center point of the exclusion zone.
     * @param radius the radius of the exclusion zone in kilometers.
     * @return true if the given point is within the exclusion zone, false otherwise.
     * */
    public boolean isExclusionPoint(Point2D point, Point2D excludedPoint, Double radius) {
        Haversine scorer = new Haversine();
        double haversineDistance = scorer.computeDistanceByPoint2D(point, excludedPoint);
        if (haversineDistance < radius) {
            return true;
        }

        return false;
    }
}
