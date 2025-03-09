package com.example.pulse.workout.day.service;

import com.example.pulse.constants.StatusCodes;
import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.day.entity.Day;
import com.example.pulse.workout.day.repository.DayRepository;
import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.repository.ExerciseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class DayServiceImpl implements DayService{
    DayRepository dayRepository;
    ExerciseRepository exerciseRepository;
    @Override
    @Transactional
    public Day add(Day day) throws PulseException {
        List<Day> days = new ArrayList<>();
        days.add(day);
        if(day.getExercises()!=null && !day.getExercises().isEmpty()){
            List<Exercise> exercises = new ArrayList<>();
            for(Exercise exercise:day.getExercises()){
                if(exercise.getId()>0){
                   Exercise existingExercise = exerciseRepository.findById(exercise.getId()).orElseThrow(()-> new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
                        existingExercise.getDays().add(day);
                        exercises.add(existingExercise);
                }else{
                    exercise.setDays(days);
                    exercises.add(exercise);
                }
            }
            day.setExercises(exercises);
        }
        day = dayRepository.save(day);
        if(!dayRepository.existsById(day.getId())){
            throw  new PulseException(StatusCodes.DAY_ADDING_FAILED);
        }
        return dayRepository.save(day);
    }

    @Override
    public Day update(int id, Day day) throws PulseException{
        try {
            Day existingDay = dayRepository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.DAY_FETCHING_FAILED));
            List<Exercise> updatedExercises = new ArrayList<>();
            List<Day> existingDays = new ArrayList<>();
            existingDay.setName(day.getName());
            for (Exercise exercise : day.getExercises()) {
                if (exercise.getId() > 0) {
                    Exercise existingExercise = exerciseRepository.findById(exercise.getId()).orElseThrow(()->new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
                    if (!existingExercise.getDays().contains(existingDay)) {
                        existingExercise.getDays().add(existingDay);
                        updatedExercises.add(existingExercise);
                    } else {
                        updatedExercises.add(exercise);
                    }
                } else {
                    exercise.getDays().add(day);
                    updatedExercises.add(exercise);
                }
            }

            existingDay.getExercises().clear();
            existingDay.getExercises().addAll(updatedExercises);
            day = dayRepository.save(existingDay);
        }catch (DataAccessException e){
            throw new PulseException(StatusCodes.DAY_UPDATE_FAILED);
        }
        return day;
    }

    @Override
    public Day findById(int id) throws PulseException{
        Day day = null;
        try {
            day =dayRepository.findById(id).orElseThrow(()->new PulseException(StatusCodes.DAY_FETCHING_FAILED));
        }catch (DataAccessException e){
            throw new PulseException(StatusCodes.DAY_FETCHING_FAILED);
        }
        return day;
    }

    @Override
    public List<Day> findAll() {
        List<Day> days = new ArrayList<>();
        try {
            days = dayRepository.findAll();
        }catch (DataAccessException e){
            throw new PulseException(StatusCodes.DAY_FETCHING_FAILED);
        }
        return days;
    }

    @Override
    public boolean delete(int id) {
        boolean isDayDeletionSuccess = false;
        try {
            Optional<Day> day = dayRepository.findById(id);
            if(day.isPresent()){
                day.get().setPlan(null);
                day.get().getExercises().forEach(exercise -> exercise.getDays().removeIf(day1 -> day1.getId()==id));
                dayRepository.delete(day.get());
                isDayDeletionSuccess = true;
            }
        }catch (DataAccessException e){
            throw new PulseException(StatusCodes.DAY_DELETION_FAILED);
        }
        return isDayDeletionSuccess;
    }
}
