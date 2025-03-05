package com.example.pulse.workout.log.service;

import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.log.entity.Log;
import com.example.pulse.workout.log.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LogServiceImpl implements LogService{
    private static final Logger log = LoggerFactory.getLogger(LogServiceImpl.class);
    LogRepository logRepository;
    @Autowired
    LogServiceImpl(LogRepository logRepository){
        this.logRepository = logRepository;
    }
    @Override
    public Optional<Log> findById(int id) {
        return logRepository.findById(id);
    }

    @Override
    public Log add(Log log) {
        return logRepository.save(log);
    }

    @Override
    public Log update(int id, Log log) {
        log.setId(id);
        return logRepository.save(log);
    }

    @Override
    public boolean delete(int id) {
        Optional<Log> log = logRepository.findById(id);
        if (log.isPresent()){
            log.get().getExercises().forEach(exercise -> exercise.getLogs().remove(log.get()));
            logRepository.delete(log.get());
        }
        return !logRepository.existsById(id);
    }
}
