package com.example.pulse.workout.day.service;

import com.example.pulse.workout.day.entity.Day;
import com.example.pulse.workout.day.repository.DayRepository;
import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.repository.ExerciseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class DayServiceImpl implements DayService{
    DayRepository dayRepository;
    ExerciseRepository exerciseRepository;
    DayServiceImpl(DayRepository dayRepository,ExerciseRepository exerciseRepository){
        this.dayRepository = dayRepository;
        this.exerciseRepository = exerciseRepository;
    }
    @Override
    @Transactional
    public Day add(Day day) {
        if(day.getExercises()!=null && !day.getExercises().isEmpty()){
            List<Exercise> exercises = new ArrayList<>();

                for(Exercise exercise:day.getExercises()){
                    if(exercise.getId()>0){
                        Optional<Exercise> existingExercise = exerciseRepository.findById(exercise.getId());
                        if (existingExercise.isPresent()){
                            existingExercise.get().setDay(day);
                            exercises.add(existingExercise.get());
                        }
                    }else{
                        exercise.setDay(day);
                        exercises.add(exercise);
                    }
                }

            day.setExercises(exercises);
        }
        return dayRepository.save(day);
    }

    @Override
    public Day update(int id, Day day) {
      day.setId(id);
        if(day.getExercises()!=null && !day.getExercises().isEmpty()){
            List<Exercise> exercises = new ArrayList<>();

            for(Exercise exercise:day.getExercises()){
                if(exercise.getId()>0){
                    Optional<Exercise> existingExercise = exerciseRepository.findById(exercise.getId());
                    if (existingExercise.isPresent()){
                        existingExercise.get().setDay(day);
                        exercises.add(existingExercise.get());
                    }
                }else{
                    exercise.setDay(day);
                    exercises.add(exercise);
                }
            }

            day.setExercises(exercises);
        }
        return dayRepository.save(day);
    }

    @Override
    public Optional<Day> findById(int id) {
        return dayRepository.findById(id);
    }

    @Override
    public boolean delete(int id) {
        if (dayRepository.findById(id).isPresent()){
            dayRepository.delete(dayRepository.findById(id).get());
        }
        return !dayRepository.existsById(id);
    }
}
