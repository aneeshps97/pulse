package com.example.pulse.workout.exercise.controller;

import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.service.ExerciseService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    ExerciseService exerciseService;
    ExerciseController(ExerciseService exerciseService){
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public Exercise add(@RequestBody  Exercise exercise){
        return exerciseService.add(exercise);
    }

    @GetMapping("/{exerciseId}")
    public Optional<Exercise> findExerciseById(@PathVariable int exerciseId){
        return exerciseService.findById(exerciseId);
    }

    @PutMapping("/{exerciseId}")
    public Exercise updateExerciseDetails(@PathVariable int exerciseId, @RequestBody Exercise exercise){
        return exerciseService.update(exerciseId,exercise);
    }

    @DeleteMapping("/{exerciseId}")
    public boolean deleteExercise(@PathVariable int exerciseId){
        return exerciseService.delete(exerciseId);
    }
}
