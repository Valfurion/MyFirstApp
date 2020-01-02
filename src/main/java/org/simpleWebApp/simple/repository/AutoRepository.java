package org.simpleWebApp.simple.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AutoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Integer number;

    public List<String> findAllTitles() {
        String sql = "select title from auto.vehicle";
        List autos = jdbcTemplate.queryForList(sql, List.class);
        System.out.println("autos = " + autos);
        return autos;
    }

    public String getAutoById(Integer number) {
        String sql = "select title from auto.vehicle where vehicle_id=(" + number.toString() + ")";
        String auto = jdbcTemplate.queryForObject(sql, String.class);
        System.out.println("auto = " + auto);
        return auto;


    }
    public String createAuto(String auto) {
        String sql = "Insert into auto.vehicle (title) values ('" + auto + "')";
        this.jdbcTemplate.execute(sql);
        System.out.println("auto = " + auto);
        return auto;
    }


    public String updateAuto(Integer id, String auto) {
        String sql = "update auto.vehicle set title = ('" + auto + "') where Vehicle_id = (" + id.toString() + ")";
        jdbcTemplate.execute(sql);
        return getAutoById(id);


    }

    public Boolean deleteAuto(Integer id) {
        String sql = "delete from auto.Vehicle where Vehicle_id = (" + id.toString() + ")";
        jdbcTemplate.execute(sql);
        try {
            getAutoById(id);
        }catch (EmptyResultDataAccessException e){
            return true;
        }
        return false;
    }
}
