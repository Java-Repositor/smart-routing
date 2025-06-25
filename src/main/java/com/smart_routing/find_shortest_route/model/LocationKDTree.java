package com.smart_routing.find_shortest_route.model;

import com.harium.storage.kdtree.KDTree;
import com.harium.storage.kdtree.KeyDuplicateException;
import com.harium.storage.kdtree.KeySizeException;

import java.util.*;

/**
 * Wrapper around KDTree for efficient spatial search of Location objects
 * based on latitude and longitude.
 */
public class LocationKDTree {

    private final KDTree<Location> tree;

    /**
     * Constructs a KDTree with the given list of unique Locations.
     * Ensures no duplicate keys (lat, lon) are inserted to avoid exceptions.
     *
     * @param locations List of Location objects
     * @throws KeySizeException       If the key dimensions are not 2D
     * @throws KeyDuplicateException  If a key is accidentally duplicated
     */
    public LocationKDTree(List<Location> locations) throws KeySizeException, KeyDuplicateException {
        this.tree = new KDTree<>(2);
        Set<String> insertedKeys = new HashSet<>();

        for (Location loc : locations) {
            double[] key = new double[]{loc.getParsedLat(), loc.getParsedLon()};
            String keyString = loc.getParsedLat() + "," + loc.getParsedLon(); // unique string key

            if (insertedKeys.add(keyString)) { // only insert if not already present
                tree.insert(key, loc);
            }
        }
    }

    /**
     * Finds the k-nearest neighbors to a given source location.
     *
     * @param source Source location for proximity search
     * @param k      Number of nearest neighbors to return
     * @return List of nearest Location objects (may include source itself)
     * @throws KeySizeException         If the search key is not 2D
     * @throws IllegalArgumentException If k is larger than the number of nodes
     */
    public List<Location> findKNearest(Location source, int k)
            throws KeySizeException, IllegalArgumentException {

        double[] key = new double[]{source.getParsedLat(), source.getParsedLon()};
        List<Location> result = new ArrayList<>();

        for (Location neighbor : tree.nearest(key, k)) {
            result.add(neighbor);
        }
        return result;
    }
}
