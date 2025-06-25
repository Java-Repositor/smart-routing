package com.smart_routing.find_shortest_route.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smart_routing.find_shortest_route.model.Edge;
import com.smart_routing.find_shortest_route.model.Location;

import lombok.Data;

@Data
public class Graph {

	private Map<Location, List<Edge>> adjacencyList = new HashMap<>();

	public void addLocation(Location location) {
		adjacencyList.putIfAbsent(location, new ArrayList<>());
	}

	public void addEdge(Location from, Location to, double distance) {
		adjacencyList.get(from).add(new Edge(to, distance));
	}

	public List<Edge> getEdges(Location from) {
		return adjacencyList.getOrDefault(from, new ArrayList<>());
	}
	
	public boolean contains(Location location) {
        return adjacencyList.containsKey(location);
    }

    public int size() {
        return adjacencyList.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Graph:\n");
        for (Map.Entry<Location, List<Edge>> entry : adjacencyList.entrySet()) {
            sb.append(entry.getKey().getPincode()).append(" -> ");
            for (Edge edge : entry.getValue()) {
                sb.append(edge.getDestination().getPincode())
                  .append("(").append(edge.getDistance()).append(" km), ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
