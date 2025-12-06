package com.aanvi.rideshare.controller;

import com.aanvi.rideshare.dto.CreateRideRequest;
import com.aanvi.rideshare.model.Ride;
import com.aanvi.rideshare.service.RideService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    @Autowired
    private RideService rideService;

    // User creates a ride
    @PostMapping("/rides")
    public ResponseEntity<Ride> createRide(@Valid @RequestBody CreateRideRequest request,
                                           Authentication authentication) {
        String username = authentication.getName();
        Ride ride = rideService.createRide(request, username);
        return ResponseEntity.ok(ride);
    }

    // User gets their own rides
    @GetMapping("/user/rides")
    public ResponseEntity<List<Ride>> getUserRides(Authentication authentication) {
        String username = authentication.getName();
        List<Ride> rides = rideService.getUserRides(username);
        return ResponseEntity.ok(rides);
    }

    // Driver views pending ride requests
    @GetMapping("/driver/rides/requests")
    public ResponseEntity<List<Ride>> getPendingRides() {
        List<Ride> rides = rideService.getPendingRides();
        return ResponseEntity.ok(rides);
    }

    // Driver accepts a ride
    @PostMapping("/driver/rides/{rideId}/accept")
    public ResponseEntity<Ride> acceptRide(@PathVariable String rideId,
                                           Authentication authentication) {
        String driverUsername = authentication.getName();
        Ride ride = rideService.acceptRide(rideId, driverUsername);
        return ResponseEntity.ok(ride);
    }

    // Complete a ride (both user and driver can do this)
    @PostMapping("/rides/{rideId}/complete")
    public ResponseEntity<Ride> completeRide(@PathVariable String rideId,
                                             Authentication authentication) {
        String username = authentication.getName();
        Ride ride = rideService.completeRide(rideId, username);
        return ResponseEntity.ok(ride);
    }
}