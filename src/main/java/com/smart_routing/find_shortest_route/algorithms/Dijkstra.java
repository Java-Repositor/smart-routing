package com.smart_routing.find_shortest_route.algorithms;

import java.util.*;

import com.smart_routing.find_shortest_route.graph.Graph;
import com.smart_routing.find_shortest_route.model.Edge;
import com.smart_routing.find_shortest_route.model.Location;
import com.smart_routing.find_shortest_route.model.PathResult;

/**
 * Implements Dijkstra's algorithm to find the shortest path between
 * two locations in a weighted graph.
 */
public class Dijkstra {

    /**
     * Computes the shortest path between a source and destination Location using Dijkstra's algorithm.
     *
     * @param graph       the graph containing all locations and edges
     * @param source      the starting location
     * @param destination the target location
     * @return PathResult object containing the shortest path and total distance
     */
    public static PathResult findShortestPath(Graph graph, Location source, Location destination) {
        Map<Location, Double> distances = new HashMap<>();
        Map<Location, Location> previous = new HashMap<>();
        Set<Location> visited = new HashSet<>();

        // Min-heap to always pick the node with the smallest distance
        PriorityQueue<LocationDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(LocationDistance::getDistance));
        pq.offer(new LocationDistance(source, 0.0));
        distances.put(source, 0.0);

        while (!pq.isEmpty()) {
            LocationDistance current = pq.poll();
            Location currentLocation = current.getLocation();

            if (visited.contains(currentLocation)) continue;
            visited.add(currentLocation);

            // Early exit if destination is reached
            if (currentLocation.equals(destination)) break;

            for (Edge edge : graph.getEdges(currentLocation)) {
                Location neighbor = edge.getDestination();
                if (visited.contains(neighbor)) continue;

                double newDistance = distances.get(currentLocation) + edge.getDistance();

                if (newDistance < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, currentLocation);
                    pq.offer(new LocationDistance(neighbor, newDistance));
                }
            }
        }

        // Reconstruct the shortest path by backtracking
        List<Location> path = new ArrayList<>();
        Location step = destination;
        while (step != null) {
            path.add(step);
            step = previous.get(step);
        }
        Collections.reverse(path);

        double totalDistance = distances.getOrDefault(destination, Double.MAX_VALUE);
        return new PathResult(path, totalDistance);
    }

    /**
     * Helper class to hold a location and its associated distance from the source.
     * Used for priority queue comparison.
     */
    private static class LocationDistance {
        private final Location location;
        private final double distance;

        public LocationDistance(Location location, double distance) {
            this.location = location;
            this.distance = distance;
        }

        public Location getLocation() {
            return location;
        }

        public double getDistance() {
            return distance;
        }
    }
}
