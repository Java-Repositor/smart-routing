package com.smart_routing.find_shortest_route.graph;

import java.util.List;

import com.harium.storage.kdtree.KeyDuplicateException;
import com.harium.storage.kdtree.KeySizeException;
import com.smart_routing.find_shortest_route.model.Location;

public interface GraphBuilder {

    Graph build(List<Location> locations, int neighborsCount) throws KeySizeException, KeyDuplicateException;

}
