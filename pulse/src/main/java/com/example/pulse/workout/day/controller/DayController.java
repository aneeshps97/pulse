package com.example.pulse.workout.day.controller;

import com.example.pulse.workout.day.entity.Day;
import com.example.pulse.workout.day.service.DayService;
import com.example.pulse.workout.exercise.entity.Exercise;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/day")
public class DayController {
    DayService dayService;
    DayController(DayService dayService){
        this.dayService = dayService;
    }

    @PostMapping
    public Day addDay(@RequestBody Day day){
        return dayService.add(day);
    }

    @PutMapping("/{id}")
    public Day updateDay(@PathVariable int id, @RequestBody Day day){
        return dayService.update(id,day);
    }

    @GetMapping("/{id}")
    public Optional<Day> findDayById(@PathVariable int id){
        return dayService.findById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteDay(@PathVariable int id){
        return dayService.delete(id);
    }
}
