package org.simpleWebApp.objects.repository;

import org.simpleWebApp.objects.entity.Engine;
import org.springframework.beans.factory.annotation.Autowired;
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

@Repository
public class EngineRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Engine findById(Long engineId) {
        String sql = "select * from auto.engine where engine_id= (" + engineId.toString() + ")";
        Engine engn = jdbcTemplate.queryForObject(sql, new EngineMapper());
        return engn;
    }

    public Engine create(Engine engine) {
        String sql = "Insert into auto.engine (title, volume) values (?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        System.out.println(sql);
        jdbcTemplate.update(psc -> {
            PreparedStatement ps = psc.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, engine.getTitle());
            ps.setString(2, engine.getVolume());
            return ps;
        }, holder);
        long engineId = (long) holder.getKeys().get("engine_id");
        engine.setEngineId(engineId);
        return engine;
    }

    public Engine findByTitleAndVolume(String title, String volume) {
        String sql = "select * from auto.engine where title = '" + title + "' and volume = '" + volume + "' ";
        Engine engn = jdbcTemplate.queryForObject(sql, new EngineMapper());
        return engn;
    }

    public List<Engine> findAll() {
        String sql = "select * from auto.engine";
        List<Engine> engine = jdbcTemplate.query(sql, new EngineMapper());
        return engine;
    }

    public Engine getById(Long id) {
        String sql = "select * from auto.engine where engine_id = " + id;
        return jdbcTemplate.queryForObject(sql, new EngineMapper());

    }


    private class EngineMapper implements RowMapper<Engine> {

        @Override
        public Engine mapRow(ResultSet resultSet, int i) throws SQLException {
            Engine engine = new Engine();
            engine.setEngineId(resultSet.getLong("engine_id"));
            engine.setTitle(resultSet.getString("title"));
            engine.setVolume(resultSet.getString("volume"));
            return engine;
        }
    }
}
