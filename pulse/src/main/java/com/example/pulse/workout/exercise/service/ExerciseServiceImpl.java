package com.example.pulse.workout.exercise.service;

import com.example.pulse.constants.StatusCodes;
import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.day.entity.Day;
import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.repository.ExerciseRepository;
import com.example.pulse.workout.log.entity.Log;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    ExerciseRepository exerciseRepository;

    @Override
    public Exercise add(Exercise exercise) throws PulseException {
        Exercise savedExercise = null;
        try {
            savedExercise = exerciseRepository.save(exercise);
        } catch (DataAccessException e) {
            e.getCause();
            throw new PulseException(StatusCodes.EXERCISE_ADDING_FAILED);
        }
        return savedExercise;
    }

    @Override
    @Transactional
    public Exercise update(int id, Exercise exercise) throws PulseException {
        Exercise updatedExercise = null;
        try {
            updatedExercise = exerciseRepository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
            updatedExercise.setName(exercise.getName());
            updatedExercise.setComment(exercise.getComment());
            updatedExercise = exerciseRepository.save(updatedExercise);
        } catch (DataAccessException e) {
            e.getCause();
            throw new PulseException(StatusCodes.EXERCISE_UPDATE_FAILED);
        }
        return updatedExercise;
    }

    @Override
    public Exercise findById(int id) throws PulseException {
        Exercise exercise = null;
        try {
            exercise = exerciseRepository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
        } catch (DataAccessException e) {
            e.getCause();
            throw new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED);
        }
        return exercise;
    }

    @Override
    public List<Exercise> findAll() throws PulseException {
        List<Exercise> exercises = new ArrayList<>();
        try {
            exercises = exerciseRepository.findAll();
        } catch (DataAccessException e) {
            throw new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED);
        }
        return exercises;
    }

    @Override
    public boolean delete(int id) throws PulseException {
        boolean isDeletionSuccess = false;
        try {
            Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
            for (Day day : exercise.getDays()) {
                day.getExercises().remove(exercise);
            }
            exercise.getDays().clear();
            exerciseRepository.delete(exercise);
            if (exerciseRepository.existsById(id)) {
                throw new PulseException(StatusCodes.EXERCISE_DELETION_FAILED);
            } else {
                isDeletionSuccess = true;
            }
        } catch (DataAccessException e) {
            e.getCause();
            throw new PulseException(StatusCodes.LOG_FETCHING_FAILED);
        }
        return isDeletionSuccess;
    }
}
