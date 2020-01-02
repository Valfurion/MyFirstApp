package org.simpleWebApp.objects.repository;

import org.simpleWebApp.objects.entity.Engine;
import org.simpleWebApp.objects.entity.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import sun.swing.StringUIClientPropertyKey;

import javax.xml.ws.Holder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class TruckRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EngineRepository engineRepository;
//Отображение всех грузовиков
    public List<Truck> findAll() {
        String sql = "select * from auto.truck";
        System.out.println(sql);
        List<Truck> trucks = jdbcTemplate.query(sql, new TruckMapper());
        for (Truck truck : trucks) {
            Engine engine = engineRepository.findById(truck.getEngine().getEngineId());
            truck.setEngine(engine);
        }

        return trucks;

    }
//Отображение грузовиков по их Id
    public Truck findById(Long number) {
        String sql = "select * from auto.truck where truck_id = (" + number.toString() + ")";
        System.out.println(sql);
        Truck truck = jdbcTemplate.queryForObject(sql, new TruckMapper());
        Engine engine = engineRepository.findById(truck.getEngine().getEngineId());
        truck.setEngine(engine);
        return truck;
    }
//Создание грузовика
    public Truck createTruck(Truck truck) {
        Engine engine = getOrCreateEngine(truck);
        truck.setEngine(engine);
        String sql = "Insert into auto.truck (title, engine_id) values (?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        System.out.println(sql);
        jdbcTemplate.update(psc -> {
            PreparedStatement ps = psc.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, truck.getTitle());
            ps.setLong(2, truck.getEngine().getEngineId());
            return ps;
        }, holder);
        long truckId = (long) holder.getKeys().get("truck_id");
        truck.setVehicleId(truckId);
        return truck;

    }

    private Engine getOrCreateEngine(Truck truck) {
        Engine engine = truck.getEngine();
        if (engine.getTitle() != null && engine.getVolume() != null) {
            try {
                engine = engineRepository.findByTitleAndVolume(engine.getTitle(), engine.getVolume());
            } catch (EmptyResultDataAccessException e) {
                engine = engineRepository.create(engine);
            }

        }
        return engine;
    }
//Внесение изменений в грузовик
    public Truck changeTruck(Truck truck) {
        Engine engine = getOrCreateEngine(truck);
        truck.setEngine(engine);
        String sql = "update auto.truck set title = ?, engine_id = ? where truck_id = ?";
        jdbcTemplate.update(psc -> {
                    PreparedStatement ps = psc.prepareStatement(sql);
                    ps.setString(1, truck.getTitle());
                    ps.setLong(2, truck.getEngine().getEngineId());
                    ps.setLong(3, truck.getVehicleId());
                    return ps;
                }
        );
        return findById(truck.getVehicleId());


    }
//Удаление грузовика по его Id
    public Boolean deleteTruck(Long number) {
        String sql = "delete from auto.truck where truck_id = ?";
        jdbcTemplate.update(psc -> {
            PreparedStatement ps = psc.prepareStatement(sql);
            ps.setLong(1, number);
            return ps;
        });
        try {
            findById(number);
        } catch (EmptyResultDataAccessException e) {
            return true;
        }
        return false;
    }

    private class TruckMapper implements RowMapper<Truck> {

        @Override
        public Truck mapRow(ResultSet resultSet, int i) throws SQLException {
            Truck truck = new Truck();
            truck.setVehicleId(resultSet.getLong("truck_id"));
            truck.setTitle(resultSet.getString("title"));
            Engine engine = new Engine();
            engine.setEngineId(resultSet.getLong("engine_id"));
            truck.setEngine(engine);

            return truck;
        }
    }
}
