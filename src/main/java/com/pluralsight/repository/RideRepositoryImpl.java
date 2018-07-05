package com.pluralsight.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pluralsight.repository.util.RideRowMapper;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        List<Ride> rides = jdbcTemplate.query("select * from ride", new RideRowMapper());

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

        //jdbcTemplate.update("insert into ride (name, duration) values (?, ?)", ride.getName(), ride.getDuration());
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                PreparedStatement ps = connection.prepareStatement("insert into ride (name, duration) values (?, ?)", new String[]{"id"});
//                ps.setString(1, ride.getName());
//                ps.setInt(2, ride.getDuration());
//                return ps;
//            }
//        }, keyHolder);
//
//        Number id = keyHolder.getKey();

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.setGeneratedKeyName("id");

        Map<String, Object> data = new HashMap<>();
        data.put("name", ride.getName());
        data.put("duration", ride.getDuration());

        List<String> columns = new ArrayList<>();
        columns.add("name");
        columns.add("duration");

        jdbcInsert.setTableName("ride");
        jdbcInsert.setColumnNames(columns);

        Number id = jdbcInsert.executeAndReturnKey(data);

        return getRide(id.intValue());
    }

    @Override
    public Ride getRide(int i) {
        String sql = "select * from ride where id=?";
        Ride ride = jdbcTemplate.queryForObject(sql, new RideRowMapper(), i);
        System.out.println("Ride: " + ride);

        return ride;
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

    @Override
    public Ride updateRide(Ride ride) {
        jdbcTemplate.update("update ride set name=?, duration=? where id=?",
                            ride.getName(), ride.getDuration(), ride.getId());

        return ride;
    }

    @Override
    public void updateRides(List<Object[]> pairs) {
        jdbcTemplate.batchUpdate("update ride set ride_date = ? where id = ?", pairs);
    }

}
