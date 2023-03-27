package utils.getIndex;

import models.Graph.Graph;
import models.vertex.CoordinateVertex;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetIndexMethodClassTest {
    @Test
    void getVertexIndex() {
        // To test this method we will reproduce a ArrayList to represent our vertices
        ArrayList<Point2D> positionsArray = new ArrayList<>(Arrays.asList(
                new Point2D.Double(50.0, 30.0),
                new Point2D.Double(49.995, 30.0),
                new Point2D.Double(49.990, 30.0),
                new Point2D.Double(49.985, 30.0),
                new Point2D.Double(49.980, 30.0),
                new Point2D.Double(49.975, 30.0)
        ));

        Graph newGraph = new Graph();

        for (int i = 0; i < positionsArray.size(); i++){
            CoordinateVertex newCoordinateVertex = new CoordinateVertex(positionsArray.get(i), 100.0);
            newGraph.addVertex(newCoordinateVertex);
        }

        newGraph.addVertexEdgesByDistance(0.7);

        ArrayList<CoordinateVertex> vertices = newGraph.getVertexes();

        // Testing the method: How you can see, we have the indexes bellow
        for (CoordinateVertex v: vertices) {
            System.out.println(v.getPosition().toString() + ": " + v.getIndex());
        }

        // To verify that our method is working, lets do an instance of the class, using the point (49.98, 30) the expected returned index is 4
        GetIndexMethodClass test = new GetIndexMethodClass(newGraph, 49.98, 30.0);
        assertEquals(4, test.getVertexIndex());
    }
}
