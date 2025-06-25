package com.smart_routing.find_shortest_route.graph;

import com.harium.storage.kdtree.KeyDuplicateException;
import com.harium.storage.kdtree.KeySizeException;
import com.smart_routing.find_shortest_route.algorithms.HaversineDistanceCalculate;
import com.smart_routing.find_shortest_route.model.Edge;
import com.smart_routing.find_shortest_route.model.Location;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Brute-force implementation of GraphBuilder.
 * 
 * For each location, calculates the distance to all other locations using the
 * Haversine formula, then connects it to its N nearest neighbors.
 */
public class BruteForceGraphBuilder implements GraphBuilder {

    /**
     * Builds a graph by connecting each location to its N nearest neighbors using brute-force search.
     *
     * @param locations      List of all location nodes
     * @param neighborsCount Number of nearest neighbors to connect
     * @return Graph instance with all edges built
     * @throws KeySizeException         If dimensions are inconsistent (not expected here)
     * @throws KeyDuplicateException    If duplicate key is inserted (not expected here)
     */
    @Override
    public Graph build(List<Location> locations, int neighborsCount) throws KeySizeException, KeyDuplicateException {
        long start = System.currentTimeMillis();

        Graph graph = new Graph();

        // Register all nodes
        locations.forEach(graph::addLocation);

        // For each source location, compute distance to every other location
        locations.parallelStream().forEach(source -> {
            List<Edge> distanceList = new ArrayList<>();

            for (Location target : locations) {
                if (!source.equals(target)) {
                    double distance = HaversineDistanceCalculate.find(
                            source.getParsedLat(), source.getParsedLon(),
                            target.getParsedLat(), target.getParsedLon());

                    distanceList.add(new Edge(target, distance));
                }
            }

            // Sort and take the top-N nearest locations
            distanceList.sort(Comparator.comparingDouble(Edge::getDistance));
            List<Edge> nearest = distanceList.subList(0, Math.min(neighborsCount, distanceList.size()));

            // Safely add edges in a multi-threaded environment
            synchronized (graph) {
                for (Edge edge : nearest) {
                    graph.addEdge(source, edge.getDestination(), edge.getDistance());
                }
            }
        });

        long end = System.currentTimeMillis();
        System.out.println("Graph built in " + (end - start) / 1000.0 + " sec");

        return graph;
    }
}
