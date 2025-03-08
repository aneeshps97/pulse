package com.example.pulse.workout.exercise.service;

import com.example.pulse.constants.StatusCodes;
import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.day.entity.Day;
import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.repository.ExerciseRepository;
import com.example.pulse.workout.log.entity.Log;
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
    public Exercise add(Exercise exercise) throws PulseException {
           Exercise savedExercise = exerciseRepository.save(exercise);
           if (!exerciseRepository.existsById(savedExercise.getId())){
               throw new PulseException(StatusCodes.EXERCISE_ADDING_FAILED);
           }else {
               return savedExercise;
           }
    }

    @Override
    @Transactional
    public Exercise update(int id,Exercise exercise) throws PulseException{
        Exercise updatedExercise = exerciseRepository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
        updatedExercise.setName(exercise.getName());
        updatedExercise.setComment(exercise.getComment());
        updatedExercise = exerciseRepository.save(updatedExercise);
        if (!exerciseRepository.existsById(updatedExercise.getId())){
            throw new PulseException(StatusCodes.EXERCISE_UPDATE_FAILED);
        }else {
            return updatedExercise;
        }
    }

    @Override
    public Exercise findById(int id) throws PulseException{
        return exerciseRepository.findById(id).orElseThrow(()->new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
    }

    @Override
    public boolean delete(int id) throws PulseException{
        Exercise exercise =exerciseRepository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
            for (Day day: exercise.getDays()){
                day.getExercises().remove(exercise);
            }
            exercise.getDays().clear();

            for (Log log : exercise.getLogs()){
                log.getExercises().remove(exercise);
            }
            exercise.getLogs().clear();
            exerciseRepository.delete(exercise);
            if (exerciseRepository.existsById(id)){
                throw new PulseException(StatusCodes.EXERCISE_DELETION_FAILED);
            }else {
                return true;
            }
    }
}
