package com.smart_routing.find_shortest_route.serviceImplementation;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_routing.find_shortest_route.algorithms.Dijkstra;
import com.smart_routing.find_shortest_route.graph.BruteForceGraphBuilder;
import com.smart_routing.find_shortest_route.graph.CustomGraphBuilder;
import com.smart_routing.find_shortest_route.graph.Graph;
import com.smart_routing.find_shortest_route.graph.GraphBuilder;
import com.smart_routing.find_shortest_route.graph.KDTreeGraphBuilder;
import com.smart_routing.find_shortest_route.model.Location;
import com.smart_routing.find_shortest_route.service.DistanceService;
import com.smart_routing.find_shortest_route.util.CsvReader;

/**
 * Service implementation for calculating the shortest path between two
 * pincodes.
 */
@Service
public class DistanceServiceImplementation implements DistanceService {

	@Autowired
	private CsvReader csvReader;

	/**
	 * Returns the shortest path between two pincodes using either Brute-force or
	 * KD-Tree graph building.
	 *
	 * @param fromPincode Source pincode
	 * @param toPincode   Destination pincode
	 * @param mode        Mode: "Brute" for brute-force, anything else uses KD-Tree
	 * @return PathResult containing the shortest path and distance
	 */
	@Override
	public Object getMapDetails(Long fromPincode, Long toPincode, String mode) {
		try {
			List<Location> locations = csvReader.getLocationData();

			// Select GraphBuilder strategy
			GraphBuilder builder = "brute".equalsIgnoreCase(mode) ? new BruteForceGraphBuilder()
					: new CustomGraphBuilder();

			// Build the graph with N-nearest neighbors
			Graph graph = builder.build(locations, 30);

			// Resolve source and destination
			Location source = findLocationByPincode(locations, fromPincode);
			Location destination = findLocationByPincode(locations, toPincode);

			if (source == null || destination == null) {
				throw new IllegalArgumentException("Source or Destination pincode not found in dataset.");
			}

			// Calculate shortest path
			return Dijkstra.findShortestPath(graph, source, destination);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Error while computing path: " + ex.getMessage(), ex);
		}
	}

	/**
	 * Utility method to fetch a Location by pincode from the dataset.
	 */
	private Location findLocationByPincode(List<Location> data, Long pincode) {
		return data.stream().filter(loc -> Objects.equals(loc.getPincode(), String.valueOf(pincode))).findFirst()
				.orElse(null);
	}
}
