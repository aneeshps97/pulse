package com.example.pulse.workout.day.service;

import com.example.pulse.constants.StatusCodes;
import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.day.entity.Day;
import com.example.pulse.workout.day.repository.DayRepository;
import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.repository.ExerciseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    static final Logger logger = LoggerFactory.getLogger(DayServiceImpl.class);

    @Override
    @Transactional
    public Day add(Day day) throws PulseException {
        logger.debug("Request received data::{}",day.toString());
        try {
            if(day.getExercises()!=null && !day.getExercises().isEmpty()){
                logger.debug("exercises recited for day::{}",day.getExercises().toString());
                List<Exercise> exercises = new ArrayList<>();
                for(Exercise exercise:day.getExercises()){
                    if(exercise.getId()>0){
                        logger.debug("Exercise id::{}",exercise.getId());
                        Exercise existingExercise = exerciseRepository.findById(exercise.getId()).orElseThrow(()-> new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
                        logger.debug("Existing exerciseData received::{}",existingExercise.toString());
                        existingExercise.getDays().add(day);
                        exercises.add(existingExercise);
                    }else{
                        logger.debug("Creating new exercise with day exercise name::{} dayName::{} exerciseId::{} dayId::{}",exercise.getName(),day.getName(),exercise.getId(),day.getId());
                        exercise.getDays().add(day);
                        exercises.add(exercise);
                    }
                }
                day.setExercises(exercises);
            }
            day = dayRepository.save(day);
        }catch (DataAccessException e){
            logger.error("adding new day failed ",e.getCause());
            throw  new PulseException(StatusCodes.DAY_ADDING_FAILED);
        }

        return dayRepository.save(day);
    }

    @Override
    public Day update(int id, Day day) throws PulseException{
        try {
            logger.debug("Data received for updating the day id ::{} data::{}",id,day.toString());
            Day existingDay = dayRepository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.DAY_FETCHING_FAILED));
            List<Exercise> updatedExercises = new ArrayList<>();
            List<Day> existingDays = new ArrayList<>();
            existingDay.setName(day.getName());
            for (Exercise exercise : day.getExercises()) {
                if (exercise.getId() > 0) {
                    Exercise existingExercise = exerciseRepository.findById(exercise.getId()).orElseThrow(()->new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
                    logger.debug("data received from database for existing exercises::{}", existingExercise.toString());
                    if (!existingExercise.getDays().contains(existingDay)) {
                        logger.debug("adding current day to the existing exercises");
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
            logger.info("data received for finding the day using id ::{}",id);
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
            logger.info("Request received for finding all the days in the db");
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
            logger.info("deleting the day with id ::{}",id);
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
