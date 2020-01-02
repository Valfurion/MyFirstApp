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

    @GetMapping("/get/{number}")
    public Truck getById(@PathVariable Long number) {
        Truck truck = truckRepository.findById(number);
        return truck;
    }

    @PostMapping
    public Truck createTruck(@RequestBody Truck truck) {
        return truckRepository.createTruck(truck);
    }

    @PutMapping("/change/{number}")
    public Truck changeTruck(@PathVariable Long number, @RequestBody Truck truck) {
        truck.setVehicleId(number);
        return truckRepository.changeTruck(truck);

    }

    @DeleteMapping("/{number}")
    public Boolean deleteTruck(@PathVariable Long number) {
        if (number == null) {
            System.out.println("Отсутствует");}
        return truckRepository.deleteTruck(number);
    }
}
