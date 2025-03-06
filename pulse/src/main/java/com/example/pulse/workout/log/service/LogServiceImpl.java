package com.example.pulse.workout.log.service;

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
    public Optional<Log> findById(int id) {
        return logRepository.findById(id);
    }

    @Override
    public Log add(Log log) {
        List<Exercise> exercises = log.getExercises();
        List<Exercise> existingExercises = new ArrayList<>();
        if(!exercises.isEmpty()){
            for (Exercise exercise : exercises){
                if (exercise.getId()>0){
                    exercise = exerciseRepository.findById(exercise.getId()).get();
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
        return logRepository.save(log);
    }

    @Override
    public Log update(int id, Log log) {
        Optional<Log> existingLog = logRepository.findById(id);
        existingLog.get().setDate(log.getDate());
        existingLog.get().setReps(log.getReps());
        existingLog.get().setWeight(log.getWeight());
        List<Exercise> updatedExercises = new ArrayList<>();
        if (existingLog.isPresent()) {
            for (Exercise exercise : log.getExercises()) {
                if (exercise.getId() > 0) {
                    Optional<Exercise> exisingExercise = exerciseRepository.findById(exercise.getId());
                    if (exisingExercise.isPresent()) {
                        if (!exisingExercise.get().getLogs().contains(existingLog.get())) {
                            exisingExercise.get().getLogs().add(existingLog.get());
                            updatedExercises.add(exisingExercise.get());
                        }else{
                            updatedExercises.add(exisingExercise.get());
                        }
                    }

                } else {
                    exercise.getLogs().add(log);
                    updatedExercises.add(exercise);
                }
            }
            existingLog.get().getExercises().clear();
            existingLog.get().getExercises().addAll(updatedExercises);
        }
        return logRepository.save(existingLog.get());
    }

    @Override
    public boolean delete(int id) {
        Optional<Log> log = logRepository.findById(id);
        if (log.isPresent()) {
            log.get().getExercises().forEach(exercise -> exercise.getLogs().remove(log.get()));
            logRepository.delete(log.get());
        }
        return !logRepository.existsById(id);
    }
}
