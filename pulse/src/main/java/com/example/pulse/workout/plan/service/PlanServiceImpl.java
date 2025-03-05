package com.example.pulse.workout.plan.service;

import com.example.pulse.workout.day.entity.Day;
import com.example.pulse.workout.day.repository.DayRepository;
import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.repository.ExerciseRepository;
import com.example.pulse.workout.plan.entity.Plan;
import com.example.pulse.workout.plan.repository.PlanRespository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanServiceImpl implements PlanService{
    PlanRespository planRespository;
    DayRepository dayRepository;
    ExerciseRepository exerciseRepository;
    PlanServiceImpl(PlanRespository planRespository,DayRepository dayRepository,ExerciseRepository exerciseRepository){
        this.planRespository = planRespository;
        this.dayRepository = dayRepository;
        this.exerciseRepository = exerciseRepository;
    }
    @Override
    public Plan addPlan(Plan plan) {
        List<Day> days = new ArrayList<>();
        days = plan.getDays();
        if(!days.isEmpty()){
            List<Day> existingDays = new ArrayList<>();
            for (Day day:days){
               if (day.getId()>0){
                   Optional<Day> existingDay = dayRepository.findById(day.getId());
                   if (existingDay.isPresent()){
                       existingDay.get().setPlan(plan);
                       existingDays.add(existingDay.get());
                   }
                   day.setPlan(plan);
               }else {
                   List<Exercise> exercises = day.getExercises();
                   List<Exercise> existingExercises = new ArrayList<>();
                   if(!exercises.isEmpty()){
                       for(Exercise exercise: exercises){
                           if(exercise.getId()>0){
                               Optional<Exercise> existingExercise = exerciseRepository.findById(exercise.getId());
                               if(existingExercise.isPresent()){
                                   existingExercise.get().getDays().add(day);
                                   existingExercises.add(existingExercise.get());
                               }
                           }else {
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
        }
        return planRespository.save(plan);
    }

    @Override
    public Plan updatePlan(int id,Plan plan) {
        plan.setId(id);
        if (plan.getDays()!=null && !plan.getDays().isEmpty()){
            List<Day> days = new ArrayList<>();
            for (Day day: plan.getDays()){
                if (day.getId()>0){
                    day.setPlan(plan);
                    days.add(day);
                }else {
                    day.setPlan(plan);
                    days.add(day);
                }
            }
            plan.setDays(days);
        }
        return planRespository.save(plan);
    }

    @Override
    public Optional<Plan> findPlanById(int id) {
        return planRespository.findById(id);
    }

    @Override
    public boolean deletePlan(int id) {
        Optional<Plan> plan = planRespository.findById(id);
       if(plan.isPresent()){
           for(Day day: plan.get().getDays()){
               day.setPlan(null);
           }
           plan.get().getDays().clear();
           planRespository.delete(plan.get());
       }
        return !planRespository.existsById(id);
    }
}
