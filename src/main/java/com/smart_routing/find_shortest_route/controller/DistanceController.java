package com.smart_routing.find_shortest_route.controller;

import com.smart_routing.find_shortest_route.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller to expose endpoints for calculating the shortest delivery
 * route between two Indian pincodes.
 */
@RestController
@RequestMapping("/api/v1")
public class DistanceController {

	private final DistanceService distanceService;

	/**
	 * Constructor-based injection for better testability and immutability.
	 */
	@Autowired
	public DistanceController(DistanceService distanceService) {
		this.distanceService = distanceService;
	}

	/**
	 * Endpoint to retrieve the shortest route between two pincodes.
	 *
	 * @param fromPincode Source pincode
	 * @param toPincode   Destination pincode
	 * @param mode        Optional mode (e.g., 'brute' or 'test' for KD-Tree)
	 * @return ResponseEntity containing path details and distance
	 */
	@GetMapping("/find/best-route/{fromPincode}/{toPincode}")
	public ResponseEntity<Object> getMapDetails(@PathVariable("fromPincode") Long fromPincode,
			@PathVariable("toPincode") Long toPincode, @RequestParam(name = "mode", required = false) String mode) {

		Object data = distanceService.getMapDetails(fromPincode, toPincode, mode);
		return ResponseEntity.ok(data);
	}
}
