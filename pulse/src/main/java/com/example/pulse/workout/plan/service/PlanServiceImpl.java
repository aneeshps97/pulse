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

    @Override
    public Plan addPlan(Plan plan) throws PulseException{
        try {
            List<Day> days = plan.getDays();
            if (!days.isEmpty()) {
                List<Day> existingDays = new ArrayList<>();
                for (Day day : days) {
                    if (day.getId() > 0) {
                       Day existingDay = dayRepository.findById(day.getId()).orElseThrow(()->new PulseException(StatusCodes.DAY_FETCHING_FAILED));
                       if (existingDay.getPlan()!=null && existingDay.getPlan().getId()>0){
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
                                    Optional<Exercise> existingExercise = exerciseRepository.findById(exercise.getId());
                                    if (existingExercise.isPresent()) {
                                        existingExercise.get().getDays().add(day);
                                        existingExercises.add(existingExercise.get());
                                    }
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
        Plan exisingPlan = null;
        try {
            exisingPlan = planRespository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.PLAN_FETCHING_FAILED));
            if (plan.getDays() != null && !plan.getDays().isEmpty()) {
                List<Day> days = new ArrayList<>();
                for (Day day : plan.getDays()) {
                    if (day.getId() > 0) {
                        Day existingDay = dayRepository.findById(day.getId()).orElseThrow(() -> new PulseException(StatusCodes.DAY_FETCHING_FAILED));
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
            throw new PulseException(StatusCodes.PLAN_UPDATING_FAILED);
        }
        return plan;
    }

    @Override
    public Plan findPlanById(int id) throws PulseException{
        Plan plan = null;
        try {
            plan = planRespository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.PLAN_FETCHING_FAILED));
        } catch (DataAccessException e) {
            throw new PulseException(StatusCodes.PLAN_FETCHING_FAILED);
        }
        return plan;
    }

    @Override
    public List<Plan> findAll() throws PulseException{
        List<Plan> plans = new ArrayList<>();
        try {
            plans = planRespository.findAll();
        }catch (DataAccessException e){
            throw new PulseException(StatusCodes.PLAN_FETCHING_FAILED);
        }
        return plans;
    }

    @Override
    public boolean deletePlan(int id) throws PulseException{
        boolean isPlanDeletionSuccess = false;
        try {
            Plan plan = planRespository.findById(id).orElseThrow(() -> new PulseException(StatusCodes.PLAN_FETCHING_FAILED));
            for (Day day : plan.getDays()) {
                day.setPlan(null);
            }
            plan.getDays().clear();
            planRespository.delete(plan);
            isPlanDeletionSuccess = true;
        } catch (DataAccessException e) {
            throw new PulseException(StatusCodes.PLAN_DELETION_FAILED);
        }
        return isPlanDeletionSuccess;
    }
}
