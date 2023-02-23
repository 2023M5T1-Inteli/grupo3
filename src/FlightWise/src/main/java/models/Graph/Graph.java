package models.Graph;

import models.Scorer.Harversine;
import models.edge.CoordinateEdge;
import models.vertex.CoordinateVertex;
import models.vertex.IVertex;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class Graph {
    private ArrayList<CoordinateVertex> vertexes = new ArrayList<>();

    public void addVertex(CoordinateVertex vertexNode) {
        vertexNode.setIndex(vertexes.size());
        vertexes.add(vertexNode);
    }

    public ArrayList<CoordinateVertex> getVertexes() {
        return vertexes;
    }

    public void addVertexEdgesByDistance(double distance){
        Harversine scorer = new Harversine();
        for (int i = 0; i < vertexes.size(); i++){
            for (int j = 0; j < vertexes.size(); j++){
                if (i == j) continue;
                double distanceToNextVertex = scorer.computeCost(vertexes.get(i), vertexes.get(j));

                if (distanceToNextVertex <= distance) {
                    vertexes.get(i).addEdge(vertexes.get(j), distanceToNextVertex);
                }
            }
        }
    }

    public ArrayList<CoordinateVertex> findPath(CoordinateVertex destinationVertex) {
        ArrayList<CoordinateVertex> path = new ArrayList<CoordinateVertex>();

        for (CoordinateVertex vertex = destinationVertex; vertex != null; vertex = vertex.previousVertex){
            path.add(vertex);
        }

        Collections.reverse(path);

        return path;
    }

    public void ASearch(int indexInitial, int indexTarget){
        Harversine scorer = new Harversine();

        CoordinateVertex initialVertex = vertexes.get(indexInitial);
        CoordinateVertex targetVertex = vertexes.get(indexTarget);

        Set<CoordinateVertex> explored = new HashSet<CoordinateVertex>();
        PriorityQueue<CoordinateVertex> queue = new PriorityQueue<CoordinateVertex>(getVertexes().size(),
                new Comparator<CoordinateVertex>() {
                    @Override
                    public int compare(CoordinateVertex o1, CoordinateVertex o2) {
                        return Double.compare((o1.totalCost + o1.minimalCost), (o2.totalCost + o2.minimalCost));
                    }
                });

        initialVertex.totalCost = 0;
        initialVertex.minimalCost = scorer.computeCost(initialVertex, targetVertex);

        queue.add(initialVertex);

        boolean found = false;

        while (!queue.isEmpty() && !found){
            CoordinateVertex currentVertex = queue.poll();

            explored.add(currentVertex);

            if (currentVertex.getIndex() == targetVertex.getIndex()){
                found = true;
            }

            for (CoordinateEdge ce: currentVertex.getEdges()){
                CoordinateVertex child = ce.targetVertex;
                double cost = ce.distance;
                double childTotalCost = currentVertex.totalCost + cost;
                double absoluteCost = childTotalCost + child.minimalCost;

                if (explored.contains(child) && (absoluteCost >= child.absoluteCost)){
                    continue;
                }
                else if(!queue.contains(child) || (absoluteCost < child.absoluteCost)){
                    child.previousVertex = currentVertex;
                    child.totalCost = childTotalCost;
                    child.absoluteCost = absoluteCost;

                    if (queue.contains(child)){
                        queue.remove(child);
                    }

                    queue.add(child);
                }
            }
        }
    }

}
