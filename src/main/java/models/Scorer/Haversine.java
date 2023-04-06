package models.Scorer;

import models.vertex.IVertex;

import java.awt.geom.Point2D;


/*
* The haversine formula determines the great-circle distance between two points on a sphere given
* their longitudes and latitudes.
* */
public class Haversine implements IScorer {
    private double HaversineCalc(double startPointLat, double startPointLong, double targetPointLat, double targetPointLong) {
        double deltaLatitude = Math.toRadians(targetPointLat - startPointLat);
        double deltaLongitude = Math.toRadians(targetPointLong - startPointLong);
        double startPointLatInRadians = Math.toRadians(startPointLat);
        double targetPointLatitudeInRadius = Math.toRadians(targetPointLat);

        double a = Math.pow(Math.sin(deltaLatitude / 2),2)
                + Math.pow(Math.sin(deltaLongitude / 2),2) * Math.cos(startPointLatInRadians) * Math.cos(targetPointLatitudeInRadius);
        double c = 2 * Math.asin(Math.sqrt(a));

        return c;
    }
    @Override
    public double computeCost(IVertex from, IVertex to) {
        double earthRadiusInkilometers = 6372.8;

        double startPointLatitude = from.getPosition().getX();
        double startPointLongitude = from.getPosition().getY();

        double targetPointLatitude = to.getPosition().getX();
        double targetPointLongitude = to.getPosition().getY();


        return earthRadiusInkilometers * HaversineCalc(startPointLatitude, startPointLongitude, targetPointLatitude, targetPointLongitude);
    }


    public double computeDistanceByPoint2D(Point2D point1, Point2D point2) {
        double earthRadiusInkilometers = 6372.8;

        double startPointLatitude = point1.getX();
        double startPointLongitude = point1.getY();

        double targetPointLatitude = point2.getX();
        double targetPointLongitude = point2.getY();

        return earthRadiusInkilometers * HaversineCalc(startPointLatitude, startPointLongitude, targetPointLatitude, targetPointLongitude);
    }
}
