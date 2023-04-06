package models.Algorithms;

import models.Scorer.Haversine;
import models.edge.CoordinateEdge;
import models.vertex.CoordinateVertex;

import java.util.*;

public class AStar {

    public ArrayList<CoordinateVertex> findPath(CoordinateVertex destinationVertex) {
        ArrayList<CoordinateVertex> path = new ArrayList<CoordinateVertex>();

        /*
        The for loop starts from the destination vertex and is added in the "path" ArrayList until the vertex is null
        the previous vertex assumes the position inside the loop.

        It will be like: TargetVertex -> Vertex -> Vertex -> ... -> InitialVertex
        * */
        for (CoordinateVertex vertex = destinationVertex; vertex != null; vertex = vertex.previousVertex){
            path.add(vertex);
        }

        // Reverting the order of the path. Now it starts from the InitialVertex
        Collections.reverse(path);

        return path;
    }

    public ArrayList<CoordinateVertex> ASearch(int indexInitial, int indexTarget, ArrayList<CoordinateVertex> vertices, double maxHeight) {

        // Create a Haversine scorer
        Haversine scorer = new Haversine();

        // Get the initial and target vertices by their indices
        CoordinateVertex initialVertex = vertices.get(indexInitial);
        CoordinateVertex targetVertex = vertices.get(indexTarget);

        // Create a set of already explored vertices
        Set<CoordinateVertex> explored = new HashSet<CoordinateVertex>();

        // Create a priority queue that compares the totalCost + minimalCost of two vertices
        TreeSet<CoordinateVertex> queue = new TreeSet<>(
                new Comparator<CoordinateVertex>() {
                    @Override
                    public int compare(CoordinateVertex o1, CoordinateVertex o2) {
                        return Double.compare((o1.totalCost + o1.minimalCost * 0.6 + (o1.averageHeight/1000) * 0.4 + o1.exclusionCost), (o2.totalCost + o2.minimalCost * 0.6 + (o2.averageHeight/1000) * 0.4) + o2.exclusionCost);
                    }
                });

        // Set the initial vertex's totalCost to 0 and its minimalCost to the distance between the initial and target vertices
        initialVertex.totalCost = 0;
        initialVertex.minimalCost = scorer.computeCost(initialVertex, targetVertex);

        // Add the initial vertex to the queue
        queue.add(initialVertex);
        boolean found = false;

        // While the queue is not empty and we have not found the target vertex
        while (!queue.isEmpty() && !found) {

            // Get the vertex with the most priority from the queue
            CoordinateVertex currentVertex = queue.pollFirst();

            // Add the current vertex to the set of already explored vertices
            explored.add(currentVertex);

            // If the current vertex is the target vertex
            if (currentVertex.getIndex() == targetVertex.getIndex()) {
                found = true;
            }

            // For each edge connected to the current vertex
            for (CoordinateEdge ce : currentVertex.getEdges()) {

                // Get the target vertex of the edge
                CoordinateVertex child = ce.targetVertex;

                // Get the cost of the edge
                double cost = ce.cost;

                // Calculate the totalCost of the child vertex
                double childTotalCost = currentVertex.totalCost + cost;

                // Calculate the absoluteCost of the child vertex
                double absoluteCost = childTotalCost + child.minimalCost * 0.6 + (child.averageHeight/1000) * 0.4 + child.exclusionCost;

                // If the child vertex has already been explored and its absoluteCost is greater than the current absoluteCost, continue
                if (explored.contains(child) && (absoluteCost >= child.absoluteCost)) {
                    continue;
                } else if (!queue.contains(child) || (absoluteCost < child.absoluteCost)) {

                    // Otherwise, add the child vertex to the queue
                    child.previousVertex = currentVertex;
                    child.totalCost = childTotalCost;
                    child.absoluteCost = absoluteCost;

                    // If the child vertex is already in the queue, remove it
                    if (queue.contains(child)) {
                        queue.remove(child);
                    }

                    // Add the child vertex to the queue
                    queue.add(child);
                }
            }
        }

        // Find the path from the target vertex to the initial vertex
        ArrayList<CoordinateVertex> bestPath = findPath(targetVertex);

        // Return the best path
        return bestPath;
    }
}
