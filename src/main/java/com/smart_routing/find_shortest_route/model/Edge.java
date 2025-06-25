package com.smart_routing.find_shortest_route.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Edge {

	private Location destination;
	private double distance;

	public Edge(Location destination, double distance) {
		super();
		this.destination = destination;
		this.distance = distance;
	}

	public Location getDestination() {
		return destination;
	}

	public void setDestination(Location destination) {
		this.destination = destination;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}
