package com.example.pulse.workout.exercise.service;

import com.example.pulse.workout.exercise.entity.Exercise;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ExerciseService {
    public Exercise add(Exercise exercise) throws Exception;
    public Exercise update(int id,Exercise exercise);
    public Exercise findById(int id);
    public boolean delete(int id);
}
