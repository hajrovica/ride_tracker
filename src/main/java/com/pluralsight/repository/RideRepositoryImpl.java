package com.pluralsight.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
	public List<Ride> getRides() {
//		Ride ride = new Ride();
//		ride.setName("Corner Canyon");
//		ride.setDuration(120);
//		List <Ride> rides = new ArrayList<>();
//		rides.add(ride);
        String sql = "select * from ride";
        List<Ride> rides = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map row:rows){
            Ride ride = new Ride();
            ride.setName((String) row.get("name"));
            ride.setDuration((int)row.get("duration"));
            rides.add(ride);
        }

		return rides;
	}

    @Override
    public Ride createRide(Ride ride) {

        jdbcTemplate.update("insert into ride (name, duration) values (?, ?)", ride.getName(), ride.getDuration());

        return null;
    }

    @Override
    public Integer countRide() {
        String sql = "SELECT count(*) FROM RIDE";
        int count;
        count = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println("Num rows: " + count);

        return  count;
    }

    @Override
    public void empty() {
        String sql = "TRUNCATE TABLE ride";
        jdbcTemplate.execute(sql);
    }

}
