package com.example.pulse.workout.exercise.service;

import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService{
    ExerciseRepository exerciseRepository;
    @Autowired
    ExerciseServiceImpl(ExerciseRepository exerciseRepository){
        this.exerciseRepository = exerciseRepository;
    }
    @Override
    public Exercise add(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public Exercise update(int exerciseId,Exercise exercise) {
        exercise.setId(exerciseId);
        return exerciseRepository.save(exercise);
    }

    @Override
    public Optional<Exercise> findById(int exerciseId) {
        return exerciseRepository.findById(exerciseId);
    }

    @Override
    public boolean delete(int exerciseId) {
        Optional<Exercise> exercise =exerciseRepository.findById(exerciseId);
        exercise.ifPresent(value -> exerciseRepository.delete(value));
        return !exerciseRepository.existsById(exerciseId);
    }
}
