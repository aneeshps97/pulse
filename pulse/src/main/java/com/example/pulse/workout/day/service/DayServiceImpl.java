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
        List<Day> days = new ArrayList<>();
        days.add(day);
        if(day.getExercises()!=null && !day.getExercises().isEmpty()){
            List<Exercise> exercises = new ArrayList<>();
            for(Exercise exercise:day.getExercises()){
                if(exercise.getId()>0){
                    Optional<Exercise> existingExercise = exerciseRepository.findById(exercise.getId());
                    if (existingExercise.isPresent()){
                        existingExercise.get().getDays().add(day);
                        exercises.add(existingExercise.get());
                    }
                }else{
                    exercise.setDays(days);
                    exercises.add(exercise);
                }
            }

            day.setExercises(exercises);
        }
        return dayRepository.save(day);
    }

    @Override
    public Day update(int id, Day day) {
        Optional<Day> existingDay = dayRepository.findById(id);
        List<Exercise> existingExercises = new ArrayList<>();
        List<Day> existingDays = new ArrayList<>();
        if (existingDay.isPresent()){
            existingDay.get().setName(day.getName());
            for (Exercise exercise: existingDay.get().getExercises()){
                if (!exercise.getDays().contains(existingDay.get())){
                    exercise.getDays().add(existingDay.get());
                    existingExercises.add(exercise);
                }else {
                    existingExercises.add(exercise);
                }
            }

           existingDay.get().getExercises().clear();
            existingDay.get().getExercises().addAll(existingExercises);
        }

        return dayRepository.save(existingDay.get());
    }

    @Override
    public Optional<Day> findById(int id) {
        return dayRepository.findById(id);
    }

    @Override
    public boolean delete(int id) {
        Optional<Day> day = dayRepository.findById(id);
        if(day.isPresent()){
            day.get().setPlan(null);

            day.get().getExercises().forEach(exercise -> exercise.getDays().removeIf(day1 -> day1.getId()==id));
            dayRepository.delete(day.get());
        }

        return !dayRepository.existsById(id);
    }
}
