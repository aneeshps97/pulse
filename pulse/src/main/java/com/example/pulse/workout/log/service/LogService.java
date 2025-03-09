package com.example.pulse.workout.log.service;

import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.log.entity.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface LogService {
    public Log findById(int id) throws PulseException;
    public List<Log> findAll() throws PulseException;
    public Log add(Log log) throws PulseException;
    public Log update(int id,Log log) throws PulseException;
    public boolean delete(int id) throws PulseException;
}
