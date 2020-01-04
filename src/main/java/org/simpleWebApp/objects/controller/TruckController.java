package org.simpleWebApp.objects.controller;

import org.simpleWebApp.objects.entity.Truck;
import org.simpleWebApp.objects.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



//Valfurion
@RestController
@RequestMapping("/trucks")
public class TruckController {
    @Autowired
    private TruckRepository truckRepository;

    @GetMapping
    public List<Truck> getAll() {
        return truckRepository.findAll();
    }

    @GetMapping("/{id}")
    public Truck getById(@PathVariable Long id) {
        return truckRepository.findById(id);
    }

    @PostMapping
    public Truck createTruck(@RequestBody Truck truck) {
        return truckRepository.createTruck(truck);
    }

    @PutMapping("/change/{id}")
    public Truck changeTruck(@PathVariable Long id, @RequestBody Truck truck) {
        truck.setVehicleId(id);
        return truckRepository.changeTruck(truck);

    }

    @DeleteMapping("/{id}")
    public Boolean deleteTruck(@PathVariable Long id) {
        if (id == null) {
            System.out.println("Отсутствует");}
        return truckRepository.deleteTruck(id);
    }
}
