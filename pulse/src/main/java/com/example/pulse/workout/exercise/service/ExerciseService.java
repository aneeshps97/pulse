package com.example.pulse.workout.exercise.service;

import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.exercise.entity.Exercise;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ExerciseService {
    public Exercise add(Exercise exercise) throws PulseException;
    public Exercise update(int id,Exercise exercise) throws PulseException ;
    public Exercise findById(int id) throws PulseException;
    public List<Exercise> findAll() throws PulseException;
    public boolean delete(int id) throws PulseException;
}
