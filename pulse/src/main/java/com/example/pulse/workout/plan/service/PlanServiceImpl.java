package com.example.pulse.workout.plan.service;

import com.example.pulse.workout.day.entity.Day;
import com.example.pulse.workout.plan.entity.Plan;
import com.example.pulse.workout.plan.repository.PlanRespository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanServiceImpl implements PlanService{
    PlanRespository planRespository;
    PlanServiceImpl(PlanRespository planRespository){
        this.planRespository = planRespository;
    }
    @Override
    public Plan addPlan(Plan plan) {
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
        Optional<Plan> plan =planRespository.findById(id);
        plan.ifPresent(value -> planRespository.delete(value));
        return !planRespository.existsById(id);
    }
}
