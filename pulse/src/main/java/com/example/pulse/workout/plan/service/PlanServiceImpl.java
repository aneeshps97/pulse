package com.example.pulse.workout.plan.service;

import com.example.pulse.constants.StatusCodes;
import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.day.entity.Day;
import com.example.pulse.workout.day.repository.DayRepository;
import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.repository.ExerciseRepository;
import com.example.pulse.workout.plan.entity.Plan;
import com.example.pulse.workout.plan.repository.PlanRespository;
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
public class PlanServiceImpl implements PlanService {
    PlanRespository planRespository;
    DayRepository dayRepository;
    ExerciseRepository exerciseRepository;
    public static final Logger logger  = LoggerFactory.getLogger(PlanServiceImpl.class);

    @Override
    public Plan addPlan(Plan plan) throws PulseException{
        try {
            logger.info("going to add plan ::{}",plan.toString());
            List<Day> days = plan.getDays();
            if (!days.isEmpty()) {
                List<Day> existingDays = new ArrayList<>();
                for (Day day : days) {
                    if (day.getId() > 0) {
                       Day existingDay = dayRepository.findById(day.getId()).orElseThrow(()->new PulseException(StatusCodes.DAY_FETCHING_FAILED));
                       logger.debug("Data received for day from repository::{}",existingDay.toString());
                       if (existingDay.getPlan()!=null && existingDay.getPlan().getId()>0){
                           logger.error("Day id ::{} is already assigned with plan id::{}",existingDay.getId(),existingDay.getPlan().getId());
                           throw new PulseException(StatusCodes.DAY_ALREADY_ASSIGNED_TO_ANOTHER_PLAN);
                       }
                       existingDay.setPlan(plan);
                       existingDays.add(existingDay);
                       day.setPlan(plan);
                    } else {
                        List<Exercise> exercises = day.getExercises();
                        List<Exercise> existingExercises = new ArrayList<>();
                        if (!exercises.isEmpty()) {
                            for (Exercise exercise : exercises) {
                                if (exercise.getId() > 0) {
                                    Exercise existingExercise = exerciseRepository.findById(exercise.getId()).orElseThrow(()->new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
                                       logger.debug("Existing exercise from repository::{}",existingExercise.toString());
                                        existingExercise.getDays().add(day);
                                        existingExercises.add(existingExercise);
                                } else {
                                    exercise.setDays(days);
                                    existingExercises.add(exercise);
                                }
                                day.setPlan(plan);
                                day.setExercises(existingExercises);
                                existingDays.add(day);
                            }
                        }
                    }
                }
                plan.setDays(existingDays);
                plan = planRespository.save(plan);
            }
        } catch (DataAccessException e) {
            throw new PulseException(StatusCodes.PLAN_ADDING_FAILED);
        }
        return plan;
    }

    @Override
    public Plan updatePlan(int id, Plan plan) throws PulseException{
        logger.info("updating plan id::{} with data ::{}",id,plan);
        Plan exisingPlan = null;
        try {
            exisingPlan = planRespository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.PLAN_FETCHING_FAILED));
            logger.info("Plan received from db::{}",exisingPlan.toString());
            if (plan.getDays() != null && !plan.getDays().isEmpty()) {
                List<Day> days = new ArrayList<>();
                for (Day day : plan.getDays()) {
                    if (day.getId() > 0) {
                        Day existingDay = dayRepository.findById(day.getId()).orElseThrow(() -> new PulseException(StatusCodes.DAY_FETCHING_FAILED));
                        logger.debug("day data received for the plan from db for id ::{} data::{}",day.getId(),existingDay);
                        if (existingDay.getPlan()!=null && existingDay.getPlan().getId()>0 && existingDay.getPlan().getId()!=plan.getId()){
                            throw new PulseException(StatusCodes.DAY_ALREADY_ASSIGNED_TO_ANOTHER_PLAN);
                        }
                        days.add(existingDay);
                    } else {
                        day.setPlan(exisingPlan);
                        days.add(day);
                    }
                }
                exisingPlan.getDays().clear();
                exisingPlan.setDays(days);
            }
            plan = planRespository.save(exisingPlan);
        } catch (DataAccessException e) {
            logger.error("plan updating failed ::",e.getCause());
            throw new PulseException(StatusCodes.PLAN_UPDATING_FAILED);
        }
        return plan;
    }

    @Override
    public Plan findPlanById(int id) throws PulseException{
        logger.info("finding plan with id ::{}",id);
        Plan plan = null;
        try {
            plan = planRespository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.PLAN_FETCHING_FAILED));
        } catch (DataAccessException e) {
            logger.error("plan fetching failed ::",e.getCause());
            throw new PulseException(StatusCodes.PLAN_FETCHING_FAILED);
        }
        return plan;
    }

    @Override
    public List<Plan> findAll() throws PulseException{
        logger.info("finding all plan details");
        List<Plan> plans = new ArrayList<>();
        try {
            plans = planRespository.findAll();
        }catch (DataAccessException e){
            logger.error("plan finding failed ::",e.getCause());
            throw new PulseException(StatusCodes.PLAN_FETCHING_FAILED);
        }
        return plans;
    }

    @Override
    public boolean deletePlan(int id) throws PulseException{
        logger.info("Deleting plan with id ::{}",id);
        boolean isPlanDeletionSuccess = false;
        try {
            Plan plan = planRespository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.PLAN_FETCHING_FAILED));
            for (Day day : plan.getDays()) {
                logger.debug("clearing day from plan ::id::{}",day.getId());
                day.setPlan(null);
            }
            plan.getDays().clear();
            planRespository.delete(plan);
            isPlanDeletionSuccess = true;
        } catch (DataAccessException e) {
            logger.error("Plan delete failed ::",e.getCause());
            throw new PulseException(StatusCodes.PLAN_DELETION_FAILED);
        }
        return isPlanDeletionSuccess;
    }
}
