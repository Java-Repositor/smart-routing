package com.smart_routing.find_shortest_route.graph;

import java.util.List;

import com.harium.storage.kdtree.KeyDuplicateException;
import com.harium.storage.kdtree.KeySizeException;
import com.smart_routing.find_shortest_route.algorithms.HaversineDistanceCalculate;
import com.smart_routing.find_shortest_route.model.CustomKDTree;
import com.smart_routing.find_shortest_route.model.Location;
import com.smart_routing.find_shortest_route.model.LocationKDTree;

public class CustomGraphBuilder implements GraphBuilder {

	/**
     * Builds a graph using KD-Tree for efficient nearest-neighbor lookup.
     *
     * @param locations      List of all locations
     * @param neighborsCount Number of nearest neighbors to connect
     * @return Graph with optimized edges
     * @throws KeySizeException       If KDTree keys are not of expected dimension (2D here)
     * @throws KeyDuplicateException  If duplicate keys are found (handled during KDTree init)
     */
    @Override
    public Graph build(List<Location> locations, int neighborsCount) throws KeySizeException, KeyDuplicateException {
        Graph graph = new Graph();
        locations.forEach(graph::addLocation);

        CustomKDTree kdTree = new CustomKDTree(locations);
        
        kdTree.printTree();

        locations.parallelStream().forEach(source -> {
            try {
                // +1 to account for self, which will be excluded in loop
                List<Location> nearest = kdTree.findKNearest(source, neighborsCount + 1);

                for (Location target : nearest) {
                    if (!source.equals(target)) {
                        double distance = HaversineDistanceCalculate.find(
                                source.getParsedLat(), source.getParsedLon(),
                                target.getParsedLat(), target.getParsedLon());

                        synchronized (graph) {
                            graph.addEdge(source, target, distance);
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                // Critical failure in tree lookup — aborting this source
                throw new RuntimeException("Failed to fetch nearest neighbors for " + source.getPincode(), e);
            }
        });

        return graph;
    }

}
