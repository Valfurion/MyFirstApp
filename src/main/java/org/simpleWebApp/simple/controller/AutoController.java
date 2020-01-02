package org.simpleWebApp.simple.controller;

import org.simpleWebApp.simple.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auto")
public class AutoController {
    private List<String> autos = new ArrayList<>();

    @Autowired
    private AutoRepository autoRepository;

    //возвращаю список авто
    @GetMapping("/get/all")
    public String getAll() {
        List<String> autos = autoRepository.findAllTitles();
        return autos.toString();
    }

    // возвращаю один автомобиль из списка по порядковому номеру
    @GetMapping("/get/{number}")
    public String getByNumber(@PathVariable Integer number) {
        return autoRepository.getAutoById(number);
    }

    // добавление авто
    @PostMapping
    public String createAuto(@RequestBody String auto) {
        return autoRepository.createAuto(auto);
    }

    // изменение авто
    @PutMapping("/{id}")
    public String put(@PathVariable Integer id, @RequestBody String auto) {
        if (auto == null) System.out.println("Введите название авто");
        return autoRepository.updateAuto(id, auto);

    }

    @DeleteMapping("/{id}")
    public Boolean deleteAuto(@PathVariable Integer id) {
        if (id == null) System.out.println("Введите id");
        return autoRepository.deleteAuto(id);

    }


}