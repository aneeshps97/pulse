package com.example.pulse.workout.log.service;

import com.example.pulse.workout.log.entity.Log;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface LogService {
    public Log findById(int id);
    public Log add(Log log);
    public Log update(int id,Log log);
    public boolean delete(int id);
}
