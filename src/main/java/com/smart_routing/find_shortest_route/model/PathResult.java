package com.smart_routing.find_shortest_route.model;

import java.util.List;

public class PathResult {

	private final List<Location> path;
	private final double totalDistance;

	public PathResult(List<Location> path, double totalDistance) {
		this.path = path;
		this.totalDistance = totalDistance;
	}

	public List<Location> getPath() {
		return path;
	}

	public double getTotalDistance() {
		return totalDistance;
	}
}