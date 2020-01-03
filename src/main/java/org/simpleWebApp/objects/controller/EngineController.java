package org.simpleWebApp.objects.controller;

import org.simpleWebApp.objects.entity.Engine;
import org.simpleWebApp.objects.repository.EngineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/engine")
public class EngineController {
    @Autowired
    EngineRepository engineRepository;

    @GetMapping("/getAll")
    public List<Engine> findAll (){
        List<Engine> engines = engineRepository.findAll();
        return engines;
    }
}
