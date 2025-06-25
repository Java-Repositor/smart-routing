package com.smart_routing.find_shortest_route.service;

public interface DistanceService {

	Object getMapDetails(Long fromPincode, Long toPincode, String mode);

}
