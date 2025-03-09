package com.example.pulse.workout.log.service;

import com.example.pulse.constants.StatusCodes;
import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.repository.ExerciseRepository;
import com.example.pulse.workout.log.entity.Log;
import com.example.pulse.workout.log.repository.LogRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LogServiceImpl implements LogService {
    private static final Logger log = LoggerFactory.getLogger(LogServiceImpl.class);
    LogRepository logRepository;
    ExerciseRepository exerciseRepository;

    @Override
    public Log findById(int id) throws PulseException {
        Log log = null;
        try {
            log = logRepository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.LOG_FETCHING_FAILED));
        } catch (DataAccessException e) {
            e.getCause();
            throw new PulseException(StatusCodes.LOG_FETCHING_FAILED);
        }
        return log;
    }

    @Override
    public List<Log> findAll() throws PulseException {
        List<Log> logs = new ArrayList<>();
        try {
            logs= logRepository.findAll();
        }catch (DataAccessException e){
            throw new PulseException(StatusCodes.LOG_FETCHING_FAILED);
        }
        return logs;
    }

    @Override
    public Log add(Log log) throws PulseException{
        try {
            Exercise exercise = log.getExercise();
            System.out.println(exercise.toString());
            if(exercise.getId()>0){
                exercise = exerciseRepository.findById(exercise.getId()).orElseThrow(()->new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
                log.setExercise(exercise);
            }
            log = logRepository.save(log);
        }catch (DataAccessException e){
            throw new PulseException(StatusCodes.LOG_ADDING_FAILED);
        }
        return log;
    }

    @Override
    public Log update (int id, Log log) throws PulseException{
        Log existingLog = null;
        try {
           existingLog = logRepository.findById(id).orElseThrow(()-> new PulseException(StatusCodes.LOG_FETCHING_FAILED));
           Exercise exercise = log.getExercise();
           if (exercise!=null){
               if (exercise.getId()>0){
                   exercise = exerciseRepository.findById(exercise.getId()).orElseThrow(()-> new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
               }
           }
           existingLog.setExercise(exercise);
        }catch (DataAccessException e){
            throw new PulseException(StatusCodes.LOG_UPDATE_FAILED);
        }
        return existingLog;
    }

    @Override
    public boolean delete(int id) {
        boolean isDeleteSuccess = false;
        try {
            Log log = logRepository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.LOG_FETCHING_FAILED));
            log.setExercise(null);
            logRepository.delete(log);
        }catch (DataAccessException e){
            throw new PulseException(StatusCodes.LOG_DELETION_FAILED);
        }
        return true;
    }
}
