package org.example;
import models.Graph.Graph;
import models.edge.CoordinateEdge;
import models.vertex.CoordinateVertex;
import org.neo4j.driver.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;


public class Main {

    public static void createCoodinateNode(CoordinateVertex coordinateVertex, Session session) {
        
        String cypherQuery = "CREATE (p:Coordinate {latitude: $lat, longitude: $long, averageHeight: $avgHeight})";
        try (Transaction tx = session.beginTransaction()) {
            Point2D position = coordinateVertex.getPosition();
            tx.run(cypherQuery, Map.of("lat", position.getX(),"long", position.getY(), "avgHeight", coordinateVertex.averageHeight));
            tx.commit();
        }
    }

    public static void main(String[] args) {
        Driver driver = GraphDatabase.driver("neo4j+s://41f6b34f.databases.neo4j.io",
                AuthTokens.basic("neo4j","<password>"));

        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            Point2D position = new Point2D.Double(0.0, 0.0);
            CoordinateVertex newCoordinateVertex = new CoordinateVertex(position, 0.0);
            createCoodinateNode(newCoordinateVertex, session);

        }
        driver.close();


        //region teste criação de grafo
        Point2D position1 = new Point2D.Double(50.0, 30.0);
        CoordinateVertex newCoordinateVertex1 = new CoordinateVertex(position1, 100.0);

        Point2D position2 = new Point2D.Double(49.995, 30.0);
        CoordinateVertex newCoordinateVertex2 = new CoordinateVertex(position2, 100.0);

        Graph newGraph = new Graph();
        newGraph.addVertex(newCoordinateVertex1);
        newGraph.addVertex(newCoordinateVertex2);

        newGraph.addVertexEdges();

        ArrayList<CoordinateVertex> vertexes = newGraph.getVertexes();

        CoordinateVertex vertexTest1 = vertexes.get(0);
        CoordinateVertex vertexTest2 = vertexes.get(1);
        ArrayList<CoordinateEdge> edgeListTest = vertexTest1.getEdges();

        System.out.println(newGraph.getVertexes());
        System.out.println(vertexTest1.getEdges());
        System.out.println(vertexTest1.getIndex());
        System.out.println(vertexTest2.getIndex());
        System.out.println(edgeListTest.get(0).distance);
        System.out.println(edgeListTest.get(0).heightRange);
        System.out.println(edgeListTest.get(0).fromVertexIndex);
        System.out.println(edgeListTest.get(0).toVertexIndex);

        //endregion
    }
}