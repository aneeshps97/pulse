package com.example.pulse.workout.log.controller;

import com.example.pulse.workout.log.entity.Log;
import com.example.pulse.workout.log.service.LogService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/log")
public class logController {

    LogService logService;
    logController(LogService logService){
        this.logService = logService;
    }

    @PostMapping
    public Log addLog(@RequestBody Log log){
        return logService.add(log);
    }

    @PutMapping("/{id}")
    public Log updateLog(@PathVariable int id, @RequestBody Log log){
        return logService.update(id,log);
    }

    @GetMapping("/{id}")
    public Optional<Log> findLogById(@PathVariable int id){
        return logService.findById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteLog(@PathVariable int id){
        return logService.delete(id);
    }
}
