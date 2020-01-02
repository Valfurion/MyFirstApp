package org.simpleWebApp.objects.controller;

import org.simpleWebApp.objects.entity.Vehicle;
import org.simpleWebApp.objects.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/newAutoObject")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping("/getAll")
    public List<Vehicle> getAllAuto() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles;
    }

    @GetMapping("/get/{id}")
    public Vehicle getAuto (@PathVariable Integer id) {
        Vehicle vehicle = vehicleRepository.findById(id);
        return vehicle;
    }

    @PostMapping
    public Vehicle createAuto(@RequestBody String vehicleTitle) {
        Vehicle vehicle = new Vehicle();
        vehicle.setTitle(vehicleTitle);
        return vehicleRepository.createVehicle(vehicle);

    }

    @DeleteMapping("/{id}")
    public Boolean deleteAuto(@PathVariable Integer id) {
        if (id == null) System.out.println("введите id");
        return vehicleRepository.deleteAuto(id);

    }
}
