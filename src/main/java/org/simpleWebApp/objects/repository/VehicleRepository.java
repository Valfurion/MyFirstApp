package org.simpleWebApp.objects.repository;

import org.simpleWebApp.objects.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.prefs.AbstractPreferences;

@Repository
public class VehicleRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private AbstractPreferences resultSet;

    public List<Vehicle> findAll() {
        String sql = "select * from auto.vehicle";
        List<Vehicle> auto = jdbcTemplate.query(sql, new VehicleMapper());
        return auto;

    }

    public Vehicle findById(Integer number) {
        String sql = "select * from auto.vehicle where vehicle_id= (" + number.toString() + ")";
        Vehicle auto = jdbcTemplate.queryForObject(sql, new VehicleMapper());
        return auto;
    }

   /* public String createVehicle(String vehicle) {
        String sql = "Insert into auto.vehicle (title) values ('" + vehicle + "')";
        jdbcTemplate.update(sql);
        System.out.println("vehicle = " + vehicle);
        return vehicle;

    }
    */
   public Vehicle createVehicle (Vehicle vehicle){
       String sql = "Insert into auto.vehicle (title) values (?)";
       KeyHolder holder = new GeneratedKeyHolder();
       System.out.println(sql);
       jdbcTemplate.update(psc -> {
           PreparedStatement ps = psc.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
           ps.setString(1, vehicle.getTitle());
           return ps;
       }, holder);

       long vehicleId = (long) holder.getKeys().get("vehicle_id");
       vehicle.setVehicleId(vehicleId);
       return vehicle;
   }


    public Boolean deleteAuto(Integer vehicleId) {
        String sql = "delete from auto.Vehicle where Vehicle_id = ?";
        jdbcTemplate.update(psc ->{
            PreparedStatement ps = psc.prepareStatement(sql);
            ps.setLong(1, vehicleId);
            return ps;
        });
        try {
            findById(vehicleId);
        }
        catch (EmptyResultDataAccessException e){
            return true;
        }
        return false;
    }


    private class VehicleMapper implements RowMapper<Vehicle> {

        @Override
        public Vehicle mapRow(ResultSet resultSet, int i) throws SQLException {
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleId(resultSet.getLong("vehicle_id"));
            vehicle.setTitle(resultSet.getString("title"));
            return vehicle;

        }
    }

}
