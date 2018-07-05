package com.pluralsight.controller;

import java.util.List;

import com.pluralsight.service.RideService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pluralsight.model.Ride;

import org.junit.Test;

public class RestControllerTest {
    @Autowired
    private RideService rideService;

    @Test(timeout=3000)
    public void testCreateRides() {
        RestTemplate restTemplate = new RestTemplate();
//        System.out.println(rideService.getClass());
//        rideService.empty();
        restTemplate.exchange("http://localhost:8080/empty", HttpMethod.GET, null, String.class);

        Ride ride = new Ride();
        ride.setName("Ride 003");
        ride.setDuration(10);

        ride = restTemplate.postForObject("http://localhost:8080/ride", ride, Ride.class);
        String countRide = restTemplate.exchange("http://localhost:8080/count", HttpMethod.GET, null, String.class).getBody();

//        Integer countRide = rideService.countRide();
        System.out.println("Count: " + countRide);

        Assert.assertEquals(1, Long.parseLong(String.valueOf(countRide)));

    }


	@Test(timeout=3000)
	public void testGetRide() {
		RestTemplate restTemplate = new RestTemplate();

		Ride ride = restTemplate.getForObject("http://localhost:8080/ride/1", Ride.class);
        System.out.println("Ride name: " + ride.getName());
	}

    @Test(timeout=3000)
    public void testUpdateRide() {
        RestTemplate restTemplate = new RestTemplate();

        Ride ride = restTemplate.getForObject("http://localhost:8080/ride/1", Ride.class);
        ride.setDuration(ride.getDuration() + 1);

        restTemplate.put("http://localhost:8080/ride", ride);

        System.out.println("Ride name: " + ride.getName());
        System.out.println("Ride name: " + ride.getDuration());
    }

    @Test(timeout=3000)
    public void testGetRides() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Ride>> ridesResponse = restTemplate.exchange(
                "http://localhost:8080/ride", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Ride>>() {
                });
        List<Ride> rides = ridesResponse.getBody();

        for (Ride ride : rides) {
            System.out.println("Ride name: " + ride.getName());
        }
    }

}
