package com.pluralsight.repository;

import java.util.List;

import com.pluralsight.model.Ride;

public interface RideRepository {

	List<Ride> getRides();

    Ride createRide(Ride ride);

    Ride getRide(int i);

    Integer countRide();

    void empty();

    Ride updateRide(Ride ride);

    void updateRides(List<Object[]> pairs);

    void deleteRide(Integer id);
}