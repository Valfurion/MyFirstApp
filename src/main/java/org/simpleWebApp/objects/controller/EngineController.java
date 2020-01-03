package org.simpleWebApp.objects.controller;

import org.simpleWebApp.objects.entity.Engine;
import org.simpleWebApp.objects.repository.EngineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/engine")
public class EngineController {
    @Autowired
    private EngineRepository engineRepository;

    @GetMapping("/getAll")
    public List<Engine> findAllEngines (){
        return engineRepository.findAll();
    }
}
