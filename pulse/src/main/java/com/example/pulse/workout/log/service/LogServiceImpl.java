package com.example.pulse.workout.log.service;

import com.example.pulse.constants.StatusCodes;
import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.repository.ExerciseRepository;
import com.example.pulse.workout.log.entity.Log;
import com.example.pulse.workout.log.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LogServiceImpl implements LogService {
    private static final Logger log = LoggerFactory.getLogger(LogServiceImpl.class);
    LogRepository logRepository;
    ExerciseRepository exerciseRepository;

    @Autowired
    LogServiceImpl(LogRepository logRepository, ExerciseRepository exerciseRepository) {
        this.logRepository = logRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public Log findById(int id) throws PulseException{
        return logRepository.findById(id).orElseThrow(()->new PulseException(StatusCodes.LOG_FETCHING_FAILED));
    }

    @Override
    public Log add(Log log) {
        List<Exercise> exercises = log.getExercises();
        List<Exercise> existingExercises = new ArrayList<>();
        if(!exercises.isEmpty()){
            for (Exercise exercise : exercises){
                if (exercise.getId()>0){
                    exercise = exerciseRepository.findById(exercise.getId()).orElseThrow(()-> new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
                    exercise.getLogs().add(log);
                    existingExercises.add(exercise);
                }else {
                    exercise.getLogs().add(log);
                    existingExercises.add(exercise);
                }

            }
            log.getExercises().clear();
            log.setExercises(existingExercises);
        }
        log = logRepository.save(log);
        if(!logRepository.existsById(log.getId())){
            throw new PulseException(StatusCodes.LOG_ADDING_FAILED);
        }
        return log;
    }

    @Override
    public Log update(int id, Log log) {
        Log existingLog = logRepository.findById(id).orElseThrow(()->new PulseException(StatusCodes.LOG_FETCHING_FAILED));
        existingLog.setDate(log.getDate());
        existingLog.setReps(log.getReps());
        existingLog.setWeight(log.getWeight());
        List<Exercise> updatedExercises = new ArrayList<>();
            for (Exercise exercise : log.getExercises()) {
                if (exercise.getId() > 0) {
                    Exercise exisingExercise = exerciseRepository.findById(exercise.getId()).orElseThrow(()->new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
                        if (!exisingExercise.getLogs().contains(existingLog)) {
                            exisingExercise.getLogs().add(existingLog);
                            updatedExercises.add(exisingExercise);
                        }else{
                            updatedExercises.add(exisingExercise);
                        }
                } else {
                    exercise.getLogs().add(log);
                    updatedExercises.add(exercise);
                }
            }
            existingLog.getExercises().clear();
            existingLog.getExercises().addAll(updatedExercises);
            log = logRepository.save(existingLog);
            if(!logRepository.existsById(log.getId())){
                throw new PulseException(StatusCodes.LOG_UPDATION_FAILED);
            }
        return logRepository.save(existingLog);
    }

    @Override
    public boolean delete(int id) {
        Log log = logRepository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.LOG_FETCHING_FAILED));
        log.getExercises().forEach(exercise -> exercise.getLogs().remove(log));
        logRepository.delete(log);
        if(logRepository.existsById(id)){
            throw new PulseException(StatusCodes.LOG_DELETION_FAILED);
        }
        return true;
    }
}
