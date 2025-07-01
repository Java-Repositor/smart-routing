package com.smart_routing.find_shortest_route.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Custom KD-Tree implementation for 2D spatial indexing (latitude, longitude)
 */
public class CustomKDTree {

    private static class KDNode {
        Location point;
        KDNode left, right;
        int depth;

        public KDNode(Location point, int depth) {
            this.point = point;
            this.depth = depth;
        }
    }

    private KDNode root;

    /**
     * Constructs the KD-Tree from a list of locations
     */
    public CustomKDTree(List<Location> locations) {
        this.root = build(locations, 0);
    }

    /**
     * Recursive method to build the KD-Tree
     */
    private KDNode build(List<Location> points, int depth) {
        if (points == null || points.isEmpty()) return null;

        int axis = depth % 2;
        points.sort(Comparator.comparingDouble(loc -> axis == 0 ? loc.getParsedLat() : loc.getParsedLon()));
        
        int median = points.size() / 2;

        KDNode node = new KDNode(points.get(median), depth);
        node.left = build(points.subList(0, median), depth + 1);
        node.right = build(points.subList(median + 1, points.size()), depth + 1);

        return node;
    }

    /**
     * Finds k nearest neighbors for the target location using max-heap
     */
    public List<Location> findKNearest(Location target, int k) {
        PriorityQueue<LocationDistance> maxHeap = new PriorityQueue<>(
                Comparator.comparingDouble(LocationDistance::getDistance).reversed()
        );

        searchKNN(root, target, k, maxHeap);

        List<Location> result = new ArrayList<>();
        for (LocationDistance ld : maxHeap) {
            result.add(ld.location);
        }
        return result;
    }

    /**
     * Recursive KNN search
     */
    private void searchKNN(KDNode node, Location target, int k, PriorityQueue<LocationDistance> heap) {
        if (node == null) return;

        double dist = distance(target, node.point);
        if (!target.equals(node.point)) {
            heap.offer(new LocationDistance(node.point, dist));
            if (heap.size() > k) heap.poll();
        }

        int axis = node.depth % 2;
        double targetCoord = axis == 0 ? target.getParsedLat() : target.getParsedLon();
        double nodeCoord = axis == 0 ? node.point.getParsedLat() : node.point.getParsedLon();

        KDNode first = targetCoord < nodeCoord ? node.left : node.right;
        KDNode second = first == node.left ? node.right : node.left;

        searchKNN(first, target, k, heap);

        if (Math.abs(targetCoord - nodeCoord) < heap.peek().distance || heap.size() < k) {
            searchKNN(second, target, k, heap);
        }
    }

    /**
     * Computes Euclidean distance (sufficient for nearest-neighbor search)
     */
    private double distance(Location a, Location b) {
        return Math.sqrt(
                Math.pow(a.getParsedLat() - b.getParsedLat(), 2) +
                        Math.pow(a.getParsedLon() - b.getParsedLon(), 2)
        );
    }

    /**
     * Helper class to associate a location with distance
     */
    private static class LocationDistance {
        Location location;
        double distance;

        LocationDistance(Location location, double distance) {
            this.location = location;
            this.distance = distance;
        }

        public double getDistance() {
            return distance;
        }
    }

    public KDNode getRoot() {
        return root;
    }
    
    public void printTree() {
        printTree(root, "", true);
    }

    private void printTree(KDNode node, String prefix, boolean isLeft) {
        if (node != null) {
            System.out.println(prefix + (isLeft ? "├── " : "└── ") + node.point.getPincode() + "," + node.point.getParsedLat());
            printTree(node.left, prefix + (isLeft ? "│   " : "    "), true);
            printTree(node.right, prefix + (isLeft ? "│   " : "    "), false);
        }
    }

} 
