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
                        existingExercise.get().setDays(days);
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
        if (existingDay.isPresent()){
           existingDay.get().setName(day.getName());
           //find excercises
           List<Exercise> exercises = day.getExercises();
           //update the exercises and save that to the day entity which is existing day
           List<Exercise> updatedExercises = new ArrayList<>();
           for (Exercise exercise: day.getExercises()){
               if(exercise.getId()>0){
                   //this means that exercises already exists in the db
                   //check the day for that corresponding exercise and if it is not there update it
                   Optional<Exercise> existingExercise = exerciseRepository.findById(exercise.getId());
                   if(existingExercise.isPresent()){
                       //the data exists in the db
                       if(!existingExercise.get().getDays().contains(existingDay.get())){
                           existingExercise.get().getDays().add(existingDay.get());
                       }
                       //now we are creating a list to save to day.getExercises
                       updatedExercises.add(exercise);
                   }
               }else {
                       //consider it as a new exercise
                       exercise.getDays().add(existingDay.get());
                       existingDay.get().getExercises().add(exerciseRepository.save(exercise));
                       updatedExercises.add(exercise);
               }
           }
           //remove every exercises other than this updated exercises from the existing day
            existingDay.get().getExercises().removeIf(exercise -> !updatedExercises.contains(exercise));
            existingDay.get().setExercises(updatedExercises);

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
