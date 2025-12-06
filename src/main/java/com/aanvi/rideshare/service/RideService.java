package com.aanvi.rideshare.service;

import com.aanvi.rideshare.dto.CreateRideRequest;
import com.aanvi.rideshare.exception.BadRequestException;
import com.aanvi.rideshare.exception.NotFoundException;
import com.aanvi.rideshare.model.Ride;
import com.aanvi.rideshare.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    // User creates a ride request
    public Ride createRide(CreateRideRequest request, String userId) {
        Ride ride = new Ride();
        ride.setUserId(userId);
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());
        ride.setStatus("REQUESTED");

        return rideRepository.save(ride);
    }

    // Get all pending ride requests (for drivers)
    public List<Ride> getPendingRides() {
        return rideRepository.findByStatus("REQUESTED");
    }

    // Driver accepts a ride
    public Ride acceptRide(String rideId, String driverId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!ride.getStatus().equals("REQUESTED")) {
            throw new BadRequestException("Ride is not available for acceptance");
        }

        ride.setDriverId(driverId);
        ride.setStatus("ACCEPTED");

        return rideRepository.save(ride);
    }

    // Complete a ride
    public Ride completeRide(String rideId, String userId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!ride.getStatus().equals("ACCEPTED")) {
            throw new BadRequestException("Only accepted rides can be completed");
        }

        // Verify the user is either the passenger or the driver
        if (!ride.getUserId().equals(userId) && !ride.getDriverId().equals(userId)) {
            throw new BadRequestException("You are not authorized to complete this ride");
        }

        ride.setStatus("COMPLETED");

        return rideRepository.save(ride);
    }

    // Get user's own rides
    public List<Ride> getUserRides(String userId) {
        return rideRepository.findByUserId(userId);
    }

    // Get driver's rides
    public List<Ride> getDriverRides(String driverId) {
        return rideRepository.findByDriverId(driverId);
    }
}