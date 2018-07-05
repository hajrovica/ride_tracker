package com.pluralsight.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
//        List<Ride> rides = new ArrayList<>();
        List<Ride> rides = jdbcTemplate.query("select * from ride", new RowMapper<Ride>() {
            @Override
            public Ride mapRow(ResultSet resultSet, int i) throws SQLException {
                //template method pattern - check it
                Ride ride = new Ride();
                ride.setId(resultSet.getInt("id"));
                ride.setName(resultSet.getString("name"));
                ride.setDuration(resultSet.getInt("duration"));
                return ride;
            }

        });

//        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
//
//        for (Map row:rows){
//            System.out.println("Row: " + row);
//            Ride ride = new Ride();
//            ride.setName((String) row.get("name"));
//            ride.setDuration((int)row.get("duration"));
//            rides.add(ride);
//        }

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
