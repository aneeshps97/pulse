package com.example.pulse.workout.day.service;

import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.day.entity.Day;
import com.example.pulse.workout.exercise.entity.Exercise;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface DayService {
    public Day add(Day day) throws PulseException;
    public Day update(int id,Day day) throws PulseException;
    public Day findById(int id) throws PulseException;
    public List<Day> findAll()throws PulseException;
    public boolean delete(int id) throws PulseException;
}
