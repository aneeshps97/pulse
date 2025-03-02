package com.example.pulse.workout.exercise.service;

import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.repository.ExerciseRepository;
import jakarta.transaction.Transactional;
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
    @Transactional
    public Exercise update(int id,Exercise exercise) {
        exercise.setId(id);
        return exerciseRepository.save(exercise);
    }

    @Override
    public Optional<Exercise> findById(int id) {
        return exerciseRepository.findById(id);
    }

    @Override
    public boolean delete(int id) {
        Optional<Exercise> exercise =exerciseRepository.findById(id);
        exercise.ifPresent(value -> exerciseRepository.delete(value));
        return !exerciseRepository.existsById(id);
    }
}
