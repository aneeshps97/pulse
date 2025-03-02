package com.example.pulse.workout.day.service;

import com.example.pulse.workout.day.entity.Day;
import com.example.pulse.workout.exercise.entity.Exercise;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface DayService {
    public Day add(Day day);
    public Day update(int id,Day day);
    public Optional<Day> findById(int id);
    public boolean delete(int id);
}
