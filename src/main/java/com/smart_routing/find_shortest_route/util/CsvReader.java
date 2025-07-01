package com.smart_routing.find_shortest_route.util;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.bean.CsvToBeanBuilder;
import com.smart_routing.find_shortest_route.model.Location;

/**
 * Utility component for reading and parsing location data from a CSV file.
 */
@Component
public class CsvReader {

    private static final String FILE_PATH = "src/main/resources/sameple-data.csv";

    private List<Location> locationData = new ArrayList<>();

    public CsvReader() {
        loadLocations();
    }

    /**
     * Loads location data from CSV if not already loaded.
     * Filters out invalid rows and parses coordinates.
     *
     * @return List of valid Location objects
     */
    public List<Location> loadLocations() {
        if (locationData.isEmpty()) {
            try (FileReader reader = new FileReader(FILE_PATH)) {

                List<Location> parsed = new CsvToBeanBuilder<Location>(reader)
                        .withType(Location.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build()
                        .parse();

                // Filter and prepare coordinates
                locationData = parsed.stream()
                        .filter(loc -> loc.getPincode() != null && !loc.getPincode().isBlank())
                        .peek(Location::computeParsedCoordinates).toList();
                       
                System.out.println("Loaded " + locationData.size() + " valid location records from CSV.");

            } catch (Exception e) {
                System.err.println("Error reading CSV: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return locationData;
    }

    /**
     * Returns a defensive copy of the loaded location data.
     *
     * @return List of Location objects
     */
    public List<Location> getLocationData() {
        return new ArrayList<>(locationData);
    }

    /**
     * Parses a coordinate string (with optional compass suffix like N/S/E/W).
     *
     * @param coord Raw coordinate string
     * @return Parsed coordinate as double (negative if S/W)
     */
    public static double parseCoordinate(String coord) {
        if (coord == null || coord.isBlank()) return 0.0;

        coord = coord.trim().toUpperCase();

        try {
            double value = Double.parseDouble(coord.replaceAll("[^0-9.]", ""));
            if (coord.endsWith("S") || coord.endsWith("W")) {
                return -value;
            }
            return value;
        } catch (NumberFormatException e) {
            System.err.println("Invalid coordinate format: " + coord);
            return 0.0;
        }
    }
}
