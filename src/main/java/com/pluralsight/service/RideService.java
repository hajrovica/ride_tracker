package com.pluralsight.service;

import java.util.List;

import com.pluralsight.model.Ride;

public interface RideService {

	List<Ride> getRides();

    Ride createRide(Ride ride);

    Integer countRide();

    Ride getRide(int id);

    void empty();

    Ride updateRide(Ride ride);

    void batch();
}