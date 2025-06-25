package com.smart_routing.find_shortest_route.algorithms;

/**
 * Utility class to calculate the great-circle distance between two
 * geographical coordinates using the Haversine formula.
 *
 * This formula accounts for the curvature of the Earth, providing
 * accurate distance results in kilometers.
 */
public class HaversineDistanceCalculate {

    // Prevent instantiation
    private HaversineDistanceCalculate() {}

    // Earth's radius in kilometers
    private static final int EARTH_RADIUS_KM = 6371;

    /**
     * Calculates the great-circle distance between two points on the Earth's surface.
     *
     * @param lat1 Latitude of point 1 in decimal degrees
     * @param lon1 Longitude of point 1 in decimal degrees
     * @param lat2 Latitude of point 2 in decimal degrees
     * @param lon2 Longitude of point 2 in decimal degrees
     * @return Distance in kilometers between the two points
     */
    public static double find(double lat1, double lon1, double lat2, double lon2) {
        // Convert degrees to radians
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double a = Math.pow(Math.sin(dLat / 2), 2)
                 + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}
