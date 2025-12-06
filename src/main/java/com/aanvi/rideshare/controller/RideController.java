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
        Ride ride = rideService.createRide